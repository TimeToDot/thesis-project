import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ProjectEmployee } from '../../models/project-employee.model';
import { ProjectEmployeesService } from '../../services/project-employees.service';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { first } from 'rxjs';

@Component({
  selector: 'bvr-view-project-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    RouterLinkWithHref,
    ToastComponent,
  ],
  templateUrl: './view-project-employee.component.html',
})
export class ViewProjectEmployeeComponent implements OnInit {
  isArchiveModalOpen: boolean = false;
  modalDescription: string = '';
  projectEmployee!: ProjectEmployee;

  constructor(
    private projectEmployeeService: ProjectEmployeesService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getEmployee();
  }

  getEmployee(): void {
    const projectId = this.route.parent?.snapshot.paramMap.get('id');
    const employeeId = this.route.snapshot.paramMap.get('id');
    if (projectId && employeeId) {
      this.projectEmployeeService
        .getProjectEmployee(projectId, employeeId)
        .pipe(first())
        .subscribe(projectEmployee => (this.projectEmployee = projectEmployee));
    }
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${this.projectEmployee.employee.firstName} ${this.projectEmployee.employee.lastName}? This action cannot be undone.`;
  }

  archive(value: boolean): void {
    if (value) {
      this.projectEmployeeService
        .archiveProjectEmployee(this.projectEmployee)
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
