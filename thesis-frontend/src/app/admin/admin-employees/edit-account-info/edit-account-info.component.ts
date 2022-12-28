import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-account-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-account-info.component.html',
})
export class EditAccountInfoComponent {
  @Input() editEmployeeForm!: FormGroup;
}
