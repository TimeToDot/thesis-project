import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Account } from '../../../shared/models/account.model';

@Component({
  selector: 'bvr-view-address-info',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-address-info.component.html',
})
export class ViewAddressInfoComponent {
  @Input() account!: Account;
}
