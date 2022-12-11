import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { DropdownSearchAccountComponent } from 'src/app/projects/project-employees/dropdown-search-account/dropdown-search-account.component';
import { Account } from 'src/app/shared/model/account.model';
import { FormFieldComponent } from 'src/app/shared/components/form-field/form-field.component';

@Component({
  selector: 'bvr-find-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    DropdownSearchAccountComponent,
    FormFieldComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './find-employee.component.html',
})
export class FindEmployeeComponent implements OnInit {
  @Input() addProjectEmployeeForm!: FormGroup;

  @Output() nextStepChange: EventEmitter<void> = new EventEmitter();

  employees: Account[] = [
    {
      id: '1',
      email: 'krystian.kowalczyk@gmail.com',
      firstName: 'Krystian',
      image: 'assets/icons/icon16.png',
      lastName: 'Kowalczyk',
    },
    {
      id: '2',
      email: 'joanna.malawska@gmail.com',
      firstName: 'Joanna',
      image: 'assets/icons/icon8.png',
      lastName: 'Malawska',
    },
    {
      id: '3',
      email: 'anna.nowak@gmail.com',
      firstName: 'Anna',
      image: 'assets/icons/icon7.png',
      lastName: 'Nowak',
    },
    {
      id: '4',
      email: 'pawel.szymanski@gmail.com',
      firstName: 'Paweł',
      image: 'assets/icons/icon19.png',
      lastName: 'Szymański',
    },
    {
      id: '5',
      email: 'maria.wisniewska@gmail.com',
      firstName: 'Maria',
      image: 'assets/icons/icon3.png',
      lastName: 'Wiśniewska',
    },
    {
      id: '6',
      email: 'jan.kowalski@gmail.com',
      firstName: 'Jan',
      image: 'assets/icons/icon13.png',
      lastName: 'Kowalski',
    },
    {
      id: '7',
      email: 'emil.zielinski@gmail.com',
      firstName: 'Emil',
      image: 'assets/icons/icon18.png',
      lastName: 'Zieliński',
    },
  ];

  constructor() {}

  ngOnInit(): void {
    setTimeout(() => this.observeIdSelection(), 0);
  }

  observeIdSelection(): void {
    this.addProjectEmployeeForm
      .get(['userInfo', 'id'])
      ?.valueChanges.subscribe(() => {
        this.nextStepChange.emit();
      });
  }

  isRequired(name: string): boolean {
    return this.addProjectEmployeeForm
      .get(['userInfo', name])
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
