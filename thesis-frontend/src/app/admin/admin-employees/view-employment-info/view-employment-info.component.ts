import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Account } from '../../../shared/models/account.model';

@Component({
  selector: 'bvr-view-employment-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-employment-info.component.html',
})
export class ViewEmploymentInfoComponent {
  @Input() account!: Account;
}
