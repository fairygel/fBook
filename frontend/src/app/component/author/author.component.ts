import {Component, OnInit} from '@angular/core';
import {AuthorService} from "../../service/author.service";
import {AuthorIndexViewDTO} from "../../dto/author/authorIndexViewDTO";

@Component({
  selector: 'authors-root',
  standalone: true,
  imports: [],
  template: `
    @if (authors && authors.length > 0) {
      <ul>
        @for (author of authors; track author.id) {
          <li>{{ author.fullName }}</li>
        }
      </ul>
    } @else {
      <p>No authors found.</p>
    }
    <a href="/">go back</a>
  `,
  styles: ``
})
export class AuthorComponent implements OnInit {
  authors: AuthorIndexViewDTO[] = [];

  constructor(private readonly authorService: AuthorService) { }

  fetchAuthors() {
    this.authorService.getAuthors()
      .subscribe(
        (response) => {
          this.authors = response
        },
        (error) => {
          console.error(error);
        }
      )
  }

  ngOnInit(): void {
    this.fetchAuthors()
  }
}
