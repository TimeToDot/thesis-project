import { Injectable } from '@angular/core';
import { User } from '../../shared/model/user.model';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  private _users: User[] = [
    {
      id: '0',
      name: 'Robert',
      surname: 'Skrzypczak',
      position: 'Frontend Developer',
      employmentDate: new Date(2021, 8, 15),
      active: true,
    },
    {
      id: '1',
      name: 'Beata',
      surname: 'Iwan',
      position: 'Product Designer',
      employmentDate: new Date(2022, 7, 1),
      active: true,
    },
  ];

  constructor() {}

  getUsers(): User[] {
    return this._users;
  }
}
