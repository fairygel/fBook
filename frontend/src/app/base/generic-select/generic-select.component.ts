import {Component, ElementRef, EventEmitter, HostListener, Input, Output, ViewChild} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {OptionDTO} from "./optionDTO";
import {Observable} from "rxjs";
import {ApiError} from "../../error/api-error";
import {GenericCreationModalComponent} from "../generic-create-modal/generic-creation-modal.component";

@Component({
    selector: 'app-generic-select',
    standalone: true,
    imports: [CommonModule, FormsModule, ReactiveFormsModule, GenericCreationModalComponent],
    templateUrl: 'generic-select.html',
    styleUrl: 'generic-select.scss'
})
export class GenericSelect {
    @ViewChild('modal') creationModal!: GenericCreationModalComponent;

    isLoading: boolean = true;
    allOptions: OptionDTO[] = [];

    bookStatusControl = new FormControl<number|null>(null);

    @Input() shownOption: OptionDTO|null = null;
    @Input() toOption!: ((r: any) => OptionDTO);
    @Input() createMethod!: ((value: string) => Observable<any>);
    @Input() loadMethod!: (() => Observable<any>);

    @Input() isOpen: boolean = false;
    @Input() objectName: string = '';
    @Input() canCreate: boolean = false;

    // returns selected option id
    @Output() onOptionSelected = new EventEmitter<OptionDTO|null>();
    @Output() closeModalEvent = new EventEmitter<boolean>();
    isDropdownOpened: boolean = false;

    added: boolean = false;

    constructor(private readonly elementRef: ElementRef) {}

    loadOptions<T>(action: () => Observable<T>): void {
        action().subscribe({
            next: (response: any) => {
                this.allOptions = (response as any[]).map(r => this.toOption(r));
                this.sortOptions();
            },
            error: (error: ApiError) => {
                console.error(error);
            },
            complete: () => {
                this.changeLoading(false);
            }
        })
    }

    changeLoading(value: boolean) {
        if (this.isLoading === value) return;

        this.isLoading = value;

        if (this.isLoading) {
            this.bookStatusControl.disable();
        } else {
            this.bookStatusControl.enable();
        }
    }

    toggleDropdown() {
        this.isDropdownOpened = !this.isDropdownOpened;

        if (!this.isDropdownOpened) {
            this.sortOptions();
        }
    }

    sortOptions() {
        this.allOptions.sort((a, b) => {
            if (this.shownOption && a.id === this.shownOption.id) return -1;
            if (this.shownOption && b.id === this.shownOption.id) return 1;

            return a.id - b.id;
        });
    }

    isSelected(option: OptionDTO): boolean {
        return option.id === this.shownOption?.id
    }

    onOptionChange(option: OptionDTO) {
        if (this.isSelected(option)) {
            if (!this.canCreate) return;

            this.shownOption = null;
            this.onOptionSelected.emit(null);
        } else {
            this.shownOption = option
            this.onOptionSelected.emit(this.shownOption)
        }
    }

    @HostListener('document:click', ['$event'])
    closeDropdown(event: Event) {
        if (!this.elementRef.nativeElement.contains(event.target))
            if (this.isDropdownOpened) this.toggleDropdown()
    }

    @HostListener('document:keydown.escape', ['$event'])
    onEscapePress() {
        if (this.isDropdownOpened) {
            this.toggleDropdown()
        }
    }

    newOption(value: string) {
        if (!this.creationModal) return;
        this.creationModal.handleSubmit(() => this.createMethod(value));

    }

    openCreateModal() {
        this.isOpen = true;
    }

    handleClose(added: boolean) {
        this.isOpen = false;
        this.closeModalEvent.emit(added);

        if (added) {
            this.changeLoading(true);
            this.loadOptions(() => this.loadMethod());
        }
    }
}