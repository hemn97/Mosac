go
create trigger del_posts on posts
for delete
as
begin 
    delete from posts_view where post_id = (select post_id from deleted)
    delete from posts_comment where post_id = (select post_id from deleted)
    delete from posts_lightup where post_id = (select post_id from deleted)
end

go
create trigger del_users on users
for delete
as
begin 
    delete from users_auths where user_id = (select user_id from deleted)
    delete from team_applicationlist where user_id = (select user_id from deleted)
    delete from teammates where user_id = (select user_id from deleted)
    delete from teams where captain_id = (select user_id from deleted)
    delete from posts_view where user_id = (select user_id from deleted)
    delete from posts_comment where user_id = (select user_id from deleted)
    delete from posts_lightup where user_id = (select user_id from deleted)
    delete from posts where author_id = (select user_id from deleted)
end

go
create trigger del_teams on teams
for delete
as
begin 
    delete from teammates where team_id = (select team_id from deleted)
    delete from team_applicationlist where team_id = (select team_id from deleted)
end