import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-edit-position',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './edit-position.component.html',
  styles: [],
})
export class EditPositionComponent {
  save(): void {
    console.log('saveum');
  }
}
