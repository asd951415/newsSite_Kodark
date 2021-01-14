CREATE DEFINER=`root`@`localhost` PROCEDURE `admin_procedure`(
   in _switch varchar(30)
    , in _id int
	, inout _email varchar(50)    
    , inout _pwd varchar(300)
    , inout _nickName varchar(20)
    , inout _name varchar(20)    
    , inout _local varchar(50)
    , inout _birth datetime
    , inout _gender char(1)
    , inout _image varchar(200)
    , inout _auth char(8)
    , in _commentId int
    , in _commentReportId int
    , in _doneFlag varchar(1)
    , in _delFlag varchar(1)
    , in _status varchar(20)
   , out result_set varchar(10)
)
BEGIN
   declare idCount int;
   declare authCheck char(8);
   declare _dataInt int;

   if _switch = 'create_reporter' then
      insert into  users (email, pwd, auth) values (_email, _pwd, _auth);
        set @result = (select id from users where email = _email);
      insert into user_detail (user_id, nick_name, name, local, birth, gender, image) values (@result, _nickName, _name, _local, _birth, _gender, _image );
        
    elseif _switch = 'navigation' then
      select count(*) into idCount from users where id = _id;
      if idCount > 0 then
         select auth into authCheck from users where id = _id;
            if authCheck = 'admin' and _email is not null then
            set result_set = '204';
            else
            set result_set = '403';
         end if;
            
       elseif idCount < 0 or idCount = 0 then 
         set result_set = '401';
        else 
         set result_set = '500';
      end if; 
	/* 댓글 신고 토글
	   작성자 : 이종현
    */      
   elseif _switch = 'comment_report_toggle' then
		select count(*) into idCount from comment where id = _commentId;
        if idCount > 0 then
			update comment set del_flag = _delFlag 
			where id = _commentId
			and del_flag != _delFlag
			;
			set result_set = '200';
		elseif idCount = 0 then
			set result_set = '404';
		else set result_set = '500';
		end if;
	/* 댓글 신고 확인
	   작성자 : 이종현
    */
    elseif _switch = 'comment_report_check' then
		select count(*) into idCount from comm_report where id = _commentReportId;
        if idCount > 0 then
			update comm_report set done_flag = 'T' 
			where id = _commentReportId;
			set result_set = '200';
		elseif idCount = 0 then
			set result_set = '404';
		else set result_set = '500';
		end if; 
	/* 댓글 신고 목록
	   작성자 : 이종현
    */
	elseif _switch = 'comment_report_list' then
		select count(*) into idCount from comment where id = _commentId;
        if idCount > 0 then
			select 
            cr.id
            , cr.reason
            ,date_format(c.created_at, '%Y-%m-%d %H:%i:%S') as 'createdAt'
            , cr.done_flag as doneFlag
            , u.id as userId
            , u.email
            , c.id as commentId
            , c.content as content
            , c.del_flag as delFlag
			from comm_report cr
			inner join users u on cr.user_id = u.id
			inner join comment c on cr.comment_id = c.id
            where c.id = _commentId and cr.done_flag = _doneFlag
			group by cr.id;
			set result_set = '200';
		else set result_set = '404';
        end if;
   /* 대기중 기사 상세 
	  작성자 : 이종현
   */     
   elseif _switch = 'article_wait_detail' then
   select reporter_id into _dataInt from article where id = _id;
		select
		a.id
		,c.name as category
		,a.title
		,a.sub_title
		,a.content
		,u.id as userId
		,u.email
		,(select ud.name from user_detail ud inner join users u on ud.user_id = u.id where u.id = _dataInt) as name
		from article a
		inner join users u
		on a.reporter_id = u.id
        
        left join category c
        on a.category_id = c.id
        where a.id = _id and a.status = _status;

		set result_set = '200';
	/* 대기중 기사 상세 이미지
	   작성자 : 이종현
    */
    elseif _switch = 'article_wait_detail_image' then
		select 
        image
        ,source
        ,description 
        from image
        where article_id = _id;
        
    /* 기자 목록
	   작성자 : 이종현
    */	        
	elseif _switch ='admin_reporters_list' then
	select 
    users.id, 
    user_detail.name, 
    count(article.id) as articleNum, 
    sum(article.hit) as totalHit,
    (select count(*) from subscriber where reporter_id = users.id) as subNum
    from users 
    
    inner join user_detail 
    on 
    users.id = user_detail.user_id
    
    left join article
    on
    users.id = article.reporter_id
    
    where auth = 'reporter'
    group by users.id
    ;
    
   else set result_set = '500';
   end if;
END