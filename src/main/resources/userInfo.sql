select usr.name as userName, usr.created_at, post.text as firstPost, commentCount.rowCount as commentsCount
from "user" as usr
left join
post as post
inner join(
select id from post where user_id=? order by id asc limit 1) postIDTop
on postIDTop.id=post.id
on usr.id=post.user_id
left join
(Select sum(1) as rowCount, user_id as user_id  from "comment" where user_id=? group by user_id) commentCount
on usr.id=commentCount.user_id
where usr.id=?