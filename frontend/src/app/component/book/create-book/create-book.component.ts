import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {BookService} from "../../../service/book/book.service";
import {CreateBookDTO} from "../../../dto/book/createBookDTO";
import {ApiError} from "../../../error/api-error";
import {HttpErrorResponse} from "@angular/common/http";
import {SelectGenreComponent} from "../../genre/select-genre/select-genre.component";
import {SelectAuthorComponent} from "../../author/select-author/select-author.component";
import {SelectBookTypeComponent} from "../../book-type/select-book-type/select-book-type.component";

@Component({
    selector: 'app-create-book',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        SelectGenreComponent,
        SelectAuthorComponent,
        SelectBookTypeComponent
    ],
    templateUrl: 'create-book.html',
    styles: ``
})
export class CreateBookComponent {
    isLoading = false;

    genreIds: number[] = [0];
    authorId: number = 0;
    bookTypeId: number = 0;

    @Output() onBookCreated = new EventEmitter();

    bookForm = new FormGroup({
        name: new FormControl(''),
        annotation: new FormControl('')
    });

    constructor(private readonly bookService: BookService) {
    }

    handleSubmit() {
        if (this.isLoading) return;

        this.isLoading = true;

        const book = this.parseBookFromForm();

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

    private parseBookFromForm(): CreateBookDTO {
        return {
            name: this.bookForm.get('name')?.value ?? '',
            annotation: this.bookForm.get('annotation')?.value ?? null,
            authorId: this.authorId,
            genreIds: this.genreIds,
            bookTypeId: this.bookTypeId
        }
    }

    handleSelectedGenres(genres: number[]) {
        this.genreIds = genres;
    }

    handleSelectedAuthor(author: number) {
        this.authorId = author;
    }

    handleSelectedBookType(bookType: number) {
        this.bookTypeId = bookType;
    }
}
