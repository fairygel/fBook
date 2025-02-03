import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../service/book/book.service";
import {IndexBookViewDTO} from "../../../dto/book/indexBookViewDTO";
import {RouterLink} from "@angular/router";
import {CreateBookComponent} from "../create-book/create-book.component";

@Component({
    selector: 'books-root',
    standalone: true,
    imports: [
        RouterLink,
        CreateBookComponent
    ],
    template: `
        @if (isLoading) {
            <p>loading your books...</p>
        }
        @else if (books && books.length > 0) {
            <ul>
                @for (book of books; track book.id) {
                    <a routerLink="/books/{{book.id}}">{{ book.name }}</a>
                    <br />
                }
            </ul>
        } @else {
            <p>no books found :(</p>
        }

        @if (!isMenuOpened) {
            <button (click)="openMenu()">+</button>
        } @else {
            <button (click)="closeMenu()">x</button>
            <app-create-book (onBookCreated)="createBook()"/>
        }
    `,
    styles: ``
})
export class BookComponent implements OnInit {
    books: IndexBookViewDTO[] = [];
    isMenuOpened: boolean = false;
    isLoading: boolean = true;

    constructor(private readonly bookService: BookService) {
    }

    openMenu() {
        this.isMenuOpened = true;
    }

    closeMenu() {
        this.isMenuOpened = false;
    }

    fetchBooks() {
        this.bookService.getBooks()
            .subscribe({
                next: (response) => {
                    this.books = response;
                    this.isLoading = false;
                },
                error: (error) => {
                    console.error(error);
                    this.isLoading = false;
                }
            })
    }

    ngOnInit(): void {
        this.fetchBooks();
    }

    createBook() {
        this.closeMenu();
        this.fetchBooks();
    }
}
