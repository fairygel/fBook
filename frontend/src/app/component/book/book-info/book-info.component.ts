import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";

import {ApiError} from "../../../error/api-error";
import {BookService} from "../../../service/book/book.service";
import {UpdateBookDTO} from "../../../dto/book/updateBookDTO";
import {BookFullViewDTO} from "../../../dto/book/bookFulViewDTO";

import {SelectGenreComponent} from "../../genre/select-genre/select-genre.component";
import {SelectAuthorComponent} from "../../author/select-author/select-author.component";
import {SelectBookTypeComponent} from "../../book-type/select-book-type/select-book-type.component";
import {SelectBookStatusComponent} from "../../book-status/select-book-status/select-book-status.component";
import {GenreIndexViewDTO} from "../../../dto/genre/genreIndexViewDTO";
import {AuthorIndexViewDTO} from "../../../dto/author/authorIndexViewDTO";
import {BookTypeIndexViewDTO} from "../../../dto/book/type/bookTypeIndexViewDTO";
import {BookStatusIndexViewDTO} from "../../../dto/book/status/bookStatusIndexViewDTO";

@Component({
    selector: 'app-book-info',
    standalone: true,
    imports: [
        RouterLink,
        FormsModule,
        ReactiveFormsModule,
        SelectGenreComponent,
        SelectAuthorComponent,
        SelectBookTypeComponent,
        SelectBookStatusComponent
    ],
    templateUrl: 'book-info.html',
    styles: ``
})
export class BookInfoComponent implements OnInit {
    isLoading: boolean = true;
    id: number = -1;

    genresToUpdate: number[] = [];
    authorToUpdate: number|null = null;
    bookTypeToUpdate: number|null = null;
    bookStatusToUpdate: number|null = null;

    book: BookFullViewDTO|null = null;

    bookGenres: GenreIndexViewDTO[] = [];
    author: AuthorIndexViewDTO|null = null;
    bookType: BookTypeIndexViewDTO|null = null;
    bookStatus: BookStatusIndexViewDTO|null = null;

    bookForm = new FormGroup({
        name: new FormControl(''),
        annotation: new FormControl(''),
        startedReadDate: new FormControl<string|null>(null),
        endedReadDate: new FormControl<string|null>(null)
    });

    constructor(private readonly bookService: BookService,
                private readonly route: ActivatedRoute,
                private readonly router: Router) {
    }

    fetchBook(id: number) {
        this.id = id;

        this.bookService.getBook(id)
            .subscribe({
                next: (response) => {
                    this.fillBookWithData(response);
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
        this.fetchBook(bookId);
    }

    deleteBook() {
        if (this.isLoading) return;
        if (!confirm('are you sure you want to delete this book?')) return;

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

    handleUpdateBookSubmit() {
        if (this.isLoading) return;

        this.isLoading = true;

        const book = this.parseBookFromForm();

        this.bookService.updateBook(this.id, book).subscribe({
            next: () => {
                this.fetchBook(this.id);
                this.isLoading = false;
            },
            error: (error: HttpErrorResponse) => {
                const apiError: ApiError = error.error;
                alert(apiError.description);
                this.isLoading = false;
            }
        });

    }

    private fillBookWithData(response: BookFullViewDTO) {
        this.book = response;

        this.bookGenres = response.genres;
        this.author = response.author;
        this.bookType = response.bookType;
        this.bookStatus = response.bookStatus;

        this.bookForm.patchValue({
            name: response.name,
            annotation: response.annotation,
            startedReadDate: response.startedReadDate,
            endedReadDate: response.endedReadDate
        })
    }

    private parseBookFromForm() : UpdateBookDTO {
        return {
            name: this.bookForm.get('name')?.value ?? '',
            annotation: this.bookForm.get('annotation')?.value ?? null,
            endedReadDate: this.bookForm.get('endedReadDate')?.value || null,
            startedReadDate: this.bookForm.get('startedReadDate')?.value || null,

            authorId: this.authorToUpdate,
            genreIds: this.genresToUpdate,
            bookTypeId: this.bookTypeToUpdate,
            bookStatusId: this.bookStatusToUpdate,
        }
    }

    handleSelectedGenres(selectedGenres: number[]) {
        this.genresToUpdate = selectedGenres;
    }

    handleSelectedAuthor(selectedAuthor: number) {
        this.authorToUpdate = selectedAuthor;
    }

    handleSelectedBookType(selectedBookType: number) {
        this.bookTypeToUpdate = selectedBookType;
    }

    handleSelectedBookStatus(selectedBookStatus: number) {
        this.bookStatusToUpdate = selectedBookStatus;
    }
}
