import { Injectable } from '@angular/core';

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

  createBook(book: CreateBookDTO) {
    return this.http.post<CreateBookDTO>('/api/books', book);
  }

  deleteBook(id: number) {
    return this.http.delete(`/api/books/${id}`);
  }

  updateBook(id: number, book: UpdateBookDTO) {
    return this.http.patch(`/api/books/${id}`, book)
  }
}
