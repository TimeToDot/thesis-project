import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Account } from '../../../shared/models/account.model';

@Component({
  selector: 'bvr-view-account-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-account-info.component.html',
})
export class ViewAccountInfoComponent {
  @Input() account!: Account;
}
