import {
    Component,
    ElementRef,
    EventEmitter,
    HostListener,
    Input,
    OnInit,
    Output
} from '@angular/core';
import {CreateAuthorComponent} from "../create-author/create-author.component";
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthorIndexViewDTO} from "../../../dto/author/authorIndexViewDTO";
import {AuthorService} from "../../../service/author/author.service";
import {NgClass} from "@angular/common";

@Component({
    selector: 'app-select-author',
    standalone: true,
    imports: [
        CreateAuthorComponent,
        FormsModule,
        ReactiveFormsModule,
        NgClass
    ],
    templateUrl: 'select-author.html',
    styleUrl: `select-author.scss`
})
export class SelectAuthorComponent implements OnInit {
    isLoading = false;
    allAuthors: AuthorIndexViewDTO[] = [];

    isDropdownOpened: boolean = false;
    isModalOpened: boolean = false;

    authorControl = new FormControl<number>(0);

    @Input() authorToShow: AuthorIndexViewDTO|null = null;
    @Output() onAuthorSelected = new EventEmitter<number>();

    constructor(private readonly authorService: AuthorService,
                private readonly elementRef: ElementRef) {
        this.changeLoading(true);
    }

    ngOnInit(): void {
        this.fetchAuthors();
    }

    fetchAuthors() {
        this.authorService.getAuthors().subscribe({
            next: (response) => {
                this.allAuthors = response;
                this.sortOptions();
                this.changeLoading(false);
            },
            error: (error) => {
                console.error(error);
                this.changeLoading(false);
            }
        });
    }

    toggleDropdown() {
        this.isDropdownOpened = !this.isDropdownOpened;

        if (!this.isDropdownOpened) {
            this.sortOptions();
        }
    }

    sortOptions() {
        this.allAuthors.sort((a, b) => {
            if (this.authorToShow && a.id === this.authorToShow.id) return -1;
            if (this.authorToShow && b.id === this.authorToShow.id) return 1;

            return a.fullName.localeCompare(b.fullName);
        });
    }

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;
        this.isLoading = value;

        if (this.isLoading) {
            this.authorControl.disable();
        } else {
            this.authorControl.enable();
        }
    }

    onAuthorChange(author: AuthorIndexViewDTO) {
        if (this.isSelected(author)) {
            this.authorToShow = null
        } else {
            this.authorToShow = author
        }
        this.onAuthorSelected.emit(this.authorToShow?.id)
    }

    isSelected(author: AuthorIndexViewDTO): boolean {
        return author.id === this.authorToShow?.id
    }

    openCreateModal() {
        this.isModalOpened = true;
    }

    closeCreateModal(isAdded: boolean) {
        this.isModalOpened = false;

        if (!isAdded) return;
        this.changeLoading(true);
        this.fetchAuthors();
    }

    @HostListener('document:click', ['$event'])
    onDocumentClick(event: Event) {
        if (this.isModalOpened) return;

        if (!this.elementRef.nativeElement.contains(event.target))
            if (this.isDropdownOpened) this.toggleDropdown();
    }

    @HostListener('document:keydown.escape', ['$event'])
    onEscapePress() {
        if (this.isModalOpened) return;

        if (this.isDropdownOpened) {
            this.toggleDropdown();
        }
    }
}
