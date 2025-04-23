import {Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BookTypeIndexViewDTO} from "../../../dto/book/type/bookTypeIndexViewDTO";
import {BookTypeService} from "../../../service/book-type/book-type.service";
import {NgClass} from "@angular/common";

@Component({
    selector: 'app-select-book-type',
    standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule,
        NgClass
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
    isDropdownOpened: boolean = false;

    constructor(private readonly bookTypeService: BookTypeService,
                private readonly elementRef: ElementRef) {
        this.changeLoading(true);
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

    @HostListener('document:click', ['$event'])
    closeDropdown(event: Event) {
        if (!this.elementRef.nativeElement.contains(event.target))
            if (this.isDropdownOpened) this.isDropdownOpened = false;
    }

    toggleDropdown() {
        this.isDropdownOpened = !this.isDropdownOpened;
    }

    isSelected(bookStatus: BookTypeIndexViewDTO): boolean {
        return bookStatus.id === this.bookTypeToShow?.id;
    }

    onBookTypeChange(bookType: BookTypeIndexViewDTO) {
        if (this.isSelected(bookType)) {
            this.bookTypeToShow = null
        } else {
            this.bookTypeToShow = bookType
        }
        this.onBookTypeSelected.emit(this.bookTypeToShow?.id)

        this.isDropdownOpened = false;
    }
}
