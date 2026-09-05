--liquibase formatted sql

--changeset razvi:03-add-metadata-columns
ALTER TABLE image_data ADD COLUMN file_name VARCHAR(255);
ALTER TABLE image_data ADD COLUMN content_type VARCHAR(100);
ALTER TABLE image_data ADD COLUMN file_size BIGINT;
ALTER TABLE image_data ADD COLUMN created_at TIMESTAMP;