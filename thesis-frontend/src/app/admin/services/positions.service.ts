import { Injectable } from '@angular/core';
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
      count: 1,
    },
    {
      id: '2',
      name: 'Product Designer',
      description: 'Product Designer',
      count: 1,
    },
  ];

  constructor() {}

  getPositions(): Position[] {
    return this._positions;
  }
}
