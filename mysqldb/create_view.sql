create view view_post
as
select post_id, title, content, author_id, time, name
from posts inner join users
on posts.author_id=users.user_id