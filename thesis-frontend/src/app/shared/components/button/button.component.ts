import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'bvr-button',
  templateUrl: './button.component.html',
  styles: [],
})
export class ButtonComponent {
  @Input() icon: string = '';
  @Input() label: string = '';
  @Input() styleClass: string = '';

  @Output() onClick: EventEmitter<any> = new EventEmitter();
}
