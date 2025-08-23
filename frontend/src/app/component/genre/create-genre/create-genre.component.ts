import {
    Component,
    ElementRef,
    EventEmitter,
    HostListener,
    Input, OnChanges,
    OnDestroy,
    OnInit,
    Output, QueryList, SimpleChanges, ViewChildren
} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {GenreService} from "../../../service/genre/genre.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../../error/api-error";
import {CommonModule} from "@angular/common";

@Component({
    selector: 'app-create-genre',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule
    ],
    templateUrl: 'create-genre.html',
    styleUrl: 'create-genre.scss'
})
export class CreateGenreComponent implements OnInit, OnDestroy, OnChanges {
    @ViewChildren('create_input') searchInput!: QueryList<ElementRef>;

    @Input() isOpen: boolean = false;

    // true on genre added, false if no changes
    @Output() closeModalEvent = new EventEmitter<boolean>();

    isLoading: boolean = false;
    isGenreAdded = false;

    genreForm = new FormGroup({
        genre: new FormControl('')
    });

    constructor(private readonly genreService: GenreService,
                private readonly el: ElementRef) {}

    ngOnInit() {
        document.body.appendChild(this.el.nativeElement);
    }

    ngOnDestroy() {
        this.el.nativeElement.remove();
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['isOpen']?.currentValue) {
            setTimeout(() =>
                this.searchInput?.first.nativeElement.focus());
        }
    }

    createGenre() {
        if (this.isLoading) return;

        this.changeLoading(true);

        this.genreService.createGenre(this.genreForm.get('genre')?.value ?? '').subscribe({
            next: () => {
                this.genreForm.reset();
                this.isGenreAdded = true;
                this.changeLoading(false);
            },
            error: (error: HttpErrorResponse) => {
                const apiError: ApiError = error.error;
                alert(apiError.description);
                this.changeLoading(false);
            }
        });
    }

    closeModal() {
        this.genreForm.reset()
        this.closeModalEvent.emit(this.isGenreAdded);
        this.isGenreAdded = false;
    }

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;

        if (this.isLoading) {
            this.genreForm.disable();
        } else {
            this.genreForm.enable();
        }
    }

    @HostListener('document:keydown.escape', ['$event'])
    onEscapePress() {
        if (this.isOpen) this.closeModal();
    }

    @HostListener('document:click', ['$event'])
    onDocumentClick(event: Event) {
        const target = event.target as HTMLElement;

        if (target.classList.contains('modal-backdrop'))
            this.closeModal();
    }
}
