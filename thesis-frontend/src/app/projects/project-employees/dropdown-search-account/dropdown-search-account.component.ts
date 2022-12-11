import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ControlValueAccessor,
  FormsModule,
  NG_VALUE_ACCESSOR,
} from '@angular/forms';
import { Account } from '../../../shared/model/account.model';

@Component({
  selector: 'bvr-dropdown-search-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './dropdown-search-account.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: DropdownSearchAccountComponent,
    },
  ],
})
export class DropdownSearchAccountComponent implements ControlValueAccessor {
  @Input() name: string = '';
  @Input() options: Account[] = [];

  disabled: boolean = false;
  query: string = '';
  selectEnabled: boolean = false;
  selectedOption!: string;
  touched: boolean = false;

  selectOption(option: string): void {
    console.log(option);
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

  writeValue(selectedOption: string): void {
    this.selectedOption = selectedOption;
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

  onFocus(): void {
    this.selectEnabled = true;
  }

  onBlur(): void {
    setTimeout(() => (this.selectEnabled = false), 100);
  }
}
