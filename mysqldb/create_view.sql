create view view_post
as
select post_id, title, content, author_id, time, name
from posts inner join users
on posts.author_id=users.user_id

create view view_comment
as
select comment_id, post_id, name, comment
from posts_comment inner join users
on posts_comment.user_id=users.user_id