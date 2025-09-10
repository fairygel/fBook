import {
    AfterViewInit,
    Component,
    EventEmitter,
    Input,
    Output, ViewChild
} from '@angular/core';
import {GenericSelect} from "../../../base/generic-select/generic-select.component";
import {BookTypeService} from "../../../service/book-type/book-type.service";
import {OptionDTO} from "../../../base/generic-select/optionDTO";
import {BookTypeIndexViewDTO} from "../../../dto/book/type/bookTypeIndexViewDTO";

@Component({
    selector: 'app-select-book-type',
    standalone: true,
    imports: [GenericSelect],
    template: `
        <app-generic-select
                #select
                [toOption]="toOption"
                [shownOption]="toOption(bookTypeToShow)"
                (onOptionSelected)="handleOptionSelected($event)"
                objectName="Book Type">
        </app-generic-select>
    `,
    styles: `* { --label-width: 97px }`
})
export class SelectBookTypeComponent implements AfterViewInit {
    @ViewChild('select') modal!: GenericSelect;
    @Output() onBookTypeSelected = new EventEmitter<number>();
    @Input() bookTypeToShow: BookTypeIndexViewDTO|null = null;

    constructor(private readonly bookTypeService: BookTypeService) {}

    ngAfterViewInit(): void {
        this.modal.loadOptions(() => this.bookTypeService.getBookTypes());
    }

    toOption(bookStatuses: any): OptionDTO {
        if (!bookStatuses) return {id: 0, name: ''};

        let raw = bookStatuses as BookTypeIndexViewDTO;
        return {
            id: raw.id,
            name: raw.type
        };
    }

    handleOptionSelected(selectedOption: OptionDTO| null): void {
        if (!selectedOption) return;

        this.bookTypeToShow = {
            id: selectedOption.id,
            type: selectedOption.name
        };
        this.onBookTypeSelected.emit(selectedOption.id);
    }
}
