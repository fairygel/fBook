import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { GenreService } from "../../../service/genre/genre.service";
import { HttpErrorResponse } from "@angular/common/http";
import { ApiError } from "../../../error/api-error";
import { CommonModule } from "@angular/common";

@Component({
    selector: 'app-create-genre',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule
    ],
    templateUrl: 'create-genre.html',
    styleUrl: 'create-genre.scss'
})
export class CreateGenreComponent {
    @Input() isOpen: boolean = false;
    @Output() genreCreatedEvent = new EventEmitter<void>();
    @Output() closeModalEvent = new EventEmitter<void>();

    isLoading: boolean = false;

    genreForm = new FormGroup({
        genre: new FormControl('', Validators.required)
    });

    constructor(private readonly genreService: GenreService) {}

    createGenre() {
        if (this.isLoading || this.genreForm.invalid) return;

        this.changeLoading(true);

        this.genreService.createGenre(this.genreForm.get('genre')?.value ?? '').subscribe({
            next: () => {
                this.genreForm.reset();
                this.genreCreatedEvent.emit();
                this.closeModal(); // закрываем модалку после успеха
                this.changeLoading(false);
            },
            error: (error: HttpErrorResponse) => {
                const apiError: ApiError = error.error;
                alert(apiError.description);
                this.changeLoading(false);
            }
        });
    }

    closeModal() {
        this.closeModalEvent.emit();
    }

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;

        if (this.isLoading) {
            this.genreForm.disable();
        } else {
            this.genreForm.enable();
        }
    }
}
