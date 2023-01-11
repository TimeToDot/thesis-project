import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProjectApproval } from '../models/project-approval.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectApprovalsService {
  private url: string = 'http://localhost:3000/projects';

  constructor(private http: HttpClient) {}

  getProjectApproval(
    projectId: string,
    approvalId: string
  ): Observable<ProjectApproval> {
    return this.http.get<ProjectApproval>(
      `${this.url}/${projectId}/approvals/${approvalId}`
    );
  }

  getProjectApprovals(projectId: string): Observable<ProjectApproval[]> {
    return this.http.get<ProjectApproval[]>(
      `${this.url}/${projectId}/approvals?active=true`
    );
  }

  getArchivedProjectApprovals(
    projectId: string
  ): Observable<ProjectApproval[]> {
    return this.http.get<ProjectApproval[]>(
      `${this.url}/${projectId}/approvals?active=false`
    );
  }
}
