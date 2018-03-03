create view view_post
as
select post_id, title, content, author_id, time, name
from posts inner join users
on posts.author_id=users.user_id

create view view_comment
as
select comment_id, post_id, name, comment, time
from posts_comment inner join users
on posts_comment.user_id=users.user_id

create view view_postviews
as
select post_id, COUNT(*) as 'views' from posts_view group by post_id;

create view view_lightup
as
select post_id, COUNT(*) as 'ups' from posts_lightup group by post_id;

create view view_teammates
as
select team_id, COUNT(*) as 'teammatesCnt' from teammates
group by team_id

create view view_comment_2
as
select post_id, COUNT(*) as 'comments' from posts_comment group by post_id;

create view view_posts_2
as
select view_post.post_id, title, content, author_id, name, time, views, ups, comments from view_post
left join view_comment_2
on view_post.post_id=view_comment_2.post_id
left join view_lightup
on view_post.post_id=view_lightup.post_id
left join view_postviews
on view_post.post_id=view_postviews.post_id

create view view_team
as
select teams.team_id, captain_id, name as 'captain_name', team_name, time, description, place, type, teammatesCnt
from teams
left join view_teammates
on teams.team_id=view_teammates.team_id
left join users
on users.user_id=teams.captain_id

create view view_applicants
as
select team_id, users.user_id, name, contact_number, email, department from team_applicationlist
inner join users
on team_applicationlist.user_id=users.user_id