import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-edit-task',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './edit-task.component.html',
})
export class EditTaskComponent {
  save(): void {
    console.log('saveum');
  }
}
