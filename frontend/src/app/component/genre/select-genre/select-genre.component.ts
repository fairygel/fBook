import {
    Component,
    EventEmitter,
    Input,
    Output
} from '@angular/core';
import {GenreService} from "../../../service/genre/genre.service";
import {GenreIndexViewDTO} from "../../../dto/genre/genreIndexViewDTO";
import {GenericSelect} from "../../../base/generic-select/generic-select.component";
import {OptionDTO} from "../../../base/generic-select/optionDTO";
import {GenreDTO} from "../../../dto/genre/genreDTO";

@Component({
    selector: 'app-select-genre',
    standalone: true,
    imports: [GenericSelect],
    template: `
        <app-generic-select
                [toOption]="toOption"
                [shownOptionArray]="mappedGenres"
                (optionArraySelectedEvent)="handleOptionSelected($event)"
                [createMethod]="createGenre"
                [loadMethod]="loadGenres"
                objectName="Genres">
        </app-generic-select>
    `,
    styles: `* { --label-width: 68px }`
})
export class SelectGenreComponent {
    @Output() genreSelectedEvent = new EventEmitter<number[]>();
    @Input() genresToShow: GenreDTO[] = [];

    constructor(private readonly genreService: GenreService) {}

    get mappedGenres(): OptionDTO[] {
        return this.genresToShow.map(g => this.toOption(g));
    }

    toOption(genre: any): OptionDTO {
        if (!genre) return {id: 0, name: ''};

        let raw = genre as GenreIndexViewDTO;
        return {
            id: raw.id,
            name: raw.genre
        };
    }

    createGenre = (value: string) => {
        return this.genreService.createGenre(value);
    }

    loadGenres = () => {
        return this.genreService.getGenres();
    }

    handleOptionSelected(selectedOptions: OptionDTO[]| null): void {
        if (!selectedOptions) {
            this.genresToShow = [];
            this.genreSelectedEvent.emit([]);
            return;
        }

        this.genresToShow = selectedOptions.map(o => ({
            id: o.id,
            genre: o.name
        } as GenreDTO));
        this.genreSelectedEvent.emit(selectedOptions.map(o => o.id));
    }
}
