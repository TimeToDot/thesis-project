import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { DropdownOption } from '../../shared/models/dropdown-option.model';

@Injectable({
  providedIn: 'root',
})
export class BillingPeriodService {
  private _billingPeriods: DropdownOption[] = [
    { id: '1', name: 'Week' },
    { id: '2', name: '2 Weeks' },
    { id: '3', name: 'Month' },
    { id: '4', name: 'Season' },
  ];

  constructor() {}

  getBillingPeriods(): Observable<DropdownOption[]> {
    return of(this._billingPeriods);
  }
}
