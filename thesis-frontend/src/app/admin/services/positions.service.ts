import { Injectable } from '@angular/core';
import { Position } from '../../admin/model/position.model';

@Injectable({
  providedIn: 'root',
})
export class PositionsService {
  private _positions: Position[] = [
    {
      id: '1',
      name: 'Frontend Developer',
      description: 'Frontend Developer',
      employeesCount: 1,
    },
    {
      id: '2',
      name: 'Product Designer',
      description: 'Product Designer',
      employeesCount: 1,
    },
  ];

  constructor() {}

  getPositions(): Position[] {
    return this._positions;
  }
}
