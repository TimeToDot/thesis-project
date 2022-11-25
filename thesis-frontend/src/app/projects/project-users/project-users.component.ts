import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { User } from '../../shared/model/user.model';

@Component({
  selector: 'bvr-project-users',
  templateUrl: './project-users.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CdkTableModule,
    CommonModule,
    FormsModule,
    RouterModule,
  ],
})
export class ProjectUsersComponent implements OnInit {
  dataSource: User[] = [
    {
      id: '0',
      name: 'Robert',
      surname: 'Skrzypczak',
      position: 'Frontend Developer',
      employment_date: new Date(2021, 8, 15),
    },
    {
      id: '1',
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

  constructor(private route: ActivatedRoute, private router: Router) {}

  ngOnInit(): void {}

  editUser(event: Event, row: User): void {
    event.stopPropagation();
    this.router.navigate([row.id, 'edit'], { relativeTo: this.route });
  }

  showUserDetails(row: User): void {
    console.log(row.id);
    this.router.navigate([row.id], { relativeTo: this.route });
  }
}
