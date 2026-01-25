import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {BookTypeDTO} from "../../dto/book/type/bookTypeDTO";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class BookTypeService {
  constructor(private readonly http: HttpClient) {
  }

  getBookTypes(): Observable<BookTypeDTO[]> {
    return this.http.get<BookTypeDTO[]>(`/api/book-types`);
  }
}
