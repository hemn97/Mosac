create table users(
	user_id int IDENTITY(1,1),
	name varchar(20),
	contact_number varchar(11),
	email varchar(50),
	department varchar(50),
	PRIMARY KEY (user_id)
);

create table users_auths(
	user_id int REFERENCES users(user_id),
	number varchar(8) unique,
	password varchar(20),
	PRIMARY KEY (user_id)
);

create table posts(
	post_id int IDENTITY(1,1),
	title varchar(60),
	content varchar(360),
	author_id int REFERENCES users(user_id),
	time varchar(16),
	PRIMARY KEY (post_id)
);

create table posts_view(
	post_id int REFERENCES posts(post_id),
	user_id int REFERENCES users(user_id),
	PRIMARY KEY (post_id, user_id)
);

create table posts_lightup(
	post_id int REFERENCES posts(post_id),
	user_id int REFERENCES users(user_id),
	PRIMARY KEY (post_id, user_id)
);

create table posts_comment(
	comment_id int IDENTITY(1,1),
	post_id int REFERENCES posts(post_id),
	lightup_id int REFERENCES users(user_id),
	comment varchar(360),
	PRIMARY KEY (comment_id)
);

create table teams(
	team_id int IDENTITY(1,1),
	captain_id int REFERENCES users(user_id),
	team_name varchar(20),
	time varchar(50),
	description varchar(200),
	place varchar(100),
	type varchar(10),
	PRIMARY KEY (team_id)
);

create table teammates(
	team_id int REFERENCES teams(team_id),
	user_id int REFERENCES users(user_id),
	PRIMARY KEY (team_id, user_id)
);

create table team_applicationlist(
	team_id int REFERENCES teams(team_id),
	user_id int REFERENCES users(user_id),
	PRIMARY KEY (team_id, user_id)
);

create table admins(
	admin_id int IDENTITY(1,1),
	username varchar(30),
	password varchar(30),
	PRIMARY KEY (admin_id)
);