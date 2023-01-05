import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ProjectApproval } from '../models/project-approval.model';

@Injectable({
  providedIn: 'root',
})
export class ProjectApprovalsService {
  private url: string = 'http://localhost:3000/project-approvals';

  constructor(private http: HttpClient) {}

  getProjectApproval(id: string): Observable<ProjectApproval> {
    return this.http.get<ProjectApproval>(`${this.url}/${id}`);
  }

  getProjectApprovals(): Observable<ProjectApproval[]> {
    return this.http.get<ProjectApproval[]>(
      `${this.url}?projectEmployee.active=true`
    );
  }

  getArchivedProjectApprovals(): Observable<ProjectApproval[]> {
    return this.http.get<ProjectApproval[]>(
      `${this.url}?projectEmployee.active=false`
    );
  }
}
