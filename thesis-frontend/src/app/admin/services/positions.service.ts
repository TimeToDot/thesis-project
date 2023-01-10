import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Position } from '../models/position.model';

@Injectable({
  providedIn: 'root',
})
export class PositionsService {
  private url: string = 'http://localhost:3000/positions';

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
    return this.http.put<Position>(`${this.url}/${position.id}`, position);
  }

  getPositions(): Observable<Position[]> {
    return this.http.get<Position[]>(`${this.url}?active=true`);
  }

  getArchivedPositions(): Observable<Position[]> {
    return this.http.get<Position[]>(`${this.url}?active=false`);
  }
}
