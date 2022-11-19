import { Component, OnInit } from '@angular/core';
import { User } from './model/user';

@Component({
  selector: 'bvr-admin-users',
  templateUrl: './admin-users.component.html',
  styles: [],
})
export class AdminUsersComponent implements OnInit {
  dataSource: User[] = [
    {
      name: 'Robert',
      surname: 'Skrzypczak',
      position: 'Frontend Developer',
      employment_date: new Date(2021, 8, 15),
    },
    {
      name: 'Beata',
      surname: 'Iwan',
      position: 'Product Designer',
      employment_date: new Date(2022, 7, 1),
    },
  ];
  displayedColumns: string[] = [
    'person',
    'position',
    'employment_date',
    'actions',
  ];
  query: string = '';

  constructor() {}

  ngOnInit(): void {}
}
