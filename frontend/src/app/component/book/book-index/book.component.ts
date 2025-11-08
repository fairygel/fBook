import {Component, OnInit} from '@angular/core';
import {BookService} from "../../../service/book/book.service";
import {IndexBookViewDTO} from "../../../dto/book/indexBookViewDTO";
import {CreateBookComponent} from "../create-book/create-book.component";
import {Title} from "@angular/platform-browser";
import {BookInfoComponent} from "../book-info/book-info.component";
import {CommonModule} from "@angular/common";

@Component({
    selector: 'books-root',
    standalone: true,
    imports: [
        CreateBookComponent,
        BookInfoComponent,
        CommonModule
    ],
    templateUrl: 'book.html',
    styleUrl: 'book.scss'
})
export class BookComponent implements OnInit {
    books: IndexBookViewDTO[] = [];

    selectedBook: IndexBookViewDTO | null = null;
    isBookInfoModalOpen = false;

    isMenuOpened: boolean = false;
    isLoading: boolean = false;

    constructor(private readonly bookService: BookService,
                private readonly pageTitle: Title) {
        this.pageTitle.setTitle('fBook');
        this.changeLoading(true);
    }

    openBookInfoModal(book: IndexBookViewDTO) {
        this.selectedBook = book;
        this.isBookInfoModalOpen = true;
    }

    closeBookInfoModal() {
        this.selectedBook = null;
        this.isBookInfoModalOpen = false;
    }

    handleBookDeleted(bookId: number) {
        this.books = this.books.filter(b => b.id !== bookId);
    }

    handleBookUpdated() {
        this.fetchBooks();
    }

    toggleMenu() {
        this.isMenuOpened = !this.isMenuOpened;
    }

    fetchBooks() {
        this.bookService.getBooks()
            .subscribe({
                next: (response) => {
                    this.books = response;
                    this.changeLoading(false);
                    this.loadCovers();
                },
                error: (error) => {
                    console.error(error);
                    this.changeLoading(false);
                }
            })
    }

    loadCovers() {
        this.books.forEach(book => {
            this.bookService.getBookCover(book.id).subscribe({
                next: (blob) => {
                    book.coverUrl = URL.createObjectURL(blob);
                },
                error: (error) => {
                    console.error(error);
                }
            });

        });
    }

    ngOnInit(): void {
        this.fetchBooks();
    }

    createBook() {
        this.toggleMenu();
        this.fetchBooks();
    }

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;
    }

}
