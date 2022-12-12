import { Component } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-edit-task',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './edit-task.component.html',
})
export class EditTaskComponent {
  constructor(private location: Location) {}

  save(): void {
    console.log('saveum');
  }

  cancel(): void {
    this.location.back();
  }
}
