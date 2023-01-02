import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'bvr-modal',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './modal.component.html',
})
export class ModalComponent {
  @Input() confirmLabel: string = '';
  @Input() description: string = '';
  @Input() title: string = '';
  @Input() visible: boolean = false;

  @Output() confirm: EventEmitter<boolean> = new EventEmitter();
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();

  closeModal(shouldRedirect: boolean): void {
    this.visible = false;
    this.visibleChange.emit(this.visible);
    this.confirm.emit(shouldRedirect);
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }
}
