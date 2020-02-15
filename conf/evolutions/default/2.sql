# --- !Ups

CREATE TABLE users (     
    id BIGINT  NOT NULL AUTO_INCREMENT,     
    username text NOT NULL,
	password text NOT NULL,  
	email text NOT NULL,
    PRIMARY KEY (id) 
);


# --- !Downs

DROP TABLE users;

