# --- !Ups

CREATE TABLE TODO (     
    id BIGINT  NOT NULL AUTO_INCREMENT,     
    name text NOT NULL,     
    PRIMARY KEY (id) 
);


# --- !Downs

DROP TABLE TODO;

