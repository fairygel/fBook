import {
    Component,
    EventEmitter,
    Input,
    Output
} from '@angular/core';
import {GenericSelect} from "../../../base/generic-select/generic-select.component";
import {BookTypeService} from "../../../service/book-type/book-type.service";
import {OptionDTO} from "../../../base/generic-select/optionDTO";
import {BookTypeDTO} from "../../../dto/book/type/bookTypeDTO";

@Component({
    selector: 'app-select-book-type',
    standalone: true,
    imports: [GenericSelect],
    template: `
        <app-generic-select
                [toOption]="toOption"
                [shownOption]="toOption(bookTypeToShow)"
                (optionSelectedEvent)="handleOptionSelected($event)"
                objectName="Book Type"
                [loadMethod]="loadTypes">
        </app-generic-select>
    `,
    styles: `* { --label-width: 97px }`
})
export class SelectBookTypeComponent {
    @Output() bookTypeSelectedEvent = new EventEmitter<number>();
    @Input() bookTypeToShow: BookTypeDTO|null = null;

    constructor(private readonly bookTypeService: BookTypeService) {}

    toOption(bookType: any): OptionDTO {
        if (!bookType) return {id: 0, name: ''};

        let raw = bookType as BookTypeDTO;
        return {
            id: raw.id,
            name: raw.type
        };
    }

    loadTypes = () => {
        return this.bookTypeService.getBookTypes();
    }

    handleOptionSelected(selectedOption: OptionDTO| null): void {
        if (!selectedOption) return;

        this.bookTypeToShow = {
            id: selectedOption.id,
            type: selectedOption.name
        };
        this.bookTypeSelectedEvent.emit(selectedOption.id);
    }
}
