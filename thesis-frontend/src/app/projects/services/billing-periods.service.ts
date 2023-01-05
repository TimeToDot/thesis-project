import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DropdownOption } from '../../shared/models/dropdown-option.model';

@Injectable({
  providedIn: 'root',
})
export class BillingPeriodsService {
  private url: string = 'http://localhost:3000/billing-periods';

  constructor(private http: HttpClient) {}

  getBillingPeriods(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(this.url);
  }
}
