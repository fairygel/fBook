import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../service/book/book.service";
import {IndexBookViewDTO} from "../../../dto/book/indexBookViewDTO";
import {RouterLink} from "@angular/router";
import {CreateBookComponent} from "../create-book/create-book.component";
import {Title} from "@angular/platform-browser";

@Component({
    selector: 'books-root',
    standalone: true,
    imports: [
        RouterLink,
        CreateBookComponent
    ],
    templateUrl: 'book.html',
    styles: ``
})
export class BookComponent implements OnInit {
    books: IndexBookViewDTO[] = [];
    isMenuOpened: boolean = false;
    isLoading: boolean = true;

    constructor(private readonly bookService: BookService,
                private readonly pageTitle: Title) {
        this.pageTitle.setTitle('fBook');
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
