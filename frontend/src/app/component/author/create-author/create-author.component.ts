import {
    Component,
    EventEmitter,
    Input,
    Output, ViewChild
} from '@angular/core';
import {AuthorService} from "../../../service/author/author.service";
import {GenericCreateModalComponent} from "../../../base/generic-create-modal/generic-create-modal.component";

@Component({
    selector: 'app-create-author',
    standalone: true,
    imports: [GenericCreateModalComponent],
    template: `
        <app-generic-create-modal
                #modal
                [isOpen]="isOpen"
                objectName="Author"
                (closeModalEvent)="handleClose($event)"
                (submitEvent)="createAuthor($event)">
        </app-generic-create-modal>
    `
})
export class CreateAuthorComponent {
    @ViewChild('modal') modal!: GenericCreateModalComponent;
    @Input() isOpen = false;
    @Output() closeModalEvent = new EventEmitter<boolean>();

    constructor(private readonly authorService: AuthorService) {}

    handleClose(added: boolean) {
        this.closeModalEvent.emit(added);
    }

    createAuthor(value: string) {
        if (!this.modal) return;
        this.modal.handleSubmit(() => this.authorService.createAuthor(value));

    }
}
