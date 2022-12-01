import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { User } from '../../shared/model/user.model';
import { ProjectUsersService } from '../services/project-users.service';

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
  dataSource: User[] = [];
  displayedColumns: string[] = [
    'person',
    'position',
    'employmentDate',
    'actions',
  ];
  query: string = '';

  constructor(
    private route: ActivatedRoute,
    private projectUsersService: ProjectUsersService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.dataSource = this.projectUsersService.getProjectUsers();
  }

  editUser(event: Event, row: User): void {
    event.stopPropagation();
    this.router.navigate([row.id, 'edit'], { relativeTo: this.route });
  }

  showUserDetails(row: User): void {
    console.log(row.id);
    this.router.navigate([row.id], { relativeTo: this.route });
  }
}
