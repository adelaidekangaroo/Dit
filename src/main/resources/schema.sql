DROP TABLE IF EXISTS messages;

CREATE TABLE messages
(
    id      INT PRIMARY KEY AUTO_INCREMENT,
    content VARCHAR(400) NOT NULL
);