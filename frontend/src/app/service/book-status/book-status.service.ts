import { Injectable } from '@angular/core';
import {BookStatusDTO} from "../../dto/book/status/bookStatusDTO";
import {Observable} from "rxjs";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class BookStatusService {
  constructor(private readonly http: HttpClient) {
  }

  getBookStatuses(): Observable<BookStatusDTO[]> {
    return this.http.get<BookStatusDTO[]>(`/api/book-statuses`);
  }
}
