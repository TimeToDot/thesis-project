import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-address-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-address-info.component.html',
})
export class EditAddressInfoComponent {
  @Input() editEmployeeForm!: FormGroup;
}
