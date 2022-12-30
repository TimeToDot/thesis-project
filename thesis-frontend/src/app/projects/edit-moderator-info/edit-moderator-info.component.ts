import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'bvr-edit-moderator-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './edit-moderator-info.component.html',
})
export class EditModeratorInfoComponent {
  @Input() editProjectForm!: FormGroup;
}
