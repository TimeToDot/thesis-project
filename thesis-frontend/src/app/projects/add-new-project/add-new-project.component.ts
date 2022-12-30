import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { GeneralInfoComponent } from '../general-info/general-info.component';
import { ModeratorInfoComponent } from '../moderator-info/moderator-info.component';
import { BillingInfoComponent } from '../billing-info/billing-info.component';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { Subject } from 'rxjs';
import { tabAnimation } from '../../shared/animations/tab.animation';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'bvr-add-new-project',
  standalone: true,
  imports: [
    BillingInfoComponent,
    CommonModule,
    GeneralInfoComponent,
    ModalComponent,
    ModeratorInfoComponent,
    ReactiveFormsModule,
  ],
  templateUrl: './add-new-project.component.html',
  animations: [tabAnimation],
})
export class AddNewProjectComponent implements OnInit {
  addProjectForm!: FormGroup;
  enableFormButtons: boolean = true;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  modalDescription: string = '';
  redirectSubject: Subject<boolean> = new Subject<boolean>();
  step: number = 1;

  constructor(private fb: FormBuilder, private location: Location) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.addProjectForm = this.fb.group({
      generalInfo: this.fb.group({
        name: ['', [Validators.required]],
        image: [null, [Validators.required]],
        description: [''],
      }),
      moderator: ['', [Validators.required]],
      billingInfo: this.fb.group({
        billingPeriod: ['', [Validators.required]],
        overtimeModifier: [{ value: '', disabled: true }],
        bonusModifier: [{ value: '', disabled: true }],
        nightModifier: [{ value: '', disabled: true }],
        holidayModifier: [{ value: '', disabled: true }],
      }),
    });
  }

  nextStep(): void {
    ++this.step;
  }

  previousStep(): void {
    --this.step;
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  cancel(value: boolean): void {
    if (this.isFromGuard) {
      this.redirectSubject.next(value);
    } else {
      this.disableGuard(value);
      if (value) {
        this.location.back();
      }
    }
  }

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }

  toggleFormButtons(value: boolean): void {
    this.enableFormButtons = value;
  }
}
