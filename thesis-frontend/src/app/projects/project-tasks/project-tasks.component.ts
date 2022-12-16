import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { first } from 'rxjs';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { ToastState } from '../../shared/enum/toast-state';
import { ToastService } from '../../shared/services/toast.service';
import { ProjectTask } from '../models/project-task.model';
import { ProjectTasksService } from '../services/project-tasks.service';

@Component({
  selector: 'bvr-project-tasks',
  templateUrl: './project-tasks.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CdkTableModule,
    CommonModule,
    FormsModule,
    ModalComponent,
    RouterModule,
    ToastComponent,
  ],
})
export class ProjectTasksComponent implements OnInit {
  archiveDescription: string = '';
  dataSource: ProjectTask[] = [];
  displayedActiveColumns: string[] = [
    'name',
    'description',
    'creationDate',
    'actions',
  ];
  displayedArchivedColumns: string[] = ['name', 'description', 'creationDate'];
  isArchiveModalOpen: boolean = false;
  query: string = '';
  showActive: boolean = true;

  constructor(
    private projectTasksService: ProjectTasksService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.projectTasksService
      .getProjectTasks('1')
      .pipe(first())
      .subscribe(projectTasks => (this.dataSource = projectTasks));
  }

  showActiveTable(value: boolean): void {
    if (value) {
      this.projectTasksService
        .getProjectTasks('1')
        .pipe(first())
        .subscribe(projectTasks => (this.dataSource = projectTasks));
    } else {
      this.projectTasksService
        .getArchivedProjectTasks('1')
        .pipe(first())
        .subscribe(
          archivedProjectTasks => (this.dataSource = archivedProjectTasks)
        );
    }
  }

  editTask(event: Event, row: ProjectTask): void {
    event.stopPropagation();
    this.router.navigate([row.id, 'edit'], { relativeTo: this.route });
  }

  showTaskDetails(row: ProjectTask): void {
    this.router.navigate([row.id], { relativeTo: this.route });
  }

  openArchiveModal(event: Event, row: ProjectTask): void {
    event.stopPropagation();
    this.isArchiveModalOpen = true;
    this.archiveDescription = `Are you sure you want to archive task ${row.name}? This action cannot be undone.`;
  }

  archive(): void {
    this.toastService.showToast(ToastState.Success, 'Task archived');
    setTimeout(() => this.toastService.dismissToast(), 3000);
  }
}
