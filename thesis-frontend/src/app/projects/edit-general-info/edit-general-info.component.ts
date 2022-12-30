import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-general-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-general-info.component.html',
})
export class EditGeneralInfoComponent {
  @Input() editProjectForm!: FormGroup;
}
