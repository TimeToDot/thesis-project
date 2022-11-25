import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../shared/components/button/button.component';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-user',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
  templateUrl: './user.component.html',
})
export class UserComponent {}
