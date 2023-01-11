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
import { Employee } from '../../shared/models/employee.model';
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
    RouterLinkWithHref,
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
  idToArchive: string = '';
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
    this.getEmployees();
  }

  editEmployee(event: Event, row: Employee): void {
    event.stopPropagation();
    this.router.navigate([row.id, 'edit'], { relativeTo: this.route });
  }

  openArchiveModal(event: Event, row: Employee): void {
    event.stopPropagation();
    this.isArchiveModalOpen = true;
    this.idToArchive = row.id;
    this.modalDescription = `Are you sure you want to archive ${row.firstName} ${row.lastName}? This action cannot be undone.`;
  }

  showEmployeeDetails(row: Employee): void {
    this.router.navigate([row.id], { relativeTo: this.route });
  }

  showActiveTable(value: boolean): void {
    value ? this.getEmployees() : this.getArchivedEmployees();
  }

  getEmployees(): void {
    this.employeesService
      .getEmployees()
      .pipe(first())
      .subscribe(employees => (this.dataSource = employees));
  }

  getArchivedEmployees(): void {
    this.employeesService
      .getArchivedEmployees()
      .pipe(first())
      .subscribe(archivedEmployees => (this.dataSource = archivedEmployees));
  }

  archive(): void {
    this.employeesService
      .getEmployee(this.idToArchive)
      .pipe(first())
      .subscribe(employee => {
        this.employeesService
          .archiveAccount(employee)
          .pipe(first())
          .subscribe(employee => {
            this.employeesService
              .archiveEmployee(employee)
              .pipe(first())
              .subscribe(() => {
                this.toastService.showToast(
                  ToastState.Info,
                  'Employee archived'
                );
                setTimeout(() => this.toastService.dismissToast(), 3000);
                this.getEmployees();
              });
          });
      });
  }
}
