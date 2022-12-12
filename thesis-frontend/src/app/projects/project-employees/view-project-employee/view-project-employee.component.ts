import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { RouterModule } from '@angular/router';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ProjectEmployee } from '../../model/project-employee.model';

@Component({
  selector: 'bvr-view-project-employee',
  standalone: true,
  imports: [ButtonComponent, CommonModule, FormFieldComponent, RouterModule],
  templateUrl: './view-project-employee.component.html',
})
export class ViewProjectEmployeeComponent {
  employee: ProjectEmployee = {
    id: '1',
    firstName: 'Beata',
    lastName: 'Iwan',
    email: 'beata.iwan@gmail.com',
    image: 'assets/icons/icon4.png',
    position: 'Product Designer',
    employmentDate: '2022-07-01',
    contractType: 'B2B',
    workingTime: 40,
    wage: 35,
    joinDate: '2021-09-10',
    exitDate: '2022-10-23',
    active: true,
  };
}
