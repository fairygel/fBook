import {
    Component,
    ElementRef,
    EventEmitter,
    HostListener,
    Input,
    OnInit,
    Output,
    ViewEncapsulation
} from '@angular/core';
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {BookStatusIndexViewDTO} from "../../../dto/book/status/bookStatusIndexViewDTO";
import {BookStatusService} from "../../../service/book-status/book-status.service";
import {NgClass} from "@angular/common";

@Component({
    selector: 'app-select-book-status',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        NgClass
    ],
    templateUrl: "select-book-status.html",
    styleUrl: `select-book-status.scss`,
    encapsulation: ViewEncapsulation.None
})
export class SelectBookStatusComponent implements OnInit {
    isLoading = false;
    allBookStatuses: BookStatusIndexViewDTO[] = [];

    bookStatusControl = new FormControl<number|null>(null);

    @Input() bookStatusToShow: BookStatusIndexViewDTO|null = null;
    @Output() onBookStatusSelected = new EventEmitter<number>();
    isDropdownOpened: boolean = false;

    constructor(private readonly bookStatusService: BookStatusService,
                private readonly elementRef: ElementRef) {
        this.changeLoading(true);
    }

    ngOnInit(): void {
        this.fetchBookTypes();
    }

    fetchBookTypes() {
        this.bookStatusService.getBookStatuses().subscribe({
            next: (response) => {
                this.allBookStatuses = response;
                this.sortOptions();
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

    toggleDropdown() {
        this.isDropdownOpened = !this.isDropdownOpened;

        if (!this.isDropdownOpened) {
            this.sortOptions();
        }
    }

    sortOptions() {
        this.allBookStatuses.sort((a, b) => {
            if (this.bookStatusToShow && a.id === this.bookStatusToShow.id) return -1;
            if (this.bookStatusToShow && b.id === this.bookStatusToShow.id) return 1;

            return a.id - b.id;
        });
    }

    @HostListener('document:click', ['$event'])
    closeDropdown(event: Event) {
        if (!this.elementRef.nativeElement.contains(event.target))
            if (this.isDropdownOpened) this.toggleDropdown()
    }

    @HostListener('document:keydown.escape', ['$event'])
    onEscapePress() {
        if (this.isDropdownOpened) {
            this.toggleDropdown()
        }
    }

    isSelected(bookStatus: BookStatusIndexViewDTO): boolean {
        return bookStatus.id === this.bookStatusToShow?.id
    }

    onBookStatusChange(bookStatus: BookStatusIndexViewDTO) {
        if (this.isSelected(bookStatus)) {
            return;
        } else {
            this.bookStatusToShow = bookStatus
        }
        this.onBookStatusSelected.emit(this.bookStatusToShow?.id)
    }
}
