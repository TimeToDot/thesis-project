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

  @Output() confirm: EventEmitter<void> = new EventEmitter();
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter();

  closeModal(): void {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }

  confirmModal(): void {
    this.closeModal();
    this.confirm.emit();
  }

  stopPropagation(event: Event): void {
    event.stopPropagation();
  }
}
