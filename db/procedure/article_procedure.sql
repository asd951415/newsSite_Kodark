
CREATE DEFINER=`root`@`localhost` PROCEDURE `article_procedure`(
  in _switch varchar(20)
  , inout _id int
  ,in _article_id int
  ,in  _reporter_id int
  , in _category varchar(20)
  , in _commentId int
  ,out _title varchar(200)
  , out _subTitle varchar(500)
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
	if _switch = 'blind' then
		update article set status = 'deleted' where reporter_id = _reporter_id and id =_article_id;
	elseif _switch = 'get_list' then
		select a.id,a.title,a.content, i.image,i.description 
        from article a
        left outer join image i on i.article_id = a.id
        where a.reporter_id = _reporter_id
        ;
	-- 19. 기사 댓글데이터		
	elseif _switch = 'comment' then
		select c.id,u.id as userId,u.email,ud.nick_name as nickName,ud.local, c.content
			,date_format(c.created_at, '%Y-%m-%d %H:%i:%S') as createdAt,c.del_flag as delFlag 
			,(select count(reputation) from comm_reputation where reputation = 'recommend' and comment_id = c.id ) as recommend
			,(select count(reputation) from comm_reputation where reputation = 'decommend'and comment_id = c.id ) as decommend
		from comment as c
		join users as u on c.user_id = u.id
		join user_detail as ud on u.id = ud.user_id
		where c.article_id = _article_id
		limit 15 offset _id
		;
    
    -- 기사 상세페이지(16) -----------------------------------------------------------
 elseif _switch = 'articleDetail' then
	select article.id, article.title, article.content, article.created_at, article.edited_at, article.hit
		,image.image, image.source, image.description
		, users.id, users.email, user_detail.name
	from article
		inner join image on article.id = image.article_id
		inner join users on reporter_id = users.id
		inner join user_detail on users.id = user_detail.user_id
	where article.id = _id;

    /* 기사 감정 데이터
	   작성자 : 이종현
    */
    elseif _switch = 'article_emotion' then
		SELECT count(*) into checkData 
		FROM article_emotion 
		WHERE article_id =_id;
		
		IF checkData > 0 THEN
		select emotion,count(*) as count
		from article_emotion 
		
		inner join emotion
		on article_emotion.emotion_id = emotion.id
		
		where article_id= _id
		group by emotion;
		set result_set = '200';
		END IF;
        
    /* 기사 대댓글 데이터
	   작성자 : 이종현
    */
    elseif _switch = 'article_comment_reply' then
		select
		c.id
		,u.id as userId
		,u.email
		,ud.nick_name as nickName
		,ud.local
		, c.content
		,date_format(c.created_at, '%Y-%m-%d %H:%i:%S') as createdAt
		,c.del_flag as delFlag 
		,(select count(reputation) from comm_reputation where reputation = 'recommend' and comment_id = c.id ) as recommend
		,(select count(reputation) from comm_reputation where reputation = 'decommend'and comment_id = c.id ) as decommend
		from comment as c
		join users as u on c.user_id = u.id
		join user_detail as ud on u.id = ud.user_id
	    where c.article_id = _id AND c.parent_id = _commentId
		;
    end if;

-- 핫뉴스(6) -------------------------------------------------------
	if _switch = 'popular' then
		if _category = 'all' then
			select a.id, a.title, a.sub_title, i.image, i.description as imgDec, created_at
			from article as a
				left outer join image as i on a.id = i.article_id
				left outer join category as c on a.category_id = c.id 
			where a.created_at > DATE_SUB(current_timestamp(), INTERVAL 24 HOUR)
			order by a.hit desc limit 10;
		else 
			select a.id, a.title, a.sub_title, i.image, i.description as imgDec, created_at
			from article as a
				left outer join image as i on a.id = i.article_id
				left outer join category as c on a.category_id = c.id 
			where a.created_at > DATE_SUB(current_timestamp(), INTERVAL 24 HOUR)
				and c.name = _category
			order by a.hit desc limit 10;
	end if;

-- 카테고리 정보 ------------------------------------------------------
	if _switch = 'category_info' then
		select * from category;
	end if;

END