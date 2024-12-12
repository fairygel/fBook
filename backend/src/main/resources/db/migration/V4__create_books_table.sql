CREATE TABLE books
(
    id                BIGINT   NOT NULL,
    name              VARCHAR  NOT NULL,
    author_id         BIGINT   NOT NULL,
    book_status_id    SMALLINT NOT NULL,
    ended_read_date   date,
    started_read_date date,
    annotation        TEXT,
    book_type_id      SMALLINT NOT NULL,
    grade_id          BIGINT,
    CONSTRAINT books_pkey PRIMARY KEY (id)
);