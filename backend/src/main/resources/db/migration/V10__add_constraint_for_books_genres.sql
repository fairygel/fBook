ALTER TABLE "books_genres"
    ADD CONSTRAINT books_genres_book_id_genre_id_key UNIQUE (book_id, genre_id);