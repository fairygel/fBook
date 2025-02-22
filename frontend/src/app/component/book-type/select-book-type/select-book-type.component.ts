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
    styleUrl: `select-book-type.scss`
})
export class SelectBookTypeComponent implements OnInit {
    isLoading = false;
    allBookTypes: BookTypeIndexViewDTO[] = [];

    bookTypeControl = new FormControl<number>(0);

    @Input() bookTypeToShow: BookTypeIndexViewDTO|null = null;
    @Output() onBookTypeSelected = new EventEmitter<number>();

    constructor(private readonly bookTypeService: BookTypeService) {
        this.controlValueChangeOfBookType()
        this.changeLoading(true);
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
                this.changeLoading(false);
            },
            error: (error) => {
                console.error(error);
                this.changeLoading(false);
            }
        });
    }

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;

        if (this.isLoading) {
            this.bookTypeControl.disable();
        } else {
            this.bookTypeControl.enable();
        }
    }
}
