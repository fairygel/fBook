import {
    Component, ElementRef, EventEmitter, HostListener, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges,
    ViewChild
} from '@angular/core';
import {Title} from "@angular/platform-browser";
import {HttpErrorResponse} from "@angular/common/http";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";

import {ApiError, isValidationError} from "../../../error/api-error";
import {BookService} from "../../../service/book/book.service";
import {BookDTO} from "../../../dto/book/bookDTO";
import {BookFullViewDTO} from "../../../dto/book/bookFulViewDTO";

import {SelectGenreComponent} from "../../genre/select-genre/select-genre.component";
import {SelectAuthorComponent} from "../../author/select-author/select-author.component";
import {SelectBookTypeComponent} from "../../book-type/select-book-type/select-book-type.component";
import {SelectBookStatusComponent} from "../../book-status/select-book-status/select-book-status.component";

import {GenreDTO} from "../../../dto/genre/genreDTO";
import {AuthorIndexViewDTO} from "../../../dto/author/authorIndexViewDTO";
import {BookTypeDTO} from "../../../dto/book/type/bookTypeDTO";
import {BookStatusDTO} from "../../../dto/book/status/bookStatusDTO";
import {CommonModule, NgClass} from "@angular/common";

import {Subject} from "rxjs";
import {debounceTime} from "rxjs/operators";
import {IndexBookViewDTO} from "../../../dto/book/indexBookViewDTO";

@Component({
    selector: 'app-book-info',
    standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule,
        SelectGenreComponent,
        SelectAuthorComponent,
        SelectBookTypeComponent,
        SelectBookStatusComponent,
        NgClass,
        CommonModule
    ],
    templateUrl: 'book-info.html',
    styleUrl: `book-info.scss`
})
export class BookInfoComponent implements OnInit, OnDestroy, OnChanges {
    @Input() bookToShow: IndexBookViewDTO | null = null;
    @Input() isOpen: boolean = false;

    @Output() closeModalEvent = new EventEmitter<void>();
    @Output() bookDeletedEvent = new EventEmitter<number>();
    @Output() bookUpdatedEvent = new EventEmitter<void>();

    formChangeSubject = new Subject<void>();
    wasCoverOrNameUpdated = false;

    isLoading: boolean = false;
    id: number = -1;

    isShrank = false;

    coverUrl: string = "";
    cover: File | null = null;

    genresToUpdate: number[] = [];
    authorToUpdate: number | null = null;
    bookTypeToUpdate: number | null = null;
    bookStatusToUpdate: number | null = null;

    book: BookFullViewDTO | null = null;

    bookGenres: GenreDTO[] = [];
    author: AuthorIndexViewDTO | null = null;
    bookType: BookTypeDTO | null = null;
    bookStatus: BookStatusDTO | null = null;

    bookForm = new FormGroup({
        name: new FormControl(''),
        annotation: new FormControl(''),
        startedReadDate: new FormControl<string | null>(null),
        endedReadDate: new FormControl<string | null>(null)
    });

    @ViewChild('startedDate') startedDate!: ElementRef;
    @ViewChild('endedDate') endedDate!: ElementRef;

    showDelayedModal = false;
    private openTimer: ReturnType<typeof setTimeout> | null = null;

    constructor(private readonly bookService: BookService,
                private readonly pageTitle: Title) {
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['isOpen'] && this.isOpen && this.bookToShow) {
            this.fetchBook(this.bookToShow.id, true);
            this.openWithDelay();
        }
    }

    openWithDelay() {
        this.showDelayedModal = false;
        if (this.openTimer !== null) globalThis.clearTimeout(this.openTimer as any);
        this.openTimer = setTimeout(() => {
            if (this.isOpen) this.showDelayedModal = true;
        }, 100);
    }

    fetchBook(id: number, isFirstLoading?: boolean) {
        if (isFirstLoading || this.book === null) {
            this.changeLoading(true);
            this.coverUrl = this.bookToShow?.coverUrl || '';
            this.id = id;
        }

        this.bookService.getBook(id)
            .subscribe({
                next: (response) => {
                    this.fillBookWithData(response);
                    this.changeLoading(false);
                },
                error: (error) => {
                    console.error(error)
                    this.changeLoading(false);
                }
            });

    }

    ngOnInit() {
        this.formChangeSubject.pipe(
            debounceTime(1000)
        ).subscribe(() => {
            this.handleUpdateBookSubmit();
        })
        this.onResize();
    }

    onFormChange(isNameOrCover = false) {
        this.wasCoverOrNameUpdated = isNameOrCover;

        if (!this.bookForm.dirty) {
            this.bookForm.markAsDirty();
        }
        this.formChangeSubject.next();
    }

    ngOnDestroy() {
        this.handleUpdateBookSubmit();

        if (this.cover) URL.revokeObjectURL(this.coverUrl);
        this.formChangeSubject.complete();

        if (this.openTimer !== null) globalThis.clearTimeout(this.openTimer as any);
        this.openTimer = null;
    }

    closeModal() {
        this.handleUpdateBookSubmit();
        this.isOpen = false;
        this.book = null;
        this.coverUrl = '';

        if (this.openTimer !== null) globalThis.clearTimeout(this.openTimer as any);
        this.openTimer = null;
        this.showDelayedModal = false;
        this.closeModalEvent.emit();
    }

    @HostListener('document:keydown.escape')
    onEscapePress() {
        if (!this.isOpen) return;

        const dropdownOpen = document.querySelector('.custom-select.open');
        if (dropdownOpen) return;

        const creationModalOpen = document.querySelector('app-generic-create-modal .modal-backdrop');
        if (creationModalOpen) return;

        this.closeModal();
    }

    @HostListener('document:click', ['$event'])
    onDocumentClick(event: Event) {
        const target = event.target as HTMLElement;
        if (target.classList.contains('modal-backdrop'))
            this.closeModal();
    }

    deleteBook() {
        if (this.isLoading) return;
        if (!confirm('are you sure you want to delete this book?')) return;

        this.changeLoading(true);

        this.bookService.deleteBook(this.id)
            .subscribe({
                next: () => {
                    this.bookDeletedEvent.emit(this.id);
                    this.closeModal();
                },
                error: (error) => {
                    console.error(error)
                }
            })
    }

    handleUpdateBookSubmit() {
        if (this.isLoading || !this.bookForm.dirty) return;

        const book = this.parseBookFromForm();

        this.bookService.updateBook(this.id, book, this.cover).subscribe({
            next: () => {
                this.fetchBook(this.id);
                if (this.wasCoverOrNameUpdated) {
                    this.bookUpdatedEvent.emit();
                    this.wasCoverOrNameUpdated = false;
                }
            },
            error: (error: HttpErrorResponse) => {
                const apiError: ApiError = error.error;
                let errorMessage = '';

                if (isValidationError(apiError)) {
                    errorMessage = `${apiError.message}\n\n`;
                    apiError.detail.forEach(detail => {
                        errorMessage += `${detail.field}: ${detail.value}\n`;
                    });
                } else {
                    errorMessage = apiError.message;
                }

                alert(errorMessage);
            }
        });
    }

    private fillBookWithData(response: BookFullViewDTO) {
        this.book = response;

        this.bookGenres = response.genres.filter(g => g.id !== 0);
        this.author = response.author?.id !== 0 ? response.author : null;
        this.bookType = response.bookType?.id !== 0 ? response.bookType : null;
        this.bookStatus = response.bookStatus?.id !== 0 ? response.bookStatus : null;

        this.bookForm.patchValue({
            name: response.name,
            annotation: response.annotation,
            startedReadDate: response.startedReadDate,
            endedReadDate: response.endedReadDate
        })
    }

    private parseBookFromForm(): BookDTO {
        return {
            name: this.bookForm.get('name')?.value ?? '',
            annotation: this.bookForm.get('annotation')?.value ?? '',
            endedReadDate: this.bookForm.get('endedReadDate')?.value ?? undefined,
            startedReadDate: this.bookForm.get('startedReadDate')?.value ?? undefined,

            authorId: this.authorToUpdate ?? undefined,
            genreIds: this.genresToUpdate ?? undefined,
            bookTypeId: this.bookTypeToUpdate ?? undefined,
            bookStatusId: this.bookStatusToUpdate ?? undefined,
        }
    }

    get startedDateFormatted(): string {
        return this.getFormatedDate('startedReadDate')
    }

    get endedDateFormatted(): string {
        return this.getFormatedDate('endedReadDate')
    }

    getFormatedDate(elementName: string): string {
        const value = this.bookForm.get(elementName)?.value;
        if (!value) {
            return '';
        }

        const date = new Date(value);
        return date.toLocaleDateString('ru-RU', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric'
        });
    }

    handleSelectedGenres(selectedGenres: number[]) {
        this.genresToUpdate = selectedGenres;
        this.onFormChange();
    }

    handleSelectedAuthor(selectedAuthor: number) {
        this.authorToUpdate = selectedAuthor;
        this.onFormChange();
    }

    handleSelectedBookType(selectedBookType: number) {
        this.bookTypeToUpdate = selectedBookType;
        this.onFormChange();
    }

    handleSelectedBookStatus(selectedBookStatus: number) {
        this.bookStatusToUpdate = selectedBookStatus;
        this.onFormChange();
    }

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;

        if (this.isLoading) {
            this.bookForm.disable();
            this.pageTitle.setTitle('loading..');
        } else {
            this.bookForm.enable();
            if (this.book) this.pageTitle.setTitle(this.book.name);
        }
    }

    onFileSelected(event: any) {
        const input = event.target as HTMLInputElement;
        this.cover = input.files?.[0] || null;

        if (this.cover) this.generatePreview(this.cover);
    }

    private generatePreview(file: File): void {
        this.coverUrl = URL.createObjectURL(file);
        this.onFormChange(true);
    }

    onDrop(event: DragEvent) {
        this.handleDrag(event);

        if (event.dataTransfer?.files?.[0]) {
            this.cover = event.dataTransfer.files[0];

            if (this.cover) this.generatePreview(this.cover);
        }
    }

    @HostListener('window:resize', ['$event'])
    onResize() {
        this.isShrank = window.innerWidth <= 1080;
    }

    handleDrag(event: DragEvent) {
        event.preventDefault();
        event.stopPropagation();
    }

    focusOnElement(element: HTMLInputElement, event?: Event) {
        event?.preventDefault();
        if ('showPicker' in element) {
            element.showPicker();
        } else {
            (element as HTMLInputElement).focus();
        }
    }
}
