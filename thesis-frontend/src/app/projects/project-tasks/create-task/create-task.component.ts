import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-create-task',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './create-task.component.html',
})
export class CreateTaskComponent {
  save(): void {
    console.log('saveum');
  }
}
