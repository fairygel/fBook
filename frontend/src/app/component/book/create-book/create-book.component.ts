import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {BookService} from "../../../service/book/book.service";
import {CreateBookDTO} from "../../../dto/book/createBookDTO";
import {ApiError} from "../../../error/api-error";
import {HttpErrorResponse} from "@angular/common/http";
import {AuthorIndexViewDTO} from "../../../dto/author/authorIndexViewDTO";
import {AuthorService} from "../../../service/author/author.service";
import {BookTypeIndexViewDTO} from "../../../dto/book/type/bookTypeIndexViewDTO";
import {BookTypeService} from "../../../service/book-type/book-type.service";
import {GenreService} from "../../../service/genre/genre.service";
import {GenreIndexViewDTO} from "../../../dto/genre/genreIndexViewDTO";

@Component({
    selector: 'app-create-book',
    standalone: true,
    imports: [
        ReactiveFormsModule
    ],
    template: `
        <hr/>

        <form [formGroup]="bookForm" (ngSubmit)="handleSubmit()">
            <label for="name">Name:</label>
            <input id="name" type="text" formControlName="name" [disabled]="isLoading">
            <br/>

            <label for="annotation">Annotation:</label>
            <input id="annotation" type="text" formControlName="annotation" [disabled]="isLoading">
            <br/>

            <label for="author">Author:</label>
            <select id="author" formControlName="author" [disabled]="isLoading">
                <option [value]="0">--select author--</option>

                @for (author of authors; track author.id) {
                    <option [value]="author.id">{{ author.fullName }}</option>
                }
            </select>

            <form [formGroup]="authorForm" (ngSubmit)="createAuthor()">
                <label for="fullName">author name:</label>
                <input id="fullName" type="text" formControlName="fullName" [disabled]="isLoading">
                <button type="submit" [disabled]="isLoading">create</button>
                <br/>
            </form>

            <label for="bookType">book type:</label>
            <select id="bookType" formControlName="bookType" [disabled]="isLoading">
                <option [value]="0">--select book type--</option>

                @for (bookType of bookTypes; track bookType.id) {
                    @if (bookType.id !== 0){
                        <option [value]="bookType.id">{{ bookType.type }}</option>
                    }
                }
            </select>
            <br/>

            <label for="genres">genres:</label>
            <select formControlName="genres" id="genres" [disabled]="isLoading" multiple>
                @for(genre of genres; track genre.id) {
                    <option [value]="genre.id">{{genre.genre}}</option>
                }
            </select>
            <br />
            
            <form [formGroup]="genreForm" (ngSubmit)="createGenre()">
                <label for="genre">genre:</label>
                <input id="genre" type="text" formControlName="genre" [disabled]="isLoading">
                <button type="submit" [disabled]="isLoading">create</button>
                <br/>
            </form>
            
            <button type="submit" [disabled]="isLoading">create book</button>
        </form>
    `,
    styles: ``
})
export class CreateBookComponent implements OnInit {
    isLoading = false;

    genres: GenreIndexViewDTO[] = [];
    authors: AuthorIndexViewDTO[] = [];
    bookTypes: BookTypeIndexViewDTO[] = [];

    @Output() onBookCreated = new EventEmitter();

    bookForm = new FormGroup({
        name: new FormControl(''),
        annotation: new FormControl(''),
        author: new FormControl('0'),
        bookType: new FormControl('0'),
        genres: new FormControl([])
    });

    authorForm = new FormGroup({
        fullName: new FormControl('')
    });

    genreForm = new FormGroup({
        genre: new FormControl('')
    });

    constructor(private readonly bookService: BookService,
                private readonly authorService: AuthorService,
                private readonly genreService: GenreService,
                private readonly bookTypeService: BookTypeService) {
    }

    ngOnInit(): void {
        this.fetchAuthors();
        this.fetchBookTypes();
        this.fetchGenres();
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

    handleSubmit() {
        if (this.isLoading) return;

        this.isLoading = true;

        const book: CreateBookDTO = {
            name: this.bookForm.get('name')?.value ?? '',
            annotation: this.bookForm.get('annotation')?.value ?? null,
            authorId: Number(this.bookForm.get('author')?.value) || 0,
            genreIds: this.bookForm.get('genres')?.value ?? [],
            bookTypeId: Number(this.bookForm.get('bookType')?.value) || 0
        }

        this.bookService.createBook(book).subscribe({
            next: () => {
                this.bookForm.reset();
                this.onBookCreated.emit(true);
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
