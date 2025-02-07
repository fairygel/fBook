import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {BookStatusIndexViewDTO} from "../../../dto/book/status/bookStatusIndexViewDTO";
import {BookStatusService} from "../../../service/book-status/book-status.service";

@Component({
    selector: 'app-select-book-status',
    standalone: true,
    imports: [
        ReactiveFormsModule
    ],
    templateUrl: "select-book-status.html",
    styles: ``
})
export class SelectBookStatusComponent implements OnInit {
    isLoading = false;
    allBookStatuses: BookStatusIndexViewDTO[] = [];

    bookStatusControl = new FormControl<number|null>(null);

    @Input() bookStatusToShow: BookStatusIndexViewDTO|null = null;
    @Output() onBookStatusSelected = new EventEmitter<number>();

    constructor(private readonly bookStatusService: BookStatusService) {
        this.controlValueChangeOfBookStatus()
        this.changeLoading(true);
    }

    controlValueChangeOfBookStatus() {
        this.bookStatusControl.valueChanges.subscribe((bookStatusId) => {
            if (bookStatusId)
                this.onBookStatusSelected.emit(bookStatusId);
        });
    }

    ngOnInit(): void {
        this.fetchBookTypes();
    }

    fetchBookTypes() {
        this.bookStatusService.getBookStatuses().subscribe({
            next: (response) => {
                this.allBookStatuses = response;
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
            this.bookStatusControl.disable();
        } else {
            this.bookStatusControl.enable();
        }
    }
}
