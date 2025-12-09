import {
    Component,
    EventEmitter,
    Input,
    Output
} from '@angular/core';
import {GenericSelect} from "../../../base/generic-select/generic-select.component";
import {OptionDTO} from "../../../base/generic-select/optionDTO";
import {AuthorIndexViewDTO} from "../../../dto/author/authorIndexViewDTO";
import {AuthorService} from "../../../service/author/author.service";

@Component({
    selector: 'app-select-author',
    standalone: true,
    imports: [GenericSelect],
    template: `
        <app-generic-select
                [toOption]="toOption"
                [shownOption]="toOption(authorToShow)"
                (optionSelectedEvent)="handleOptionSelected($event)"
                [createMethod]="createAuthor"
                [loadMethod]="loadAuthors"
                objectName="Author">
        </app-generic-select>
    `,
    styles: `* { --label-width: 64px }`
})
export class SelectAuthorComponent {
    @Output() authorSelectedEvent = new EventEmitter<number>();
    @Input() authorToShow: AuthorIndexViewDTO|null = null;

    constructor(private readonly authorService: AuthorService) {}

    toOption(author: any): OptionDTO {
        if (!author) return {id: 0, name: ''};

        let raw = author as AuthorIndexViewDTO;
        return {
            id: raw.id,
            name: raw.fullName
        };
    }

    createAuthor = (value: string) => {
        return this.authorService.createAuthor(value);
    }

    loadAuthors = () => {
        return this.authorService.getAuthors();
    }

    handleOptionSelected(selectedOption: OptionDTO|null): void {
        if (!selectedOption) {
            this.authorToShow = null;
            this.authorSelectedEvent.emit(0);
            return;
        }

        this.authorToShow = {
            id: selectedOption.id,
            fullName: selectedOption.name
        };
        this.authorSelectedEvent.emit(selectedOption.id);
    }
}
