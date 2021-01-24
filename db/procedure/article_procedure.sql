CREATE DEFINER=`jack`@`localhost` PROCEDURE `article_procedure`(
  in _switch varchar(20)
  ,in _article_id int
  ,in _reporter_id int
  ,in _category varchar(20)
  ,inout _commentId int
  ,inout _id int
  ,out _title varchar(200)
  ,out _subTitle varchar(500)
  ,out _content varchar(5000)
  ,out _createdAt datetime
  ,out _editedAt datetime
  ,out _hit int
  ,out _image varchar(200)
  ,out _imgDec varchar(200)
  ,out _source varchar(200)
  ,out _report_id int
  ,out _email varchar(50)
  ,out _name varchar(20)
  ,out result_set varchar(8)
)
BEGIN
declare checkData int default -1;

if _switch = 'blind' then
	update article set status = 'deleted' where reporter_id = _reporter_id and id =_article_id;
end if;

if _switch = 'get_list' then
	select a.id,a.title,a.content, i.image,i.description 
	from article a
	left outer join image i on i.article_id = a.id
	where a.reporter_id = _reporter_id
	;
end if;
	-- 19. 기사 댓글데이터		
if _switch = 'comment' then
	if _commentId = -1 then
		select c.id,u.id as userId,u.email,ud.nick_name as nickName,ud.local, c.content
			,date_format(c.created_at, '%Y-%m-%d %H:%i:%S') as createdAt,c.del_flag as delFlag 
			,(select count(reputation) from comm_reputation where reputation = 'recommend' and comment_id = c.id ) as recommend
			,(select count(reputation) from comm_reputation where reputation = 'decommend'and comment_id = c.id ) as decommend
            ,(select count(*) from comment where parent_id = c.id) as replies_count
		from comment as c
		left join users as u on c.user_id = u.id
		left join user_detail as ud on u.id = ud.user_id
		where c.article_id = _article_id and c.parent_id is null
		order by c.id desc
		limit 10
		;
	else
		select c.id,u.id as userId,u.email,ud.nick_name as nickName,ud.local, c.content
			,date_format(c.created_at, '%Y-%m-%d %H:%i:%S') as createdAt,c.del_flag as delFlag 
			,(select count(reputation) from comm_reputation where reputation = 'recommend' and comment_id = c.id ) as recommend
			,(select count(reputation) from comm_reputation where reputation = 'decommend'and comment_id = c.id ) as decommend
            ,(select count(*) from comment where parent_id = c.id) as replies_count
		from comment as c
		left join users as u on c.user_id = u.id
		left join user_detail as ud on u.id = ud.user_id
		where c.article_id = _article_id and c.id < _commentId and c.parent_id is null
		order by c.id desc
		limit 10
		;
	end if;
end if;
    
    -- 기사 상세페이지(16) -----------------------------------------------------------
if _switch = 'article_detail' then
	start transaction;
	update article set hit = hit + 1 where id = _article_id;
 
	select article.id as article_id, article.title, article.sub_title, article.content, article.hit
		, DATE_FORMAT(article.created_at, '%Y-%m-%d %H:%i:%S') as created_at
        , DATE_FORMAT(article.edited_at, '%Y-%m-%d %H:%i:%S') as edited_at
		,image.image, image.source, image.description
		, users.id as reporter_id, users.email, user_detail.name
        , count(comment.id) as comment_count
	from article
		left join image on article.id = image.article_id
		left join users on article.reporter_id = users.id
		left join user_detail on article.reporter_id = user_detail.user_id
        left join comment on article.id = comment.article_id
	where article.id = _article_id and comment.parent_id is null
    ;
	commit;
end if;
  
    /* 기사 감정 데이터
	   작성자 : 이종현
       수정 : 류제욱
    */
if _switch = 'article_emotion' then
	select emotion
		, (select count(*) from article_emotion where article_id = _id and emotion_id = id) as count
	from emotion
	;
 end if;
 
    /* 기사 대댓글 데이터
	   작성자 : 이종현
    */
if _switch = 'get_comment_reply' then
	if _commentId = -1 then
		select c.id,u.id as userId,u.email,ud.nick_name as nickName,ud.local, c.content
			,date_format(c.created_at, '%Y-%m-%d %H:%i:%S') as createdAt,c.del_flag as delFlag 
			,(select count(reputation) from comm_reputation where reputation = 'recommend' and comment_id = c.id ) as recommend
			,(select count(reputation) from comm_reputation where reputation = 'decommend'and comment_id = c.id ) as decommend
		from comment as c
		left join users as u on c.user_id = u.id
		left join user_detail as ud on u.id = ud.user_id
		where c.parent_id = _id
		order by c.id desc
		limit 10
		;
	else
		select c.id,u.id as userId,u.email,ud.nick_name as nickName,ud.local, c.content
			,date_format(c.created_at, '%Y-%m-%d %H:%i:%S') as createdAt,c.del_flag as delFlag 
			,(select count(reputation) from comm_reputation where reputation = 'recommend' and comment_id = c.id ) as recommend
			,(select count(reputation) from comm_reputation where reputation = 'decommend'and comment_id = c.id ) as decommend
		from comment as c
		left join users as u on c.user_id = u.id
		left join user_detail as ud on u.id = ud.user_id
		where c.parent_id = _id and c.id < _commentId
		order by c.id desc
		limit 10
		;
	end if;
end if;
	
-- 핫뉴스(6) -------------------------------------------------------
	if _switch = 'popular' then
		if _category = 'all' then
			select a.id, a.title, a.sub_title, i.image, i.description as imgDec, created_at
			from article as a
				left outer join image as i on a.id = i.article_id
				left outer join category as c on a.category_id = c.id 
			where a.created_at > DATE_SUB(current_timestamp(), INTERVAL 1000 HOUR) and a.status = 'publish'
			order by a.hit desc, a.id desc limit 10;
		else 
			select a.id, a.title, a.sub_title, i.image, i.description as imgDec, created_at
			from article as a
				left outer join image as i on a.id = i.article_id
				left outer join category as c on a.category_id = c.id 
			where a.created_at > DATE_SUB(current_timestamp(), INTERVAL 1000 HOUR) and a.status = 'publish'
				and c.name = _category
			order by a.hit desc, a.id desc limit 10;
		end if;
	end if;

-- 카테고리 정보 ------------------------------------------------------
	if _switch = 'category_info' then
		select * from category;
	end if;
    
if _switch = 'latest' then
	select a.id, c.name as category, DATE_FORMAT(a.edited_at, '%Y-%m-%d %H:%i:%S') as edited_at, a.title, a.sub_title, i.image, i.description as imgDec
	from article as a 
	left join image as i on a.id = i.article_id
	left join category as c on a.category_id = c.id
	where created_at  > date_sub(current_timestamp(), interval 1000 hour) and c.name= _category and a.status = 'publish'
    order by a.id desc
    limit 10;
    set result_set = 'success';
end if;
    
if _switch = 'latest_all' then
	(select a.id, c.name as category, a.title, a.sub_title, i.image, i.description as imgDec
	from article as a 
	left join image as i on a.id = i.article_id
	left join category as c on a.category_id = c.id
	where created_at  > date_sub(current_timestamp(), interval 1000 hour) and a.category_id = 1 and a.status = 'publish'
    order by a.id desc
    limit 5)
	union
	(select a.id, c.name as category, a.title, a.sub_title, i.image, i.description as imgDec
	from article as a 
	left join image as i on a.id = i.article_id
	left join category as c on a.category_id = c.id
	where created_at  > date_sub(current_timestamp(), interval 1000 hour) and a.category_id = 2 and a.status = 'publish'
    order by a.id desc
    limit 5)
    union
	(select a.id, c.name as category, a.title, a.sub_title, i.image, i.description as imgDec
	from article as a 
	left join image as i on a.id = i.article_id
	left join category as c on a.category_id = c.id
	where created_at  > date_sub(current_timestamp(), interval 1000 hour) and a.category_id = 3 and a.status = 'publish'
    order by a.id desc
    limit 5)
    union
	(select a.id, c.name as category, a.title, a.sub_title, i.image, i.description as imgDec
	from article as a 
	left join image as i on a.id = i.article_id
	left join category as c on a.category_id = c.id
	where created_at  > date_sub(current_timestamp(), interval 1000 hour) and a.category_id = 4 and a.status = 'publish'
    order by a.id desc
    limit 5)
    union
	(select a.id, c.name as category, a.title, a.sub_title, i.image, i.description as imgDec
	from article as a 
	left join image as i on a.id = i.article_id
	left join category as c on a.category_id = c.id
	where created_at  > date_sub(current_timestamp(), interval 1000 hour) and a.category_id = 5 and a.status = 'publish'
    order by a.id desc
    limit 5)
    union
	(select a.id, c.name as category, a.title, a.sub_title, i.image, i.description as imgDec
	from article as a 
	left join image as i on a.id = i.article_id
	left join category as c on a.category_id = c.id
	where created_at  > date_sub(current_timestamp(), interval 1000 hour) and a.category_id = 6 and a.status = 'publish'
    order by a.id desc
    limit 5)
    union
	(select a.id, c.name as category, a.title, a.sub_title, i.image, i.description as imgDec
	from article as a 
	left join image as i on a.id = i.article_id
	left join category as c on a.category_id = c.id
	where created_at  > date_sub(current_timestamp(), interval 1000 hour) and a.category_id = 7 and a.status = 'publish'
    order by a.id desc
    limit 5)
	;
    set result_set = 'success';
end if;

END