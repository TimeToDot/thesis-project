import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmEmployeeComponent } from '../confirm-employee/confirm-employee.component';
import { FindEmployeeComponent } from '../find-employee/find-employee.component';
import { SelectWageComponent } from '../select-wage/select-wage.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'bvr-add-project-employee',
  standalone: true,
  imports: [
    CommonModule,
    ConfirmEmployeeComponent,
    FindEmployeeComponent,
    ReactiveFormsModule,
    SelectWageComponent,
  ],
  templateUrl: './add-project-employee.component.html',
})
export class AddProjectEmployeeComponent implements OnInit {
  addProjectEmployeeForm!: FormGroup;
  step: number = 1;

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.createForm();
  }
  createForm(): void {
    this.addProjectEmployeeForm = this.fb.group({
      userInfo: this.fb.group({
        id: ['', [Validators.required]],
      }),
      employmentInfo: this.fb.group({
        contractType: ['', [Validators.required]],
        workingTime: ['', [Validators.required]],
        wage: ['', [Validators.required]],
      }),
    });
  }

  nextStep(): void {
    ++this.step;
  }

  previousStep(): void {
    --this.step;
  }
}
