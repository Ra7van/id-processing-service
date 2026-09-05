--liquibase formatted sql

--changeset razvi:02-image-table-creation
CREATE TABLE image_data (
    id BIGINT PRIMARY KEY,
    image_data BYTEA,
    CONSTRAINT fk_image_identity_card
        FOREIGN KEY (id) REFERENCES identity_cards(id)
);