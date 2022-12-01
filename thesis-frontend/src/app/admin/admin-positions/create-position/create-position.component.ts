import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-create-position',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './create-position.component.html',
})
export class CreatePositionComponent {
  save(): void {
    console.log('saveum');
  }
}
