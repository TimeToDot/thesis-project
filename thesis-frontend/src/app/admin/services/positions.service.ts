import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Position } from '../models/position.model';

@Injectable({
  providedIn: 'root',
})
export class PositionsService {
  constructor(private http: HttpClient) {}

  getPosition(id: string): Observable<Position> {
    return this.http.get<Position>(`${environment.apiUrl}/positions/${id}`);
  }

  addPosition(position: Position): Observable<Position> {
    return this.http.post<Position>(
      `${environment.apiUrl}/positions`,
      position
    );
  }

  updatePosition(position: Position): Observable<Position> {
    return this.http.put<Position>(
      `${environment.apiUrl}/positions/${position.id}`,
      position
    );
  }

  archivePosition(position: Position): Observable<Position> {
    position.active = false;
    position.archiveDate = formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en');
    return this.http.put<Position>(
      `${environment.apiUrl}/positions/${position.id}`,
      position
    );
  }

  getPositions(): Observable<Position[]> {
    return this.http.get<Position[]>(
      `${environment.apiUrl}/positions?active=true`
    );
  }

  getArchivedPositions(): Observable<Position[]> {
    return this.http.get<Position[]>(
      `${environment.apiUrl}/positions?active=false`
    );
  }
}
