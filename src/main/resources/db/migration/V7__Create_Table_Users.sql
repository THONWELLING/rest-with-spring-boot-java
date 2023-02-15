CREATE TABLE users (
  "id" serial not null PRIMARY KEY,
  "user_name" varchar(255),
  "full_name" varchar(255),
  "password" varchar(255),
  "account_non_expired" bit(1),
  "account_non_locked" bit(1),
  "credentials_non_expired" bit(1),
  "enabled" bit(1),
  UNIQUE(user_name)
);