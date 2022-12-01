import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';

@Component({
  selector: 'bvr-edit-employee',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './edit-employee.component.html',
})
export class EditEmployeeComponent {
  constructor(private location: Location) {}

  cancel(): void {
    this.location.back();
  }
}
