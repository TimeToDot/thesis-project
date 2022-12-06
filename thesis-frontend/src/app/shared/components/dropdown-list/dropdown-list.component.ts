import { Component, HostListener, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { DropdownOption } from '../../model/dropdown-option.model';

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

  disabled: boolean = false;
  selectEnabled: boolean = false;
  selectedOption: DropdownOption = { name: 'Select option', id: '' };
  touched: boolean = false;

  private wasInside: boolean = false;

  toggleSelect(): void {
    this.selectEnabled = !this.selectEnabled;
  }

  selectOption(option: DropdownOption): void {
    this.markAsTouched();
    this.selectedOption = option;
    this.onChange(this.selectedOption.id);
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

  onChange = (selectedOptionId: string) => {};

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
