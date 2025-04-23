import {Component, ElementRef, EventEmitter, HostListener, Input, OnInit, Output} from '@angular/core';
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

    handleAuthorCreation() {
        this.changeLoading(true);
        this.fetchAuthors();
    }

    fetchAuthors() {
        this.authorService.getAuthors().subscribe({
            next: (response) => {
                this.allAuthors = response;
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

    @HostListener('document:click', ['$event'])
    closeDropdown(event: Event) {
        if (!this.elementRef.nativeElement.contains(event.target))
            if (this.isDropdownOpened) this.isDropdownOpened = false;
    }

    isSelected(author: AuthorIndexViewDTO): boolean {
        return author.id === this.authorToShow?.id
    }

    onAuthorChange(author: AuthorIndexViewDTO) {
        if (this.isSelected(author)) {
            this.authorToShow = null
        } else {
            this.authorToShow = author
        }
        this.onAuthorSelected.emit(this.authorToShow?.id)

        this.isDropdownOpened = false;
    }
}
