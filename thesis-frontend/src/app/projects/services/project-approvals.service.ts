import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProjectApproval } from '../models/project-approval.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectApprovalsService {
  constructor(private http: HttpClient) {}

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
