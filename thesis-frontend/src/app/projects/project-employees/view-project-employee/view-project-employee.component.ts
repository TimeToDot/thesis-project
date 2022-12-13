import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ProjectEmployee } from '../../model/project-employee.model';
import { ProjectEmployeesService } from '../../services/project-employees.service';
import { ToastComponent } from '../../../shared/components/toast/toast.component';

@Component({
  selector: 'bvr-view-project-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    RouterModule,
    ToastComponent,
  ],
  templateUrl: './view-project-employee.component.html',
})
export class ViewProjectEmployeeComponent implements OnInit {
  employee: ProjectEmployee = {
    id: '',
    firstName: '',
    lastName: '',
    email: '',
    image: '',
    position: '',
    employmentDate: '',
    contractType: '',
    workingTime: 0,
    wage: 0,
    joinDate: '',
    exitDate: '',
    active: true,
  };

  constructor(
    private projectEmployeeService: ProjectEmployeesService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getEmployee();
  }

  getEmployee(): void {
    const employeeId = this.route.snapshot.paramMap.get('id');
    if (employeeId) {
      this.employee =
        this.projectEmployeeService.getProjectEmployee(employeeId);
    }
  }
}
