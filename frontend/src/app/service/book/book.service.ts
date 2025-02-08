import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";
import {IndexBookViewDTO} from "../../dto/book/indexBookViewDTO";
import {BookFullViewDTO} from "../../dto/book/bookFulViewDTO";
import {CreateBookDTO} from "../../dto/book/createBookDTO";
import {UpdateBookDTO} from "../../dto/book/updateBookDTO";

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private readonly http: HttpClient) { }

  getBooks() {
    return this.http.get<IndexBookViewDTO[]>('/api/books');
  }

  getBook(id: number) {
    return this.http.get<BookFullViewDTO>(`/api/books/${id}`);
  }

  getBookCover(id: number) {
    return this.http.get(`/api/books/${id}/cover`, { responseType: 'blob' });
  }

  createBook(book: CreateBookDTO, cover: File|null) {
    const formData = new FormData();

    formData.append('book', this.bookToBlob(book));
    if (cover) formData.append('cover', cover);

    return this.http.post('/api/books', formData);
  }

  private bookToBlob(bookDTO: any): Blob {
      return new Blob([JSON.stringify(bookDTO)], {type: 'application/json'});
  }

  deleteBook(id: number) {
    return this.http.delete(`/api/books/${id}`);
  }

  updateBook(id: number, book: UpdateBookDTO, cover: File|null) {
    const formData = new FormData();

    formData.append('book', this.bookToBlob(book))
    if (cover) formData.append('cover', cover);

    return this.http.patch(`/api/books/${id}`, formData)
  }
}
