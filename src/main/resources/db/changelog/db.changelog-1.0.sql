--liquibase formatted sql

--changeset Savitsky_E:1
CREATE TABLE IF NOT EXISTS locations
(
    id            BIGSERIAL CONSTRAINT locations_pk PRIMARY KEY,
    "name"        VARCHAR(100)             NOT NULL,
    "region"      VARCHAR(100)             NOT NULL,
    "country"     VARCHAR(100)             NOT NULL,
    lat           FLOAT4                   NOT NULL,
    lon           FLOAT4                   NOT NULL,
    time_zone     VARCHAR(100)             NOT NULL
);


CREATE TABLE IF NOT EXISTS weathers
(
    id           BIGSERIAL CONSTRAINT weathers_pk PRIMARY KEY,
    temp         FLOAT4        NOT NULL,
    wind         FLOAT4        NOT NULL,
    pressure     FLOAT4        NOT NULL,
    humidity     FLOAT4        NOT NULL,
    "condition"  VARCHAR(100)  NOT NULL,
    location_id  BIGINT NOT NULL CONSTRAINT weathers_location_id_fk REFERENCES locations,
    create_at    TIMESTAMP DEFAULT now()
);