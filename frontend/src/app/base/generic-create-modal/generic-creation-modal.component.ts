import {
    Component,
    ElementRef,
    EventEmitter,
    Input, OnChanges, OnDestroy, OnInit,
    Output,
    QueryList, SimpleChanges,
    ViewChildren
} from '@angular/core';
import { FormControl, FormGroup, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {ApiError} from "../../error/api-error";
import {HttpErrorResponse} from "@angular/common/http";
import {Observable} from "rxjs";

@Component({
    selector: 'app-generic-create-modal',
    standalone: true,
    imports: [CommonModule, FormsModule, ReactiveFormsModule, ],
    templateUrl: 'generic-create-modal.html',
    styleUrl: 'generic-create-modal.scss'
})
export class GenericCreationModalComponent implements OnInit, OnDestroy, OnChanges {
    @ViewChildren('create_input') searchInput!: QueryList<ElementRef>;

    @Input() isOpen = false;
    @Input() objectName = '';
    @Output() closeModalEvent = new EventEmitter<boolean>();
    @Output() submitEvent = new EventEmitter<string>();

    isLoading = false;
    isObjectAdded = false;

    objectForm = new FormGroup({
        obj: new FormControl('')
    });

    constructor(protected readonly el: ElementRef) {}

    ngOnInit() {
        document.body.appendChild(this.el.nativeElement);
    }

    ngOnDestroy() {
        this.el.nativeElement.remove();
    }

    ngOnChanges(changes: SimpleChanges) {
        if (changes['isOpen']?.currentValue) {
            setTimeout(() => this.focusOnInput());
        }
    }

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;

        if (this.isLoading) {
            this.objectForm.disable();
        } else {
            this.objectForm.enable();
        }
    }

    focusOnInput() {
        this.searchInput?.first.nativeElement.focus();
    }

    closeModal() {
        this.closeModalEvent.emit(this.isObjectAdded);
        this.isObjectAdded = false;
    }

    onBackdropClick(event: MouseEvent) {
        event.stopPropagation();
        const target = event.target as HTMLElement;
        if (target.classList.contains('modal-backdrop')) {
            this.closeModal();
        }
    }

    onEscapePress(event: Event) {
        event.stopPropagation();
        if (this.isOpen) {
            this.closeModal();
        }
    }

    createObject(): void {
        const value = this.objectForm.get('obj')?.value ?? '';
        this.submitEvent.emit(value);
    }

    handleSubmit<T>(action: () => Observable<T>): void {
        this.changeLoading(true);

        action().subscribe({
            next: () => {
                this.objectForm.reset();
                this.isObjectAdded = true;
                this.changeLoading(false);
                this.focusOnInput();
            },
            error: (error: HttpErrorResponse) => {
                const apiError: ApiError = error.error;
                alert(apiError.description);
                this.changeLoading(false);
                this.focusOnInput();
            }
        });
    }

}
