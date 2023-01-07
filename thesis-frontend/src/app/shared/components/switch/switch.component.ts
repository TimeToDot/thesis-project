import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'bvr-switch',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './switch.component.html',
})
export class SwitchComponent {
  @Input() disabled: boolean = false;
  @Input() selected: boolean = false;
  @Output() valueChange: EventEmitter<boolean> = new EventEmitter();

  onChange(value: boolean): void {
    this.selected = value;
    this.valueChange.emit(this.selected);
  }
}
