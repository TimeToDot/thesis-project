import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { DropdownOption } from '../../shared/models/dropdown-option.model';

@Injectable({
  providedIn: 'root',
})
export class ContractTypesService {
  private url: string = 'http://localhost:3000/contract-types';

  constructor(private http: HttpClient) {}

  getContractTypes(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(this.url);
  }
}
