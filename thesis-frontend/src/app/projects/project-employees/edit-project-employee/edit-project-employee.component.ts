import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { Employee } from '../../../shared/model/employee.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import {
  FormBuilder,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DropdownListComponent } from '../../../shared/components/dropdown-list/dropdown-list.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'bvr-edit-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownListComponent,
    FormFieldComponent,
    FormsModule,
    ModalComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './edit-project-employee.component.html',
})
export class EditProjectEmployeeComponent implements OnInit {
  contractTypes: { id: string; name: string }[] = [
    { id: '1', name: 'Employment contract' },
    { id: '2', name: 'Commission contract' },
    { id: '3', name: 'Specific-task contract' },
    { id: '4', name: 'B2B' },
  ];
  editProjectEmployeeForm!: FormGroup;
  employee: Employee = {
    id: '6',
    email: 'jan.kowalski@gmail.com',
    firstName: 'Jan',
    image: 'assets/icons/icon13.png',
    lastName: 'Kowalski',
    position: 'Driver',
    employmentDate: '10/06/2022',
    active: true,
  };
  isArchiveModalOpen: boolean = false;
  isLeaveModalOpen: boolean = false;
  isSaveModalOpen: boolean = false;

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.editProjectEmployeeForm = this.fb.group({
      contractType: ['', [Validators.required]],
      workingTime: ['', [Validators.required]],
      wage: ['', [Validators.required]],
    });
  }

  cancel(): void {
    this.location.back();
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
  }

  openLeaveModal(): void {
    this.isLeaveModalOpen = true;
  }

  openSaveModal(): void {
    this.isSaveModalOpen = true;
  }

  isRequired(name: string): boolean {
    return this.editProjectEmployeeForm
      .get([name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }

  confirm(): void {
    this.location.back();
  }
}
