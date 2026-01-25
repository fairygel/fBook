import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {BookService} from "../../../service/book/book.service";
import {BookDTO} from "../../../dto/book/bookDTO";
import {ApiError, isValidationError} from "../../../error/api-error";
import {HttpErrorResponse} from "@angular/common/http";
import {SelectGenreComponent} from "../../genre/select-genre/select-genre.component";
import {SelectAuthorComponent} from "../../author/select-author/select-author.component";
import {SelectBookTypeComponent} from "../../book-type/select-book-type/select-book-type.component";
import {CommonModule} from "@angular/common";

@Component({
    selector: 'app-create-book',
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        SelectGenreComponent,
        SelectAuthorComponent,
        SelectBookTypeComponent
    ],
    templateUrl: 'create-book.html',
    styleUrl: `create-book.scss`
})
export class CreateBookComponent {
    isLoading = false;

    genreIds: number[] = [0];
    authorId: number = 0;
    bookTypeId: number = 0;

    cover: File|null = null;
    coverUrl: string = "";

    fieldErrors: Set<string> = new Set();

    @Output() onBookCreated = new EventEmitter();

    bookForm = new FormGroup({
        name: new FormControl(''),
        annotation: new FormControl('')
    });

    constructor(private readonly bookService: BookService) {
    }

    handleSubmit() {
        if (this.isLoading) return;

        this.fieldErrors = new Set();

        this.changeLoading(true);

        const book = this.parseBookFromForm();

        this.bookService.createBook(book, this.cover).subscribe({
            next: () => {
                this.bookForm.reset();
                this.onBookCreated.emit(true);
                this.changeLoading(false);
            },
            error: (error: HttpErrorResponse) => {
                const apiError: ApiError = error.error;
                let errorMessage = '';

                if (isValidationError(apiError)) {
                    const newErrors = new Set<string>();

                    errorMessage = `${apiError.message}\n\n`;

                    apiError.detail.forEach(detail => {
                        newErrors.add(detail.field);
                        errorMessage += `${detail.field}: ${detail.value}\n`;
                    });

                    this.fieldErrors = newErrors;
                } else {
                    errorMessage = apiError.message;
                }

                alert(errorMessage);
                this.changeLoading(false);
            }
        });
    }

    private parseBookFromForm(): BookDTO {
        return {
            name: this.bookForm.get('name')?.value ?? '',
            annotation: this.bookForm.get('annotation')?.value ?? undefined,
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

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;

        if (this.isLoading) {
            this.bookForm.disable();
        } else {
            this.bookForm.enable();
        }
    }

    onFileSelected(event: Event) {
        const input = event.target as HTMLInputElement;
        this.cover = input.files?.[0] || null;

        if (this.cover) this.generatePreview(this.cover);
    }

    private generatePreview(file: File): void {
        this.coverUrl = URL.createObjectURL(file);
    }

    onDrop(event: DragEvent) {
        this.handleDrag(event);

        if (event.dataTransfer?.files && event.dataTransfer.files[0]) {
            this.cover = event.dataTransfer.files[0];

            if (this.cover) this.generatePreview(this.cover);
        }
    }

    hasFieldError(fieldName: string): boolean {
        return this.fieldErrors.has(fieldName);
    }

    clearFieldError(fieldName: string): void {
        if (this.fieldErrors.has(fieldName)) {
            const newErrors = new Set(this.fieldErrors);
            newErrors.delete(fieldName);
            this.fieldErrors = newErrors;
        }
    }

    handleDrag(event: DragEvent) {
        event.preventDefault();
        event.stopPropagation();
    }
}
