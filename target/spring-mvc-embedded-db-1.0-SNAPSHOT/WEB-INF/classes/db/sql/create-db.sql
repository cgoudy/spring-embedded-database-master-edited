DROP TABLE notes IF EXISTS;

CREATE TABLE notes (
  id         	int GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1),
  body 			varchar(100),
);