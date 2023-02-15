CREATE TABLE user_permission (
  "id" serial primary key,
  "id_user" serial not null  REFERENCES users(id),
  "id_permission" serial not null REFERENCES permission(id)
);