import {Component, OnInit} from '@angular/core';
import {AuthorDTO} from "../../../dto/author/authorDTO";
import {AuthorService} from "../../../service/author/author.service";
import {ActivatedRoute, RouterLink} from "@angular/router";

@Component({
  selector: 'app-author-info',
  standalone: true,
  imports: [
    RouterLink
  ],
  template: `
    @if (author) {
      <h1>{{author.firstName + ' ' + author.lastName}}</h1>
    } @else {
      <h1>no author found:(</h1>
    }
    <a routerLink="/authors/">go back</a>
  `,
  styles: ``
})
export class AuthorInfoComponent implements OnInit {
  author: AuthorDTO|null = null;

  constructor(private readonly authorService: AuthorService,
              private readonly route: ActivatedRoute) {}

  fetchAuthor(id: number) {
    this.authorService.getAuthor(id)
      .subscribe({
        next: (response) => {
          this.author = response;
        },
        error: (error) => {
          console.error(error)
        }
      })
  }

  ngOnInit() {
    const authorId = +this.route.snapshot.paramMap.get('id')!;
    this.fetchAuthor(authorId)
  }
}
