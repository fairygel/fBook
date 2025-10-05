import {
    AfterViewInit,
    Component,
    EventEmitter,
    Input,
    Output, ViewChild
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
                #select
                [toOption]="toOption"
                [shownOption]="toOption(authorToShow)"
                (onOptionSelected)="handleOptionSelected($event)"
                [canCreate]="true"
                [createMethod]="createAuthor"
                [loadMethod]="loadAuthors"
                objectName="Author">
        </app-generic-select>
    `,
    styles: `* { --label-width: 64px }`
})
export class SelectAuthorComponent implements AfterViewInit {
    @ViewChild('select') modal!: GenericSelect;
    @Output() onAuthorSelected = new EventEmitter<number>();
    @Input() authorToShow: AuthorIndexViewDTO|null = null;

    constructor(private readonly authorService: AuthorService) {}

    ngAfterViewInit(): void {
        this.modal.loadOptions(() => this.authorService.getAuthors());
    }

    toOption(authors: any): OptionDTO {
        if (!authors) return {id: 0, name: ''};

        let raw = authors as AuthorIndexViewDTO;
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
            this.onAuthorSelected.emit(0);
            return;
        }

        this.authorToShow = {
            id: selectedOption.id,
            fullName: selectedOption.name
        };
        this.onAuthorSelected.emit(selectedOption.id);
    }
}
