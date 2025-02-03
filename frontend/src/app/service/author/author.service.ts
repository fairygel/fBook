import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {AuthorIndexViewDTO} from "../../dto/author/authorIndexViewDTO";
import {AuthorDTO} from "../../dto/author/authorDTO";

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private readonly http: HttpClient) {
  }

  getAuthors(): Observable<AuthorIndexViewDTO[]> {
    return this.http.get<AuthorIndexViewDTO[]>(`/api/authors`);
  }
  getAuthor(id: number): Observable<AuthorDTO> {
    return this.http.get<AuthorDTO>(`/api/authors/${id}`)
  }
  createAuthor(fullName: string): Observable<AuthorDTO> {
    let splitName = fullName.split(' ', 2);

    if (splitName.length < 2) {
      return this.http.post<AuthorDTO>(`/api/authors`, {firstName: splitName[0]});
    }

    return this.http.post<AuthorDTO>(`/api/authors`, {firstName: splitName[0], lastName: splitName[1]});
  }
}
