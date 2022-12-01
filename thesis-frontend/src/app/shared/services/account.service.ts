import { Injectable } from '@angular/core';
import { Account } from '../model/account.model';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private _account: Account = {
    email: 'jan.kowalski@gmail.com',
    firstName: 'Jan',
    image: '',
    lastName: 'Kowalski',
  };

  getEmployeeAccount(): Account {
    return this._account;
  }
}
