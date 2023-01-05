import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Account } from '../../shared/models/account.model';

@Injectable({
  providedIn: 'root',
})
export class AccountsService {
  private url: string = 'http://localhost:3000/accounts';

  constructor(private http: HttpClient) {}

  getAccount(id: string): Observable<Account> {
    return this.http.get<Account>(`${this.url}/${id}`);
  }
}
