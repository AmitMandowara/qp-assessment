create database grocery;
use grocery;
create table grocery_items(id INT primary key, name Varchar(100), unit INT, priceperunit INT);
select * from grocery_items;
alter table grocery_items drop column id;
alter table grocery_items add column id INT primary key auto_increment;
desc grocery_items;
alter table grocery_items add column vendor varchar(100);
alter table grocery_items add column is_rotten boolean default 0;

create table user_details(id int primary key auto_increment, name varchar(100), email varchar(100), address varchar(200));
create table booked_groceries(id int primary key auto_increment, grocery_id int, booked_by_user_id int, foreign key (grocery_id) references grocery_items(id),  foreign key (booked_by_user_id) references user_details(id));
alter table booked_groceries add column units_booked int;
insert into user_details values(1, "test", "test@gmail.com", "Pune");