import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {BookFullViewDTO} from "../../../dto/book/bookFulViewDTO";
import {BookService} from "../../../service/book/book.service";

@Component({
  selector: 'app-book-info',
  standalone: true,
  imports: [
      RouterLink
  ],
  template: `
    @if (isLoading) {
      <p>loading data..</p>
    } @else if (book) {
      <h1>{{book.name}}</h1>
      <p>annotation: {{book.annotation}}</p>
      <p>author: {{book.authorFirstName}} {{book.authorLastName}}</p>
      <p>type: {{book.bookType}}</p>
      <p>status: {{book.bookStatus}}</p>
      <p>genres: @for (genre of book.genres; track genre; let last = $last) { 
          {{genre.toLowerCase()}}{{ !last ? ', ' : '' }} 
      }</p>
      <button (click)="deleteBook()" [disabled]="isLoading">delete book</button>
    } @else {
        <h1>no book found:(</h1>
    }
    <hr />
  <a routerLink="/">see your books..</a>
  `,
  styles: ``
})
export class BookInfoComponent implements OnInit{
  isLoading: boolean = true;
  book: BookFullViewDTO|null = null;
  id: number = -1;

  constructor(private readonly bookService: BookService,
              private readonly route: ActivatedRoute,
              private readonly router: Router) {}

  fetchBook(id: number) {
    this.id = id;
    this.bookService.getBook(id)
        .subscribe({
          next: (response) => {
            this.book = response;
            this.isLoading = false;
          },
          error: (error) => {
            console.error(error)
            this.isLoading = false;
          }
        })
  }

  ngOnInit() {
    const bookId = +this.route.snapshot.paramMap.get('id')!;
    this.fetchBook(bookId)
  }

  deleteBook() {
    if(this.isLoading) return;
    if(!confirm('are you sure you want to delete this book?')) return;

    this.isLoading = true;

    this.bookService.deleteBook(this.id)
        .subscribe({
          next: () => {
            this.router.navigate(['/']).then();
          },
          error: (error) => {
            console.error(error)
          }
        })
  }
}
