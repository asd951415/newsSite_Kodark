
-- ----------------------------
-- 48. 회원정지 및 이메일전송 처리 ys
-- -------------------------
delimiter $$
create trigger updateUsersTB
after insert on forbbiden 
for each row
begin 
declare userStatus varchar(8);
declare userId int;
set userStatus = new.status;
set userId = new.user_id; 
update users set status = new.status where id = userId ;
end $$
delimiter ; 