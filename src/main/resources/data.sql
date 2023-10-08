INSERT INTO USERS(username, password, email)
  VALUES ('user', '{bcrypt}$2a$12$CaiILFsfaCm.tHzxqftc8e/GmWbL1TYVhtrH3TJJ6WPUXiRKXcXdu', 'user@mail.ru');

INSERT INTO ROLES(name)
  VALUES ( 'ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO USERS_ROLES (user_id, role_id) values (1, 2)