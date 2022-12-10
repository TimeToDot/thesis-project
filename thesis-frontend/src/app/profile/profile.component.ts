import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../shared/components/button/button.component';
import { Account } from '../shared/model/account.model';
import { AccountService } from '../shared/services/account.service';

@Component({
  selector: 'bvr-profile',
  templateUrl: './profile.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
})
export class ProfileComponent implements OnInit {
  currentEmployee: Account = {
    id: '',
    email: '',
    firstName: '',
    image: '',
    lastName: '',
  };

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.currentEmployee = this.accountService.getEmployeeAccount();
  }
}
