import { formatDate } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Position } from '../models/position.model';

@Injectable({
  providedIn: 'root',
})
export class PositionsService {
  private url: string = 'http://localhost:8080/thesis/api';

  constructor(private http: HttpClient) {}

  getPosition(id: string): Observable<Position> {
    return this.http.get<Position>(`${this.url}/${id}`);
  }

  addPosition(position: Position): Observable<Position> {
    return this.http.post<Position>(this.url, position);
  }

  updatePosition(position: Position): Observable<Position> {
    return this.http.put<Position>(`${this.url}/${position.id}`, position);
  }

  archivePosition(position: Position): Observable<Position> {
    position.active = false;
    position.archiveDate = formatDate(new Date(Date.now()), 'yyyy-MM-dd', 'en');
    return this.http.put<Position>(`${this.url}/${position.id}`, position);
  }

  getPositions(): Observable<Position[]> {
    return this.http
      .get<any>(`${this.url}/positions?active=true`)
      .pipe(map(value => value.positions));
  }

  getArchivedPositions(): Observable<Position[]> {
    return this.http
      .get<any>(`${this.url}?active=false`)
      .pipe(map(value => value.positions));
  }
}
