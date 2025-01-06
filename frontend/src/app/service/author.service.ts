import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {AuthorIndexViewDTO} from "../dto/author/authorIndexViewDTO";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private readonly http: HttpClient) {
  }

  getAuthors(): Observable<AuthorIndexViewDTO[]> {
    return this.http.get<AuthorIndexViewDTO[]>(`/api/authors`);
  }
}
