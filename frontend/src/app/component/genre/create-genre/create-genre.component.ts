import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { GenericCreationModalComponent } from '../../../base/generic-create-modal/generic-creation-modal.component';
import { GenreService } from '../../../service/genre/genre.service';

@Component({
    selector: 'app-create-genre',
    standalone: true,
    imports: [GenericCreationModalComponent],
    template: `
        <app-generic-create-modal
                #modal
                [isOpen]="isOpen"
                objectName="Genre"
                (closeModalEvent)="handleClose($event)"
                (submitEvent)="createGenre($event)">
        </app-generic-create-modal>
    `
})
export class CreateGenreComponent {
    @ViewChild('modal') modal!: GenericCreationModalComponent;
    @Input() isOpen = false;
    @Output() closeModalEvent = new EventEmitter<boolean>();

    constructor(private readonly genreService: GenreService) {}

    handleClose(added: boolean) {
        this.closeModalEvent.emit(added);
    }

    createGenre(value: string) {
        if (!this.modal) return;
        this.modal.handleSubmit(() => this.genreService.createGenre(value));
    }
}