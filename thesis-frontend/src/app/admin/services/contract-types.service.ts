import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ContractType } from '../../projects/models/contract-type.model';

@Injectable({
  providedIn: 'root',
})
export class ContractTypesService {
  private _contractTypes: ContractType[] = [
    { id: '1', name: 'Employment contract' },
    { id: '2', name: 'Commission contract' },
    { id: '3', name: 'Specific-task contract' },
    { id: '4', name: 'B2B' },
  ];

  constructor() {}

  getContractTypes(): Observable<ContractType[]> {
    return of(this._contractTypes);
  }
}
