import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Employee } from '../../shared/models/employee.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeesService {
  private url: string = 'http://localhost:3000/employees';

  constructor(private http: HttpClient) {}

  getEmployee(id: string): Observable<Employee> {
    return this.http.get<Employee>(`${this.url}/${id}`);
  }

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.url}?active=true`);
  }

  getArchivedEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.url}?active=false`);
  }
}
