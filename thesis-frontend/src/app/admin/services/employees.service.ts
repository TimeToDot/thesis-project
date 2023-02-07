import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Account } from '../../shared/models/account.model';
import { EmployeeProject } from '../../shared/models/employee-project.model';
import { Employee } from '../../shared/models/employee.model';
import { Approval } from '../../tracker/models/approval.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeesService {
  private url: string = 'http://localhost:3000/employees';

  constructor(private http: HttpClient) {}

  getEmployee(id: string): Observable<Account> {
    return this.http.get<Account>(`${this.url}/${id}`);
  }

  addEmployee(employee: Account): Observable<Employee> {
    const newEmp: Employee = {
      id: employee.id,
      firstName: employee.firstName,
      lastName: employee.lastName,
      email: employee.email,
      image: employee.image,
      position: employee.position.name,
      employmentDate: employee.employmentDate,
      workingTime: Number(employee.workingTime),
      wage: Number(employee.wage),
      contractType: employee.contractType,
      active: Boolean(employee.active),
    };
    return this.http.post<Employee>(this.url, newEmp);
  }

  addAccount(employee: Account): Observable<Account> {
    return this.http.post<Account>('http://localhost:3000/accounts', employee);
  }

  updateEmployee(employee: Account): Observable<Employee> {
    const newEmp: Employee = {
      id: employee.id,
      firstName: employee.firstName,
      lastName: employee.lastName,
      email: employee.email,
      image: employee.image,
      position: employee.position.name,
      employmentDate: employee.employmentDate,
      workingTime: Number(employee.workingTime),
      wage: Number(employee.wage),
      contractType: employee.contractType,
      active: Boolean(employee.active),
    };
    return this.http.put<Employee>(`${this.url}post/${newEmp.id}`, newEmp);
  }

  updateAccount(employee: Account): Observable<Account> {
    return this.http.put<Account>(
      `http://localhost:3000/accounts/${employee.id}`,
      employee
    );
  }

  archiveEmployee(employee: Account): Observable<Employee> {
    const newEmp: Employee = {
      id: employee.id,
      firstName: employee.firstName,
      lastName: employee.lastName,
      email: employee.email,
      image: employee.image,
      position: employee.position.name,
      employmentDate: employee.employmentDate,
      workingTime: Number(employee.workingTime),
      wage: Number(employee.wage),
      contractType: employee.contractType,
      exitDate: formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en'),
      active: false,
    };
    return this.http.put<Employee>(`${this.url}post/${newEmp.id}`, newEmp);
  }

  archiveAccount(employee: Account): Observable<Account> {
    employee.active = false;
    employee.exitDate = formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en');
    return this.http.put<Account>(
      `http://localhost:3000/accounts/${employee.id}`,
      employee
    );
  }

  getEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.url}?active=true`);
  }

  getArchivedEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.url}?active=false`);
  }

  getActiveEmployeeProjects(employeeId: string): Observable<EmployeeProject[]> {
    return this.http.get<EmployeeProject[]>(
      `${this.url}/${employeeId}/projects?active=true`
    );
  }

  getEmployeeProjects(employeeId: string): Observable<EmployeeProject[]> {
    return this.http.get<EmployeeProject[]>(
      `${this.url}/${employeeId}/projects`
    );
  }

  getProjectsToApprove(employeeId: string): Observable<Approval[]> {
    return this.http.get<Approval[]>(`${this.url}/${employeeId}/approve`);
  }
}
