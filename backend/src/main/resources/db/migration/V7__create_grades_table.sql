CREATE TABLE grades
(
    id      BIGINT   NOT NULL,
    book_id BIGINT   NOT NULL,
    rating  SMALLINT NOT NULL,
    comment TEXT,
    CONSTRAINT grades_pkey PRIMARY KEY (id)
);