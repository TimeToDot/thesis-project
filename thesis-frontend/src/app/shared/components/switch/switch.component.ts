import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ControlValueAccessor,
  FormsModule,
  NG_VALUE_ACCESSOR,
} from '@angular/forms';

@Component({
  selector: 'bvr-switch',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './switch.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: SwitchComponent,
    },
  ],
})
export class SwitchComponent implements ControlValueAccessor {
  @Input() disabled: boolean = false;
  @Input() selected: boolean = false;
  @Output() valueChange: EventEmitter<boolean> = new EventEmitter();

  change(value: boolean): void {
    this.selected = value;
    this.valueChange.emit(this.selected);
    this.onChange(this.selected);
  }

  writeValue(selected: boolean) {
    this.selected = selected;
  }

  onChange = (selected: boolean) => {};

  registerOnChange(onChange: any) {
    this.onChange = onChange;
  }

  onTouched = () => {};

  registerOnTouched(onTouched: any) {
    this.onTouched = onTouched;
  }
}
