ALTER TABLE "books"
    ADD CONSTRAINT books_author_id_fkey FOREIGN KEY (author_id) REFERENCES "authors" (id) ON DELETE NO ACTION;
ALTER TABLE "books"
    ADD CONSTRAINT books_book_status_id_fkey FOREIGN KEY (book_status_id) REFERENCES "book_statuses" (id) ON DELETE NO ACTION;
ALTER TABLE "books"
    ADD CONSTRAINT books_book_type_id_fkey FOREIGN KEY (book_type_id) REFERENCES "book_types" (id) ON DELETE NO ACTION;