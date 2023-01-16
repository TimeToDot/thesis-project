import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ControlValueAccessor,
  FormsModule,
  NG_VALUE_ACCESSOR,
} from '@angular/forms';

@Component({
  selector: 'bvr-input-number',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './input-number.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: InputNumberComponent,
    },
  ],
})
export class InputNumberComponent implements ControlValueAccessor, OnInit {
  @Input() decimalPlaces: number = 2;
  @Input() digits: number = 2;
  @Input() isDisabled: boolean = false;
  @Input() placeholder: string = '';
  @Input() suffix: string = '';

  regex: RegExp = new RegExp(/^\d{0,2}\.?\d{0,2}$/g);
  touched: boolean = false;
  specialKeys: string[] = [
    'Backspace',
    'Tab',
    'End',
    'Home',
    'ArrowLeft',
    'ArrowRight',
    'Del',
    'Delete',
  ];
  value!: string;

  constructor() {}

  ngOnInit(): void {
    this.setRegex();
  }

  setRegex(): void {
    const pattern = `^\\d{0,${this.digits}}\\.?\\d{0,${this.decimalPlaces}}$`;
    const pattern2 = `^\\d{0,${this.digits}}$`;
    this.regex = new RegExp(this.decimalPlaces ? pattern : pattern2, 'g');
  }

  input(value: string): void {
    this.markAsTouched();
    this.value = value;
    this.onChange(this.value);
  }

  onKeyDown(event: KeyboardEvent): void {
    if (this.specialKeys.indexOf(event.key) !== -1) {
      return;
    }
    const current = this.value ? this.value.toString() : '';
    const position = current.length;
    const next = [
      current.slice(0, position),
      event.key === 'Decimal' ? '.' : event.key,
      current.slice(position),
    ].join('');
    if (next && !String(next).match(this.regex)) {
      event.preventDefault();
    }
  }

  writeValue(value: string) {
    this.value = value;
  }

  onChange = (value: string) => {};

  registerOnChange(onChange: any) {
    this.onChange = onChange;
  }

  onTouched = () => {};

  registerOnTouched(onTouched: any) {
    this.onTouched = onTouched;
  }

  markAsTouched() {
    if (!this.touched) {
      this.onTouched();
      this.touched = true;
    }
  }
}
