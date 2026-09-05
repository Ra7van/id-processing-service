--liquibase formatted sql

--changeset razvi:04-changed-id-to-uid
ALTER TABLE identity_cards ADD COLUMN uid VARCHAR(36);
ALTER TABLE identity_cards ADD CONSTRAINT uk_identity_cards_uid UNIQUE (uid);