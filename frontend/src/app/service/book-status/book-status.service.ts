import { Injectable } from '@angular/core';
import {BookStatusIndexViewDTO} from "../../dto/book/status/bookStatusIndexViewDTO";
import {Observable} from "rxjs";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class BookStatusService {
  constructor(private readonly http: HttpClient) {
  }

  getBookStatuses(): Observable<BookStatusIndexViewDTO[]> {
    return this.http.get<BookStatusIndexViewDTO[]>(`/api/book-statuses`);
  }
}
