import { Injectable } from '@angular/core';
import { Account } from '../model/account.model';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private _account: Account = {
    email: 'jan.kowalski@gmail.com',
    firstName: 'Jan',
    image: 'assets/icons/icon13.png',
    lastName: 'Kowalski',
  };

  getEmployeeAccount(): Account {
    return this._account;
  }
}
