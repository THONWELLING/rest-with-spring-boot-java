CREATE TABLE books (
  "id" serial not null PRIMARY KEY,
  "author" text ,
  "launch_date" timestamp(6) not null,
  "price" decimal(65,2) not null,
  "title" text
);