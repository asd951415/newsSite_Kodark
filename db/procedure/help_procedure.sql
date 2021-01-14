CREATE DEFINER=`root`@`localhost` PROCEDURE `help_procedure`(
	in _switch varchar(20)
    , in _user_id int
	, in _id int
    , in _title varchar(200)
    , in _content varchar(2000)
    , in _createdAt timestamp
    , in _doneFlag char(1)
    , out result_set varchar(10)
)
BEGIN
declare idCount int;

-- 고객센터(40) --------------------------------------------
 if _switch = 'service_center' then
	select id, title, content from FAQ;
 end if;
 
-- 내문의글(41) -------------------------------------------- 
if _switch = 'question_list' then
	select count(*) into idCount from users where id = _user_id;  
	if idCount > 0 then
		select id, title, date_format(created_at, '%Y-%m-%d %H:%i:%S') as 'created_at', done_flag from question
		where user_id = (select id from users where id = _user_id);
		set result_set = '200';
	else
		set result_set = '404';
	end if;
end if;

END