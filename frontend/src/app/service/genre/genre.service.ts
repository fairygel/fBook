import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import { HttpClient } from "@angular/common/http";
import {GenreDTO} from "../../dto/genre/genreDTO";

@Injectable({
  providedIn: 'root'
})
export class GenreService {
  constructor(private readonly http: HttpClient) {
  }

  getGenres(): Observable<GenreDTO[]> {
    return this.http.get<GenreDTO[]>(`/api/genres`);
  }

  createGenre(genre: string): Observable<GenreDTO> {
    return this.http.post<GenreDTO>(`/api/genres`, {genre: genre});
  }
}
