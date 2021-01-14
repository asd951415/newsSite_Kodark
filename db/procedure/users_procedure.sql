CREATE DEFINER=`jack`@`localhost` PROCEDURE `users_procedure`(
   in _switch varchar(20)
    , in _id int
    , in _reporter_id int
    , inout _email varchar(50)  
    , inout _pwd varchar(300)
    , inout _nickName varchar(20)
    , inout _name varchar(20)    
    , inout _local varchar(50)
    , inout _birth datetime
    , inout _gender char(1)
    , inout _image varchar(200)
    , out _auth char(8)
    , out result_set varchar(10)
)
begin
declare emailCheck int;
declare idCount int;
declare letterCheck char(1);
declare tmp_pwd varchar(300);
declare nickNameCount int;

-- 회원정보 수정(34)--------------------------------------------
   if _switch = 'update_detail' then
      select count(*) into idCount from users where id = _id;        
      if idCount > 0 then
         if _email is null then
            set result_set = '404';
         else    
            update user_detail 
            set nickname = _nickName, name = _name ,local = _local, birth = _birth , gender = _gender ,image = _image  
            where id = _id;            
            set result_set = '204';
         end if;                       
      elseif idCount < 0 or idCount = 0 then 
         set result_set = '401';
      else 
         set result_set = '500';
      end if; 
	end if;

-- 회원 탈퇴(32) --------------------------------------------------
	if _switch = 'delete' then
	select count(*) into idCount from users where  id = _id;   
	select count(email) into emailCheck from users where id = _id;
      if idCount > 0 then
         if emailCheck = 0 then
            set result_set = '404';
         else    
            update users
            set status = 'stop', email = null where id = _id;
            set result_set = '204';
         end if;    
      elseif idCount < 0 or idCount = 0 then 
         set result_set = '401';
      else 
         set result_set = '500';    
      end if;
      
   elseif _switch = 'update_password' then 
      select count(*) into idCount from users where  id = _id;   
        
      if idCount > 0 then
         if _email is null then
            set result_set = '404';
		else    
            update users
            set pwd = _pwd
                where id = _id;
            set result_set = '204';
		end if;    
      elseif idCount < 0 or idCount = 0 then 
         set result_set = '401';
      else 
         set result_set = '500';    
		end if;

	elseif _switch = 'mypage_detail' then
      select count(*) into idCount from users where  id = _id;   

      if idCount > 0 then
         select nick_name, name, local, DATE_FORMAT(birth, '%Y-%m-%d %H:%i:%S') as birth, gender, image into _nickName, _name,  _local,  _birth, _gender, _image from user_detail where user_id = _id;
            
         set result_set = '200';
            
      elseif idCount < 0 or idCount = 0 then 
         set result_set = '401';
      else
         set result_set = '500';
      end if;
        
  elseif _switch = 'signin_info' then
      select count(*) into idCount from users where  id = _id;   
        
      if idCount > 0 then           
         set result_set = '200';
      else 
         set result_set = '401';
      end if;
      
   elseif _switch = 'user_info' then
      select email, auth, pwd into _email, _auth, _pwd from users where id = _id;
        if _email = null then
         set result_set = 'not_found';
      else
         set result_set = 'success';
        end if;
        
	/***** 이메일 업데이트 *****/
    elseif _switch = 'update_email' then
		select count(*) into idCount from users where email = _email;
        
        if idCount > 0 then
			set result_set = 'conflict';
		else
			update users set email = _email where id = _id;
            set result_set = 'success';
		end if;
        
	-- 37.구독취소 ys		
	elseif _switch = 'cancel_sub' then
		select count(*) into idCount from users where  id = _reporter_id and status = 'use';
        if idCount > 0 then
			delete from subscriber where reporter_id = _reporter_id and user_id = _id;
            set result_set = '205';
		else
			set result_set = '404';
		end if;
    
	-- 36.뉴스레터 토글 ys
  elseif _switch = 'toggle' then
		select count(*) into idCount from users where id = _id;
    if idCount > 0 then
			select letter_accepted into letterCheck from subscriber where user_id = _id and reporter_id = _reporter_id;
      if letterCheck = 'T' then
				update subscriber set letter_accepted = 'F' where user_id = _id and reporter_id = _reporter_id;
			elseif letterCheck = 'F' then
				update subscriber set letter_accepted = 'T' where user_id = _id and reporter_id = _reporter_id;
			end if;
      set result_set = '204';
		else
			set result_set = '404';
		end if;		
    
	end if;
      
-- 마이페이지(28)-----------------------------------------------------
	if _switch = 'my_page' then
		select count(*) into idCount from users where id = _id;   
		select email into _email from users where id = _id;
		select auth into _auth from users where id = _id; 
		
        if idCount > 0 then
			if _email is null then
			   set result_set = '404';
			else
				set result_set = '200';
			end if;
        end if;
	end if;
    
if _switch = 'user_update' then
	select count(*) into idCount from users where  id = _id;   
	
	if idCount > 0 then
        select count(*) into nickNameCount from user_detail where nick_name = _nickName;
		
        if nickNameCount > 0 then
			set result_set = '409';
		else
			select count(*) into idCount from user_detail where user_id = _id;
            
			if idCount > 0 then
				update user_detail set nick_name= _nickName, name = _name, local = _local, birth = _birth, gender = _gender, image = _image
                where user_id = _id;
			else
				insert into user_detail(user_id, nick_name, name, local, birth, gender, image)
				values(_id, _nickName, _name, _local, _birth, _gender, _image);
			end if;
            set result_set = '200';
		end if;
	else
		set result_set = '404';
	end if;
end if;
   
END