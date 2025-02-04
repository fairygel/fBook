import {Component, EventEmitter, Output} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthorService} from "../../../service/author/author.service";
import {HttpErrorResponse} from "@angular/common/http";
import {ApiError} from "../../../error/api-error";

@Component({
    selector: 'app-create-author',
    standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
    templateUrl: 'create-author.html',
    styles: ``
})
export class CreateAuthorComponent {
    isLoading = false;

    authorForm = new FormGroup({
        fullName: new FormControl('')
    });

    @Output() authorCreatedEvent = new EventEmitter();

    constructor(private readonly authorService: AuthorService) {}

    createAuthor() {
        if (this.isLoading) return;

        this.isLoading = true;

        this.authorService.createAuthor(this.authorForm.get('fullName')?.value ?? '').subscribe(
            {
                next: () => {
                    this.authorForm.reset();
                    this.authorCreatedEvent.emit();
                    this.isLoading = false;
                },
                error: (error: HttpErrorResponse) => {
                    const apiError: ApiError = error.error;
                    alert(apiError.description);
                    this.isLoading = false;
                }
            }
        )
    }
}
