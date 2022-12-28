import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-employment-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-employment-info.component.html',
})
export class EditEmploymentInfoComponent {
  @Input() editEmployeeForm!: FormGroup;
}
