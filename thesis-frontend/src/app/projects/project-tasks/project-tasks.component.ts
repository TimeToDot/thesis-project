import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
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
    RouterLinkWithHref,
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
  displayedArchivedColumns: string[] = [
    'name',
    'description',
    'creationDate',
    'archiveDate',
  ];
  idToArchive: string = '';
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
    this.getProjectTasks();
  }

  showActiveTable(value: boolean): void {
    value ? this.getProjectTasks() : this.getArchivedProjectTasks();
  }

  getProjectTasks(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    if (projectId) {
      this.projectTasksService
        .getProjectTasks(projectId)
        .pipe(first())
        .subscribe(projectTasks => (this.dataSource = projectTasks));
    }
  }

  getArchivedProjectTasks(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    if (projectId) {
      this.projectTasksService
        .getArchivedProjectTasks(projectId)
        .pipe(first())
        .subscribe(projectTasks => (this.dataSource = projectTasks));
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
    this.idToArchive = row.id;
    this.archiveDescription = `Are you sure you want to archive task ${row.name}? This action cannot be undone.`;
  }

  archive(value: boolean): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    if (value && projectId) {
      this.projectTasksService
        .getProjectTask(projectId, this.idToArchive)
        .pipe(first())
        .subscribe(task => {
          this.projectTasksService
            .archiveProjectTask(task)
            .pipe(first())
            .subscribe(() => {
              this.getProjectTasks();
              this.toastService.showToast(ToastState.Info, 'Task archived');
              setTimeout(() => this.toastService.dismissToast(), 3000);
            });
        });
    }
  }
}
