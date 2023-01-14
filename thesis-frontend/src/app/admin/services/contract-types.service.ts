import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { DropdownOption } from '../../shared/models/dropdown-option.model';

@Injectable({
  providedIn: 'root',
})
export class ContractTypesService {
  private _contractTypes: DropdownOption[] = [
    { id: '1', name: 'Employment contract' },
    { id: '2', name: 'Commission contract' },
    { id: '3', name: 'Specific-task contract' },
    { id: '4', name: 'B2B' },
  ];

  constructor() {}

  getContractTypes(): Observable<DropdownOption[]> {
    return of(this._contractTypes);
  }
}
