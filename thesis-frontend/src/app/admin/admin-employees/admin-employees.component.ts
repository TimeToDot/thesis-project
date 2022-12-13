import { CdkTableModule } from '@angular/cdk/table';
import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { ToastState } from '../../shared/enum/toast-state';
import { Employee } from '../../shared/model/employee.model';
import { ToastService } from '../../shared/services/toast.service';
import { EmployeesService } from '../services/employees.service';

@Component({
  selector: 'bvr-admin-employees',
  templateUrl: './admin-employees.component.html',
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
export class AdminEmployeesComponent implements OnInit {
  dataSource: Employee[] = [];
  displayedActiveColumns: string[] = [
    'person',
    'position',
    'workingTime',
    'hireDate',
    'actions',
  ];
  displayedArchivedColumns: string[] = [
    'person',
    'position',
    'hireDate',
    'exitDate',
  ];
  isArchiveModalOpen: boolean = false;
  modalDescription: string = '';
  query: string = '';
  showActive: boolean = true;

  constructor(
    private employeesService: EmployeesService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.dataSource = this.employeesService.getEmployees();
  }

  editEmployee(event: Event, row: Employee): void {
    event.stopPropagation();
    this.router.navigate([row.id, 'edit'], { relativeTo: this.route });
  }

  openArchiveModal(event: Event, row: Employee): void {
    event.stopPropagation();
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${row.firstName} ${row.lastName}? This action cannot be undone.`;
  }

  showEmployeeDetails(row: Employee): void {
    this.router.navigate([row.id], { relativeTo: this.route });
  }

  showActiveTable(value: boolean): void {
    if (value) {
      this.dataSource = this.employeesService.getEmployees();
    } else {
      this.dataSource = this.employeesService.getArchivedEmployees();
    }
  }

  archive(): void {
    this.toastService.showToast(ToastState.Success, 'Employee archived');
    setTimeout(() => this.toastService.dismissToast(), 3000);
  }
}
