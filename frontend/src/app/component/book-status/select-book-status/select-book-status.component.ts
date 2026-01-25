import {
    Component, EventEmitter, Input, Output,
} from '@angular/core';
import {GenericSelect} from "../../../base/generic-select/generic-select.component";
import {BookStatusService} from "../../../service/book-status/book-status.service";
import {OptionDTO} from "../../../base/generic-select/optionDTO";
import {BookStatusDTO} from "../../../dto/book/status/bookStatusDTO";

@Component({
    selector: 'app-select-book-status',
    standalone: true,
    imports: [GenericSelect],
    template: `
        <app-generic-select
                [toOption]="toOption"
                [shownOption]="toOption(bookStatusToShow)"
                [loadMethod]="loadStatuses"
                (optionSelectedEvent)="handleOptionSelected($event)"
                objectName="Book Status">
        </app-generic-select>
    `,
    styles: `* { --label-width: 110px }`
})
export class SelectBookStatusComponent {
    @Output() bookStatusSelectedEvent = new EventEmitter<number>();
    @Input() bookStatusToShow: BookStatusDTO|null = null;

    constructor(private readonly bookStatusService: BookStatusService) {}

    toOption(bookStatus: any): OptionDTO {
        if (!bookStatus) return {id: 0, name: ''};

        let raw = bookStatus as BookStatusDTO;
        return {
            id: raw.id,
            name: raw.status
        };
    }

    loadStatuses = () => {
        return this.bookStatusService.getBookStatuses();
    }

    handleOptionSelected(selectedOption: OptionDTO|null): void {
        if (!selectedOption) return;

        this.bookStatusToShow = {
            id: selectedOption.id,
            status: selectedOption.name
        };
        this.bookStatusSelectedEvent.emit(selectedOption.id);
    }
}
