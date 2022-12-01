import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';

@Component({
  selector: 'bvr-edit-employee',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './edit-project-employee.component.html',
})
export class EditProjectEmployeeComponent {
  constructor(private location: Location) {}

  cancel(): void {
    this.location.back();
  }
}
