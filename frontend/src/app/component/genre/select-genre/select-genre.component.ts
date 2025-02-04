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
    styles: ``
})
export class SelectGenreComponent implements OnInit {
    isLoading = true;
    allGenres: GenreIndexViewDTO[] = [];

    genresControl = new FormControl<number[]>([]);

    @Input() genresToShow: string[] = [];
    @Output() onGenreSelected = new EventEmitter<number[]>();

    constructor(private readonly genreService: GenreService) {
        this.controlValueChangeOfGenres()
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
        this.isLoading = true;
        this.fetchGenres();
    }

    fetchGenres() {
        this.genreService.getGenres().subscribe({
            next: (response) => {
                this.allGenres = response;
                this.isLoading = false;
            },
            error: (error) => {
                console.error(error);
            }
        });
    }
}
