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
    styleUrl: `create-author.scss`
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

        this.changeLoading(true);

        this.authorService.createAuthor(this.authorForm.get('fullName')?.value ?? '').subscribe(
            {
                next: () => {
                    this.authorForm.reset();
                    this.authorCreatedEvent.emit();
                    this.changeLoading(false);
                },
                error: (error: HttpErrorResponse) => {
                    const apiError: ApiError = error.error;
                    alert(apiError.description);
                    this.changeLoading(false);
                }
            }
        )
    }

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;

        if (this.isLoading) {
            this.authorForm.disable();
        } else {
            this.authorForm.enable();
        }
    }
}
