import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {AuthorDTO} from "../dto/authorDTO";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private readonly http: HttpClient) {
  }

  getAuthors(): Observable<AuthorDTO[]> {
    return this.http.get<AuthorDTO[]>(`/api/authors`);
  }
}
