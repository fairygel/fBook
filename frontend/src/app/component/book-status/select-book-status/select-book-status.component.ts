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
    isLoading = true;
    allBookStatuses: BookStatusIndexViewDTO[] = [];

    bookStatusControl = new FormControl<number>(0);

    @Input() bookStatusToShow: string = "";
    @Output() onBookStatusSelected = new EventEmitter<number>();

    constructor(private readonly bookStatusService: BookStatusService) {
        this.controlValueChangeOfBookStatus()
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
                this.isLoading = false;
            },
            error: (error) => {
                console.error(error);
            }
        });
    }
}
