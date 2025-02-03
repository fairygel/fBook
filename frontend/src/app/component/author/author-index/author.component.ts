import {Component, OnInit} from '@angular/core';
import {AuthorService} from "../../../service/author/author.service";
import {AuthorIndexViewDTO} from "../../../dto/author/authorIndexViewDTO";
import {RouterLink} from "@angular/router";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'authors-root',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule
  ],
  template: `
    @if (authors && authors.length > 0) {
      <ul>
        @for (author of authors; track author.id) {
          <li><a routerLink="/authors/{{author.id}}">{{ author.fullName }}</a></li>
        }
      </ul>
    } @else {
      <p>No authors found.</p>
    }
    <form [formGroup]="authorForm" (ngSubmit)="handleSubmit()">
      <input type="text" formControlName="name">
      <button type="submit">create new author</button>
    </form>
    <a href="/">go back</a>
  `,
  styles: ``
})
export class AuthorComponent implements OnInit {
  authors: AuthorIndexViewDTO[] = [];

  authorForm = new FormGroup({
    name: new FormControl('')
  })

  constructor(private readonly authorService: AuthorService) { }

  fetchAuthors() {
    this.authorService.getAuthors()
      .subscribe({
        next: (response) => {
          this.authors = response;
        },
        error: (error) => {
          console.error(error)
        }
      })
  }

  ngOnInit(): void {
    this.fetchAuthors()
  }

  handleSubmit() {
    let authorName = this.authorForm.value.name;

    if (authorName)
      this.authorService.createAuthor(authorName).subscribe({
        next: () => {
          this.authorForm.reset();
          this.fetchAuthors()
        },
        error: (error) => {
          console.error(error)
        }
      });

  }
}
