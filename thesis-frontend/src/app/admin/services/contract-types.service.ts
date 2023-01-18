import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DropdownOption } from '../../shared/models/dropdown-option.model';

@Injectable({
  providedIn: 'root',
})
export class ContractTypesService {
  constructor(private http: HttpClient) {}

  getContractTypes(): Observable<DropdownOption[]> {
    return this.http.get<DropdownOption[]>(environment.apiUrl);
  }
}
