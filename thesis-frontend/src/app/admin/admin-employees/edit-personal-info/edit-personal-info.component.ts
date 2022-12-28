import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-personal-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-personal-info.component.html',
})
export class EditPersonalInfoComponent {
  @Input() editEmployeeForm!: FormGroup;
}
