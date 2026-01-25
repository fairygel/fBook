CREATE TABLE book_covers
(
    book_id    BIGINT  NOT NULL,
    cover      BYTEA NOT NULL,
    image_type VARCHAR NOT NULL,
    CONSTRAINT book_covers_pkey PRIMARY KEY (book_id)
);
