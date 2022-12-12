import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { EmployeesService } from '../admin/services/employees.service';
import { ButtonComponent } from '../shared/components/button/button.component';
import { Employee } from '../shared/model/employee.model';

@Component({
  selector: 'bvr-profile',
  templateUrl: './profile.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
})
export class ProfileComponent implements OnInit {
  currentEmployee: Employee = {
    id: '',
    firstName: '',
    lastName: '',
    email: '',
    image: '',
    position: '',
    employmentDate: '',
    active: false,
  };

  constructor(private employeesService: EmployeesService) {}

  ngOnInit(): void {
    this.currentEmployee = this.employeesService.getEmployee('1');
  }
}
