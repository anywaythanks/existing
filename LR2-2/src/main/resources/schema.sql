drop table if exists users;
drop table if exists comments;

create table users (
     id identity primary key,
     username varchar(25) not null,
     password varchar(25) not null,
     fullName varchar(100) not null,
     email varchar(50) not null,
     updateByEmail boolean not null
);
create table users (
   id identity primary key,
   login varchar(25) not null,
   password varchar(25) not null,
   amount bigint not null
);
create table messages  (
     id identity primary key,
     users integer not null,
     message varchar(2000) not null,
     postedTime datetime not null,
     foreign key (users) references users(id)
);
select * from users;
insert into users(username, password, fullname, email, updatebyemail) values('admin', 'admin', 'Admin admin', 'admin@mail.com', false);
insert into users(username, password, fullname, email, updatebyemail) values('user', 'user', 'User user', 'user@mail.com', false);
insert into messages(users, message, postedtime) values(1, 'yooo', CURRENT_TIMESTAMP);