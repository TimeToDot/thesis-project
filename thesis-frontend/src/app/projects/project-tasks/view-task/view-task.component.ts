import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ProjectTask } from '../../models/project-task.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ProjectTasksService } from '../../services/project-tasks.service';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { first } from 'rxjs';

@Component({
  selector: 'bvr-view-task',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    RouterLinkWithHref,
    ToastComponent,
  ],
  templateUrl: './view-task.component.html',
})
export class ViewTaskComponent implements OnInit {
  isArchiveModalOpen: boolean = false;
  modalDescription: string = '';
  task: ProjectTask = {
    id: '',
    name: '',
    projectId: '',
    description: '',
    creationDate: '',
    archiveDate: '',
    active: true,
  };

  constructor(
    private projectTasksService: ProjectTasksService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getTask();
  }

  getTask(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    const taskId = this.route.snapshot.paramMap.get('id');
    if (projectId && taskId) {
      this.projectTasksService
        .getProjectTask(projectId, taskId)
        .pipe(first())
        .subscribe(projectTask => {
          console.log(projectTask);
          this.task = projectTask;
        });
    }
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive task ${this.task.name}? This action cannot be undone.`;
  }

  archive(value: boolean): void {
    if (value) {
      this.projectTasksService
        .archiveProjectTask(this.task)
        .pipe(first())
        .subscribe(() => {
          this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
            setTimeout(
              () =>
                this.toastService.showToast(ToastState.Info, 'Task archived'),
              200
            );
            setTimeout(() => this.toastService.dismissToast(), 3200);
          });
        });
    }
  }
}
