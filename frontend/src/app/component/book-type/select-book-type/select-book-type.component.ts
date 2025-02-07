import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BookTypeIndexViewDTO} from "../../../dto/book/type/bookTypeIndexViewDTO";
import {BookTypeService} from "../../../service/book-type/book-type.service";

@Component({
    selector: 'app-select-book-type',
    standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
    templateUrl: 'select-book-type.html',
    styles: ``
})
export class SelectBookTypeComponent implements OnInit {
    isLoading = true;
    allBookTypes: BookTypeIndexViewDTO[] = [];

    bookTypeControl = new FormControl<number>(0);

    @Input() bookTypeToShow: BookTypeIndexViewDTO|null = null;
    @Output() onBookTypeSelected = new EventEmitter<number>();

    constructor(private readonly bookTypeService: BookTypeService) {
        this.controlValueChangeOfBookType()
    }

    controlValueChangeOfBookType() {
        this.bookTypeControl.valueChanges.subscribe((bookTypeId) => {
            if (bookTypeId)
                this.onBookTypeSelected.emit(bookTypeId);
        });
    }

    ngOnInit(): void {
        this.fetchBookTypes();
    }

    fetchBookTypes() {
        this.bookTypeService.getBookTypes().subscribe({
            next: (response) => {
                this.allBookTypes = response;
                this.isLoading = false;
            },
            error: (error) => {
                console.error(error);
            }
        });
    }
}
