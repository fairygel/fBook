import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {BookTypeIndexViewDTO} from "../../dto/book/type/bookTypeIndexViewDTO";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class BookTypeService {
  constructor(private readonly http: HttpClient) {
  }

  getBookTypes(): Observable<BookTypeIndexViewDTO[]> {
    return this.http.get<BookTypeIndexViewDTO[]>(`/api/book-types`);
  }
}
