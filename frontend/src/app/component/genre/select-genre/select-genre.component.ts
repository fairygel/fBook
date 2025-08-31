import {
    Component,
    ElementRef,
    EventEmitter,
    HostListener,
    Input,
    OnInit,
    Output
} from '@angular/core';
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

    isDropdownOpened: boolean = false;
    isModalOpened: boolean = false;

    genresControl = new FormControl<number[]>([]);

    // at the input field, not on the dropdown
    @Input() genresToShow: GenreIndexViewDTO[] = [];
    @Output() onGenreSelected = new EventEmitter<number[]>();

    constructor(private readonly genreService: GenreService,
                private readonly elementRef: ElementRef) {
        this.changeLoading(true);
    }

    ngOnInit(): void {
        this.fetchGenres();
    }

    fetchGenres() {
        this.genreService.getGenres().subscribe({
            next: (response) => {
                this.allGenres = response;
                this.sortOptions()
                this.changeLoading(false);
            },
            error: (error) => {
                console.error(error);
                this.changeLoading(false);
            }
        });
    }

    toggleDropdown() {
        this.isDropdownOpened = !this.isDropdownOpened;

        if (!this.isDropdownOpened) {
            this.sortOptions();
        }
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

    onGenreChange(genre: GenreIndexViewDTO) {
        if (this.isSelected(genre)) {
            this.genresToShow = this.genresToShow.filter(g => g.id !== genre.id)
        } else {
            this.genresToShow.push(genre)
        }
        this.onGenreSelected.emit(this.genresToShow.map(g => g.id))
    }

    get shownGenres(): string {
        return this.genresToShow.map(g => g.genre).join(', ')
    }

    sortOptions() {
        return this.allGenres.sort((a, b) => {
            const aSelected = this.isSelected(a);
            const bSelected = this.isSelected(b);

            if (aSelected && !bSelected) return -1;
            if (!aSelected && bSelected) return 1;

            return a.genre.localeCompare(b.genre);
        });
    }
    isSelected(genre: GenreIndexViewDTO) {
        return this.genresToShow.some(g => g.id===genre.id)
    }

    openCreateModal() {
        this.isModalOpened = true;
    }

    closeCreateModal(isAdded: boolean) {
        this.isModalOpened = false;

        if (!isAdded) return;
        this.changeLoading(true);
        this.fetchGenres();
    }

    @HostListener('document:click', ['$event'])
    onDocumentClick(event: Event) {
        if (this.isModalOpened) return;

        if (!this.elementRef.nativeElement.contains(event.target))
            if (this.isDropdownOpened) this.toggleDropdown();
    }

    @HostListener('document:keydown.escape', ['$event'])
    onEscapePress() {
        if (this.isModalOpened) return;

        if (this.isDropdownOpened) {
            this.toggleDropdown();
        }
    }
}
