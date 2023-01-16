import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Position } from '../models/position.model';

@Injectable({
  providedIn: 'root',
})
export class PositionsService {
  constructor(private http: HttpClient) {}

  getPosition(id: string): Observable<Position> {
    return this.http.get<Position>(`${environment.apiUrl}/position/${id}`);
  }

  addPosition(position: Position): Observable<Position> {
    return this.http.post<Position>(`${environment.apiUrl}/position`, position);
  }

  updatePosition(position: Position): Observable<Position> {
    return this.http.put<Position>(`${environment.apiUrl}/position`, position);
  }

  archivePosition(position: Position): Observable<Position> {
    position.active = false;
    return this.http.put<Position>(`${environment.apiUrl}/position`, position);
  }

  getPositions(): Observable<Position[]> {
    return this.http
      .get<any>(`${environment.apiUrl}/positions?active=true`)
      .pipe(map(value => value.positions));
  }

  getArchivedPositions(): Observable<Position[]> {
    return this.http
      .get<any>(`${environment.apiUrl}/positions?active=false`)
      .pipe(map(value => value.positions));
  }
}
