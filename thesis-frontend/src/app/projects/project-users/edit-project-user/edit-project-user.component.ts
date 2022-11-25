import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';

@Component({
  selector: 'bvr-edit-user',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './edit-project-user.component.html',
})
export class EditProjectUserComponent {
  constructor(private location: Location) {}

  cancel(): void {
    this.location.back();
  }
}
