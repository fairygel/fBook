import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {GenreService} from "../../../service/genre/genre.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../../error/api-error";

@Component({
    selector: 'app-create-genre',
    standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
    templateUrl: 'create-genre.html',
    styleUrl: `create-genre.scss`
})
export class CreateGenreComponent {
    isLoading: boolean = false;

    genreForm = new FormGroup({
        genre: new FormControl('')
    });

    @Output() genreCreatedEvent = new EventEmitter();

    constructor(private readonly genreService: GenreService){}

    createGenre() {
        if (this.isLoading) return;

        this.changeLoading(false);

        this.genreService.createGenre(this.genreForm.get('genre')?.value ?? '').subscribe(
            {
                next: () => {
                    this.genreForm.reset();
                    this.genreCreatedEvent.emit();
                    this.changeLoading(false)
                },
                error: (error: HttpErrorResponse) => {
                    const apiError: ApiError = error.error;
                    alert(apiError.description);
                    this.changeLoading(false);
                }
            }
        )
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
