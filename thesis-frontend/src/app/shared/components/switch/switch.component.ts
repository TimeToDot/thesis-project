import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'bvr-switch',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './switch.component.html',
})
export class SwitchComponent {
  @Output() valueChange: EventEmitter<boolean> = new EventEmitter();

  selected: boolean = false;

  onChange(value: boolean): void {
    this.selected = value;
    this.valueChange.emit(this.selected);
  }
}
