import {
  Component,
  ElementRef,
  HostListener,
  Input,
  ViewChild,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { DropdownOption } from '../../models/dropdown-option.model';

@Component({
  selector: 'bvr-dropdown-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dropdown-list.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: DropdownListComponent,
    },
  ],
})
export class DropdownListComponent implements ControlValueAccessor {
  @Input() name: string = '';
  @Input() options: DropdownOption[] = [];

  @ViewChild('dropdown') dropdown!: ElementRef<HTMLElement>;
  @ViewChild('dropdownContent') dropdownContent!: ElementRef<HTMLElement>;

  disabled: boolean = false;
  selectEnabled: boolean = false;
  selectedOption: DropdownOption = { name: 'Select option', id: '' };
  touched: boolean = false;

  private wasInside: boolean = false;

  toggleSelect(): void {
    this.selectEnabled = !this.selectEnabled;
    if (this.selectEnabled) {
      this.showDropdownUpwards();
    }
  }

  showDropdownUpwards(): void {
    setTimeout(() => {
      const dropdownRect = this.dropdown.nativeElement.getBoundingClientRect();
      const dropdownContentHeight =
        this.dropdownContent.nativeElement.offsetHeight;
      const availableSpaceBelow = window.innerHeight - dropdownRect.bottom;

      this.dropdownContent.nativeElement.style.top =
        dropdownContentHeight > availableSpaceBelow
          ? `-${dropdownContentHeight}px`
          : '100%';
    }, 0);
  }

  selectOption(option: DropdownOption): void {
    this.markAsTouched();
    this.selectedOption = option;
    this.onChange(this.selectedOption);
    this.selectEnabled = false;
  }

  markAsTouched() {
    if (!this.touched) {
      this.onTouched();
      this.touched = true;
    }
  }

  writeValue(selectedOption: DropdownOption): void {
    this.selectedOption = selectedOption
      ? selectedOption
      : { name: 'Select ' + this.name, id: '' };
  }

  onChange = (selectedOptionId: DropdownOption) => {};

  registerOnChange(onChange: any): void {
    this.onChange = onChange;
  }

  onTouched = () => {};

  registerOnTouched(onTouched: any): void {
    this.onTouched = onTouched;
  }

  setDisabledState(disabled: boolean) {
    this.disabled = disabled;
  }

  @HostListener('click')
  click(): void {
    this.wasInside = true;
  }

  @HostListener('document:click')
  hideDatepicker(): void {
    if (!this.wasInside) {
      this.selectEnabled = false;
    }
    this.wasInside = false;
  }
}
