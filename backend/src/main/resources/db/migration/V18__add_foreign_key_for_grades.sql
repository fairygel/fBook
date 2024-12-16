ALTER TABLE "grades"
    ADD CONSTRAINT grades_book_id_fkey FOREIGN KEY (book_id) REFERENCES "books" (id) ON DELETE NO ACTION;