import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EmployeeTask } from '../../shared/models/employee-task.model';
import { ProjectApproval } from '../models/project-approval.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectApprovalsService {
  constructor(private http: HttpClient) {}

  getEmployeeProjectTasks(
    employeeId: string,
    projectId: string,
    date: string
  ): Observable<EmployeeTask[]> {
    date = formatDate(date, 'yyyy-MM-dd', 'en');
    return this.http.get<EmployeeTask[]>(
      `${environment.apiUrl}/project/${projectId}/employees/${employeeId}/tasks?date=${date}`
    );
  }

  getProjectApproval(
    projectId: string,
    approvalId: string
  ): Observable<ProjectApproval> {
    return this.http.get<ProjectApproval>(
      `${environment.apiUrl}/projects/${projectId}/approvals/${approvalId}`
    );
  }

  getProjectApprovals(projectId: string): Observable<ProjectApproval[]> {
    return this.http.get<ProjectApproval[]>(
      `${environment.apiUrl}/projects/${projectId}/approvals?active=true`
    );
  }

  getArchivedProjectApprovals(
    projectId: string
  ): Observable<ProjectApproval[]> {
    return this.http.get<ProjectApproval[]>(
      `${environment.apiUrl}/projects/${projectId}/approvals?active=false`
    );
  }
}
