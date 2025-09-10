import {
    AfterViewInit,
    Component, EventEmitter, Input, Output, ViewChild,
} from '@angular/core';
import {GenericSelect} from "../../../base/generic-select/generic-select.component";
import {BookStatusService} from "../../../service/book-status/book-status.service";
import {OptionDTO} from "../../../base/generic-select/optionDTO";
import {BookStatusIndexViewDTO} from "../../../dto/book/status/bookStatusIndexViewDTO";

@Component({
    selector: 'app-select-book-status',
    standalone: true,
    imports: [GenericSelect],
    template: `
        <app-generic-select
                #select
                [toOption]="toOption"
                [shownOption]="toOption(bookStatusToShow)"
                (onOptionSelected)="handleOptionSelected($event)"
                objectName="Book Status">
        </app-generic-select>
    `,
    styles: `* { --label-width: 106px }`
})
export class SelectBookStatusComponent implements AfterViewInit {
    @ViewChild('select') modal!: GenericSelect;
    @Output() onBookStatusSelected = new EventEmitter<number>();
    @Input() bookStatusToShow: BookStatusIndexViewDTO|null = null;

    constructor(private readonly bookStatusService: BookStatusService) {}

    ngAfterViewInit(): void {
        this.modal.loadOptions(() => this.bookStatusService.getBookStatuses());
    }

    toOption(bookStatuses: any): OptionDTO {
        let raw = bookStatuses as BookStatusIndexViewDTO;
        return {
            id: raw.id,
            name: raw.status
        };
    }

    handleOptionSelected(selectedOption: OptionDTO): void {
        this.bookStatusToShow = {
            id: selectedOption.id,
            status: selectedOption.name
        };
        this.onBookStatusSelected.emit(selectedOption.id);
    }
}
