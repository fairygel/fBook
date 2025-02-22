import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GenreService} from "../../../service/genre/genre.service";
import {GenreIndexViewDTO} from "../../../dto/genre/genreIndexViewDTO";
import {CreateGenreComponent} from "../create-genre/create-genre.component";
import {FormControl, ReactiveFormsModule} from "@angular/forms";

@Component({
    selector: 'app-select-genre',
    standalone: true,
    imports: [
        CreateGenreComponent,
        ReactiveFormsModule
    ],
    templateUrl: 'select-genre.html',
    styleUrl: `select-genre.scss`
})
export class SelectGenreComponent implements OnInit {
    isLoading = false;
    allGenres: GenreIndexViewDTO[] = [];

    genresControl = new FormControl<number[]>([]);

    @Input() genresToShow: GenreIndexViewDTO[] = [];
    @Output() onGenreSelected = new EventEmitter<number[]>();

    constructor(private readonly genreService: GenreService) {
        this.controlValueChangeOfGenres()
        this.changeLoading(true);
    }

    controlValueChangeOfGenres() {
        this.genresControl.valueChanges.subscribe((genreIds) => {
            if (genreIds && genreIds.length > 0)
                this.onGenreSelected.emit(genreIds);
        });
    }

    ngOnInit(): void {
        this.fetchGenres();
    }

    handleGenreCreation() {
        this.changeLoading(true);
        this.fetchGenres();
    }

    fetchGenres() {
        this.genreService.getGenres().subscribe({
            next: (response) => {
                this.allGenres = response;
                this.changeLoading(false);
            },
            error: (error) => {
                console.error(error);
                this.changeLoading(false);
            }
        });
    }
    changeLoading(value: boolean) {
        if (value === this.isLoading) return;
        this.isLoading = value;

        if (this.isLoading) {
            this.genresControl.disable();
        } else {
            this.genresControl.enable();
        }
    }
}
