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
    styles: ``
})
export class SelectAuthorComponent implements OnInit {
    isLoading = true;
    allAuthors: AuthorIndexViewDTO[] = [];

    authorControl = new FormControl<number>(0);

    @Input() authorToShow: string = "";
    @Output() onAuthorSelected = new EventEmitter<number>();

    constructor(private readonly authorService: AuthorService) {
        this.controlValueChangeOfAuthor()
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
        this.isLoading = true;
        this.fetchAuthors();
    }

    fetchAuthors() {
        this.authorService.getAuthors().subscribe({
            next: (response) => {
                this.allAuthors = response;
                this.isLoading = false;
            },
            error: (error) => {
                console.error(error);
            }
        });
    }
}
