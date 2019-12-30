# --- !Ups

CREATE TABLE todo (     
    id BIGINT  NOT NULL AUTO_INCREMENT,     
    name text NOT NULL,  
    done boolean NOT NULL,   
    priority TINYINT NOT NULL,
    PRIMARY KEY (id) 
);


# --- !Downs

DROP TABLE todo;

