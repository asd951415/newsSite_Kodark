CREATE DEFINER=`root`@`localhost` PROCEDURE `admin_getWaitArticle`(
   in _status varchar(10)
)
BEGIN
	select
		user_detail.name
        , users.email
        , article.id
        , article.title
	from users
		left join article on users.id = article.reporter_id
        right join user_detail on users.id = user_detail.user_id
	where article.status=_status
    ;
END