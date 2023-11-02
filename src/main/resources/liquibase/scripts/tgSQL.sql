-- liquibase formatted sql

--changeset Enerlo:1
CREATE TABLE notification_task (
  id SERIAL,
  chatId INT,
  messageText VARCHAR(200),
  timeToSend TIMESTAMP
);

--changeset Enerlo:2
CREATE SEQUENCE notification_task_seq;