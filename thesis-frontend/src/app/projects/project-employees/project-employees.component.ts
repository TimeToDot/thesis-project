import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { ToastState } from '../../shared/enum/toast-state';
import { Employee } from '../../shared/models/employee.model';
import { ToastService } from '../../shared/services/toast.service';
import { ProjectEmployee } from '../models/project-employee.model';
import { ProjectEmployeesService } from '../services/project-employees.service';

@Component({
  selector: 'bvr-project-employees',
  templateUrl: './project-employees.component.html',
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
export class ProjectEmployeesComponent implements OnInit {
  dataSource: ProjectEmployee[] = [];
  displayedActiveColumns: string[] = [
    'person',
    'position',
    'employmentStatus',
    'joinDate',
    'actions',
  ];
  displayedArchivedColumns: string[] = [
    'person',
    'position',
    'joinDate',
    'exitDate',
  ];
  isArchiveModalOpen: boolean = false;
  modalDescription: string = '';
  query: string = '';
  showActive: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private projectEmployeesService: ProjectEmployeesService,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.dataSource = this.projectEmployeesService.getProjectEmployees();
  }

  editEmployee(event: Event, row: ProjectEmployee): void {
    event.stopPropagation();
    this.router.navigate([row.id, 'edit'], { relativeTo: this.route });
  }

  openArchiveModal(event: Event, row: ProjectEmployee): void {
    event.stopPropagation();
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${row.employee.firstName} ${row.employee.lastName}? This action cannot be undone.`;
  }

  showEmployeeDetails(row: Employee): void {
    this.router.navigate([row.id], { relativeTo: this.route });
  }

  showActiveTable(value: boolean): void {
    if (value) {
      this.dataSource = this.projectEmployeesService.getProjectEmployees();
    } else {
      this.dataSource =
        this.projectEmployeesService.getArchivedProjectEmployees();
    }
  }

  archive(): void {
    this.toastService.showToast(ToastState.Success, 'Employee archived');
    setTimeout(() => this.toastService.dismissToast(), 3000);
  }
}
