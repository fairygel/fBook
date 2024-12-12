CREATE TABLE authors
(
    id        BIGINT  NOT NULL,
    firstname VARCHAR NOT NULL,
    lastname  VARCHAR,
    CONSTRAINT authors_pkey PRIMARY KEY (id)
);