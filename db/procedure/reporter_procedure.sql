CREATE DEFINER=`root`@`localhost` PROCEDURE `reporter_procedure`(
	in _switch varchar(50)
	,in _id int
    ,in _article_id int
    ,in  _reporter_id int
    ,in _category_id int
    ,in _title varchar(200)
    ,in _sub_title varchar(500)
    ,in _content varchar(5000)
    ,in _main_image_url varchar(200)
    ,in _main_image_source varchar(200)
    ,in _main_image_description varchar(200)
	,out result_set varchar(10)	
    
)
BEGIN
declare checkId varchar(8);
declare last_article_id int default -1;
   
	-- 67.발행대기 기사 삭제
	if _switch = 'unpublish' then
		update article set status = 'blind' where reporter_id = _reporter_id and id =_article_id;
	-- 68. 발행된 기사 블라인드		
    elseif _switch = 'deleted' then
		update article set status = 'blind' where reporter_id = _reporter_id and id =_article_id;
	elseif _switch = 'get_list' then
		select a.id,a.title,a.sub_title, i.image,i.description 
        from article a
        left outer join image i on i.article_id = a.id
        where a.reporter_id = _reporter_id and a.status ='publish'
        ;
	-- 66.기사수정		
	elseif _switch = 'modify' then
		update article set title = _title, sub_title = _sub_title, content = _content, category_id = _category_id
        where id = _article_id;
        update image set image = _main_image_url, source = _main_image_source, description = _main_image_description
        where article_id = _article_id;
        set result_set = '204';
    
    else set result_set = '500';
  end if;

    /* 기자 프로필
	   작성자 : 이종현
    */
    if _switch = 'reporter_profile' then
		select count(*)
		into checkId
		from users 
		where auth ='reporter' and id = _id;
    
		IF checkId > 0 THEN
			select
			users.id,
			users.email,
			ud.nick_Name,
			ud.name,
			ud.local,
			date_format(ud.birth, '%Y-%m-%d') as birth,
			ud.gender,
			ud.image,
			(select count(*) from subscriber where reporter_id = users.id) as subNum
			from 
			users

			inner join user_detail ud
			on users.id = ud.user_id
			where auth ='reporter' AND users.id = _id
			group by users.id
			;
			set result_set = '200';
		
		ELSEIF checkId = 0 THEN
			set result_set = '404';
		END IF;
    
	end IF;
    
	if _switch = 'new_post' then
		-- 트랜잭션
		start transaction;
		insert into article(reporter_id, category_id, title, sub_title, content)
        values(_reporter_id, _category_id, _title, _sub_title, _content);
        
        set last_article_id = last_insert_id();
        
        if last_article_id != -1 then
			insert into image(article_id, image, source, description)
            values(last_article_id, _main_image_url, _main_image_source, _main_image_description);
            commit;
            set result_set = 'success';
		else
			rollback;
            set result_set = 'fail';
		end if;
        
	end if;
    
END