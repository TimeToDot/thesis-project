import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-billing-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-billing-info.component.html',
})
export class EditBillingInfoComponent {
  @Input() editProjectForm!: FormGroup;
}
