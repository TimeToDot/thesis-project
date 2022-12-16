import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Position } from '../models/position.model';

@Injectable({
  providedIn: 'root',
})
export class PositionsService {
  private _positions: Position[] = [
    {
      id: '1',
      name: 'Frontend Developer',
      description: 'Frontend Developer',
      creationDate: '2022-01-20',
      count: 1,
      active: true,
    },
    {
      id: '2',
      name: 'Product Designer',
      description: 'Product Designer',
      creationDate: '2022-01-20',
      count: 1,
      archiveDate: '2022-10-10',
      active: false,
    },
  ];

  constructor() {}

  getPosition(id: string): Observable<Position> {
    const position = this._positions.find(position => position.id === id);
    return of(position as Position);
  }

  getPositions(): Observable<Position[]> {
    return of(this._positions.filter(position => position.active));
  }

  getArchivedPositions(): Observable<Position[]> {
    return of(this._positions.filter(position => !position.active));
  }
}
