import {Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {GenreService} from "../../../service/genre/genre.service";
import {GenreIndexViewDTO} from "../../../dto/genre/genreIndexViewDTO";
import {CreateGenreComponent} from "../create-genre/create-genre.component";
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {NgClass} from "@angular/common";

@Component({
    selector: 'app-select-genre',
    standalone: true,
    imports: [
        CreateGenreComponent,
        ReactiveFormsModule,
        NgClass
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

    isDropdownOpened: boolean = false;

    constructor(private readonly genreService: GenreService,
                private readonly elementRef: ElementRef) {
        this.changeLoading(true);
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

    toggleDropdown() {
        this.isDropdownOpened = !this.isDropdownOpened;
    }

    @HostListener('document:click', ['$event'])
    closeDropdown(event: Event) {
        if (!this.elementRef.nativeElement.contains(event.target))
            if (this.isDropdownOpened) this.isDropdownOpened = false;
    }

    onGenreChange(genre: GenreIndexViewDTO) {
        if (this.isSelected(genre)) {
            this.genresToShow = this.genresToShow.filter(g => g.id !== genre.id)
        } else {
            this.genresToShow.push(genre)
        }
        this.onGenreSelected.emit(this.genresToShow.map(g => g.id))
    }

    get shownGenres(): string {
        return this.genresToShow
                .map(g => g.genre)
                .join(', ')
    }

    isSelected(genre: GenreIndexViewDTO) {
        return this.genresToShow.some(g => g.id===genre.id)
    }
}
