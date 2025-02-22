import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {CreateAuthorComponent} from "../create-author/create-author.component";
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthorIndexViewDTO} from "../../../dto/author/authorIndexViewDTO";
import {AuthorService} from "../../../service/author/author.service";

@Component({
    selector: 'app-select-author',
    standalone: true,
    imports: [
        CreateAuthorComponent,
        FormsModule,
        ReactiveFormsModule
    ],
    templateUrl: 'select-author.html',
    styleUrl: `select-author.scss`
})
export class SelectAuthorComponent implements OnInit {
    isLoading = false;
    allAuthors: AuthorIndexViewDTO[] = [];

    authorControl = new FormControl<number>(0);

    @Input() authorToShow: AuthorIndexViewDTO|null = null;
    @Output() onAuthorSelected = new EventEmitter<number>();

    constructor(private readonly authorService: AuthorService) {
        this.controlValueChangeOfAuthor()
        this.changeLoading(true);
    }

    controlValueChangeOfAuthor() {
        this.authorControl.valueChanges.subscribe((authorId) => {
            if (authorId)
                this.onAuthorSelected.emit(authorId);
        });
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

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;

        if (this.isLoading) {
            this.authorControl.disable();
        } else {
            this.authorControl.enable();
        }
    }
}
