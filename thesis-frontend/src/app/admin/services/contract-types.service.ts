import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DropdownOption } from '../../shared/models/dropdown-option.model';

@Injectable({
  providedIn: 'root',
})
export class ContractTypesService {
  constructor(private http: HttpClient) {}

  getContractTypes(): Observable<DropdownOption[]> {
    return this.http
      .get<any>(`${environment.apiUrl}/employee/contractTypes`)
      .pipe(
        map(value => {
          const contractTypes: DropdownOption[] = [];
          Object.keys(value).forEach(key => {
            contractTypes.push({ id: key, name: value[key] });
          });
          return contractTypes;
        })
      );
  }
}
