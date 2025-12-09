ALTER TABLE "book_covers"
    ADD CONSTRAINT book_covers_book_id_fkey FOREIGN KEY (book_id) REFERENCES "books" (id) ON DELETE NO ACTION;