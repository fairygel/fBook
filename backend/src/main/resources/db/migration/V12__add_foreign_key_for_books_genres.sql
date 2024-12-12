ALTER TABLE "books_genres"
    ADD CONSTRAINT books_genres_book_id_fkey FOREIGN KEY (book_id) REFERENCES "books" (id) ON DELETE NO ACTION;

ALTER TABLE "books_genres"
    ADD CONSTRAINT books_genres_genre_id_fkey FOREIGN KEY (genre_id) REFERENCES "genres" (id) ON DELETE NO ACTION;