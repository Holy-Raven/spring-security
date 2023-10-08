
CREATE TABLE IF NOT EXISTS users (
	username 			VARCHAR(250) 														NOT NULL,
	password 			VARCHAR(250) 														NOT NULL,
	enabled 			boolean 														NOT NULL,

	CONSTRAINT pk_users PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities (
	username 			VARCHAR(250) 														NOT NULL,
	authority 			VARCHAR(250) 														NOT NULL,

	CONSTRAINT fk_authorities_username FOREIGN KEY (username) REFERENCES users (username),
	CONSTRAINT uq_authorities_username UNIQUE (username, authority)
);