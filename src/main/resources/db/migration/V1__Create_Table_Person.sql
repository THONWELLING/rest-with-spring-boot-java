CREATE TABLE person (
"id" serial not null PRIMARY KEY,
"first_name" varchar(80) not null,
"last_name" varchar(80) not null,
"address" varchar(180) not null,
"gender" varchar(6) not null
);