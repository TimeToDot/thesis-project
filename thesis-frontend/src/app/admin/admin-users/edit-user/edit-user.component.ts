import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';

@Component({
  selector: 'bvr-edit-user',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './edit-user.component.html',
})
export class EditUserComponent {
  constructor(private location: Location) {}

  cancel(): void {
    this.location.back();
  }
}
