import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Account } from '../shared/model/account.model';
import { AccountService } from '../shared/services/account.service';

@Component({
  selector: 'bvr-profile',
  templateUrl: './profile.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class ProfileComponent implements OnInit {
  currentUser: Account = { email: '', firstName: '', image: '', lastName: '' };

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.currentUser = this.accountService.getUserAccount();
  }
}
