import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";

import {ApiError} from "../../../error/api-error";

import {UpdateBookDTO} from "../../../dto/book/updateBookDTO";
import {BookFullViewDTO} from "../../../dto/book/bookFulViewDTO";
import {GenreIndexViewDTO} from "../../../dto/genre/genreIndexViewDTO";
import {AuthorIndexViewDTO} from "../../../dto/author/authorIndexViewDTO";
import {BookTypeIndexViewDTO} from "../../../dto/book/type/bookTypeIndexViewDTO";
import {BookStatusIndexViewDTO} from "../../../dto/book/status/bookStatusIndexViewDTO";

import {BookService} from "../../../service/book/book.service";
import {GenreService} from "../../../service/genre/genre.service";
import {AuthorService} from "../../../service/author/author.service";
import {BookTypeService} from "../../../service/book-type/book-type.service";
import {BookStatusService} from "../../../service/book-status/book-status.service";

@Component({
    selector: 'app-book-info',
    standalone: true,
    imports: [
        RouterLink,
        FormsModule,
        ReactiveFormsModule
    ],
    templateUrl: 'book-info.html',
    styles: ``
})
export class BookInfoComponent implements OnInit {
    isLoading: boolean = true;
    id: number = -1;

    book: BookFullViewDTO | null = null;

    genres: GenreIndexViewDTO[] = [];
    authors: AuthorIndexViewDTO[] = [];
    bookTypes: BookTypeIndexViewDTO[] = [];
    bookStatuses: BookStatusIndexViewDTO[] = [];

    bookForm = new FormGroup({
        name: new FormControl(''),
        annotation: new FormControl(''),
        author: new FormControl(null),
        bookType: new FormControl(null),
        bookStatus: new FormControl(null),
        startedReadDate: new FormControl<string|null>(null),
        endedReadDate: new FormControl<string|null>(null),
        genres: new FormControl([])
    });

    authorForm = new FormGroup({
        fullName: new FormControl('')
    });

    genreForm = new FormGroup({
        genre: new FormControl('')
    });

    constructor(private readonly bookService: BookService,
                private readonly genreService: GenreService,
                private readonly authorService: AuthorService,
                private readonly bookTypeService: BookTypeService,
                private readonly bookStatusService: BookStatusService,
                private readonly route: ActivatedRoute,
                private readonly router: Router) {
    }

    fetchAuthors() {
        this.authorService.getAuthors().subscribe({
            next: (response) => {
                this.authors = response;
            },
            error: (error) => {
                console.error(error);
            }
        });
    }

    fetchBookTypes() {
        this.bookTypeService.getBookTypes().subscribe({
            next: (response) => {
                this.bookTypes = response;
            },
            error: (error) => {
                console.error(error);
            }
        });
    }

    fetchBookStatuses() {
        this.bookStatusService.getBookStatuses().subscribe({
            next: (response) => {
                this.bookStatuses = response;
            },
            error: (error) => {
                console.error(error);
            }
        });
    }

    fetchGenres() {
        this.genreService.getGenres().subscribe({
            next: (response) => {
                this.genres = response;
            },
            error: (error) => {
                console.error(error);
            }
        });
    }

    fetchBook(id: number) {
        this.id = id;

        this.bookService.getBook(id)
            .subscribe({
                next: (response) => {
                    this.book = response;

                    this.bookForm.patchValue({
                        name: response.name,
                        annotation: response.annotation,
                        genres: [],
                        author: null,
                        bookType: null,
                        bookStatus: null,
                        startedReadDate: response.startedReadDate,
                        endedReadDate: response.endedReadDate
                    })

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
        this.fetchAuthors();
        this.fetchBookTypes();
        this.fetchBookStatuses();
        this.fetchGenres();
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

    handleSubmit() {
        if (this.isLoading) return;

        this.isLoading = true;

        const book: UpdateBookDTO = {
            name: this.bookForm.get('name')?.value ?? '',
            annotation: this.bookForm.get('annotation')?.value ?? null,
            authorId: Number(this.bookForm.get('author')?.value) || null,
            genreIds: this.bookForm.get('genres')?.value ?? [],
            bookTypeId: Number(this.bookForm.get('bookType')?.value) || null,
            bookStatusId: Number(this.bookForm.get('bookStatus')?.value ?? null),
            startedReadDate: this.bookForm.get('startedReadDate')?.value || null,
            endedReadDate: this.bookForm.get('endedReadDate')?.value || null,
        }

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

    createAuthor() {
        if (this.isLoading) return;

        this.isLoading = true;

        this.authorService.createAuthor(this.authorForm.get('fullName')?.value ?? '').subscribe(
            {
                next: () => {
                    this.authorForm.reset();
                    this.fetchAuthors();
                    this.isLoading = false;
                },
                error: (error: HttpErrorResponse) => {
                    const apiError: ApiError = error.error;
                    alert(apiError.description);
                    this.isLoading = false;
                }
            }
        )
    }
    createGenre() {
        if (this.isLoading) return;

        this.isLoading = true;

        this.genreService.createGenre(this.genreForm.get('genre')?.value?? '').subscribe(
            {
                next: () => {
                    this.genreForm.reset();
                    this.fetchGenres();
                    this.isLoading = false;
                },
                error: (error: HttpErrorResponse) => {
                    const apiError: ApiError = error.error;
                    alert(apiError.description);
                    this.isLoading = false;
                }
            }
        )
    }
}
