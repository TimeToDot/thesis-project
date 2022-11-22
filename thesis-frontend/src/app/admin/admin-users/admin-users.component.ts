import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { User } from '../../shared/model/user.model';

@Component({
  selector: 'bvr-admin-users',
  templateUrl: './admin-users.component.html',
  standalone: true,
  imports: [ButtonComponent, CdkTableModule, CommonModule, FormsModule],
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
