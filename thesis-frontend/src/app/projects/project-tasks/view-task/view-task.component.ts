import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';

@Component({
  selector: 'bvr-view-task',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './view-task.component.html',
})
export class ViewTaskComponent {}
