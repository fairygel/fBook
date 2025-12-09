import {
    AfterViewInit,
    Component,
    ElementRef,
    EventEmitter,
    HostListener,
    Input,
    Output,
    ViewChild
} from "@angular/core";
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
export class GenericSelect implements AfterViewInit {
    @ViewChild('modal') creationModal!: GenericCreationModalComponent;

    isLoading: boolean = true;
    allOptions: OptionDTO[] = [];

    formControl = new FormControl<number|null>(null);

    // either shownOption or shownOptionArray should be set, not both
    // shownOption is for single select, shownOptionArray is for multi select
    @Input() shownOption!: OptionDTO|null;
    @Input() shownOptionArray!: OptionDTO[]|null;

    @Input({required: true}) toOption!: ((r: any) => OptionDTO);
    @Input({required: true}) loadMethod!: (() => Observable<any>);

    @Input({required: true}) objectName: string = '';

    @Input() createMethod!: ((value: string) => Observable<any>);
    @Input() isMultiSelect: boolean = false;

    // returns selected option
    @Output() optionSelectedEvent = new EventEmitter<OptionDTO|null>();
    // returns selected options
    @Output() optionArraySelectedEvent = new EventEmitter<OptionDTO[]|null>();

    isDropdownOpened: boolean = false;
    isOpen: boolean = false;

    get displayInDropdown(): string {
        if (this.shownOption != null && this.shownOptionArray == null)
            return this.shownOption?.name
        else if (this.shownOptionArray != null)
            return this.shownOptionArray.map(o => o.name).join(', ')
        else return ''
    }

    get canCreate(): boolean {
        return !!this.createMethod;
    }

    constructor(private readonly elementRef: ElementRef) {
    }

    ngAfterViewInit() {
        if (this.shownOption == null && this.shownOptionArray == null) {
            throw new Error('Either shownOption or shownOptionArray input must be set.');
        }
        if (this.shownOption != null && this.shownOptionArray != null) {
            throw new Error('Either shownOption or shownOptionArray should be set, not both');
        }
        this.loadOptions()
        this.changeLoading(true);
    }

    loadOptions(): void {
        this.loadMethod().subscribe({
            next: (response: any) => {
                this.allOptions = (response as any[])
                    .map(r => this.toOption(r))
                    .filter(option => option.id !== 0);
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
            this.formControl.disable();
        } else {
            this.formControl.enable();
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
            const aSelected = this.isSelected(a);
            const bSelected = this.isSelected(b);

            if (aSelected && !bSelected) return -1;
            if (!aSelected && bSelected) return 1;

            return a.name.localeCompare(b.name);
        });
    }

    isSelected(option: OptionDTO): boolean {
        if (this.shownOptionArray == null)
            return option.id === this.shownOption?.id
        else
            return this.shownOptionArray.some(o => o.id===option.id);
    }

    onOptionChange(option: OptionDTO) {
        if (this.isSelected(option)) {
            if (!this.createMethod) return;

            if (this.shownOptionArray == null) {
                this.shownOption = null;
                this.optionSelectedEvent.emit(null);
            } else {
                this.shownOptionArray = this.shownOptionArray.filter(o => o.id !== option.id)
                this.optionArraySelectedEvent.emit(this.shownOptionArray)
            }
        } else {
            if (this.shownOptionArray == null) {
                this.shownOption = option
                this.optionSelectedEvent.emit(this.shownOption)
            } else {
                this.shownOptionArray.push(option)
                this.optionArraySelectedEvent.emit(this.shownOptionArray)
            }
        }
    }

    @HostListener('document:click', ['$event'])
    closeDropdown(event: Event) {
        if (!this.elementRef.nativeElement.contains(event.target))
            if (this.isDropdownOpened) this.toggleDropdown()
    }

    @HostListener('document:keydown.escape')
    onEscapePress() {
        if (this.isDropdownOpened) {
            this.toggleDropdown();
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

        if (added) {
            this.changeLoading(true);
            this.loadOptions();
        }
    }
}