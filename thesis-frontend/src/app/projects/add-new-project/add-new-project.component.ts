import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { GeneralInfoComponent } from './general-info/general-info.component';
import { ModeratorInfoComponent } from './moderator-info/moderator-info.component';
import { BillingInfoComponent } from './billing-info/billing-info.component';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { Subject } from 'rxjs';
import { tabAnimation } from '../../shared/animations/tab.animation';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Regex } from '../../shared/helpers/regex.helper';
import { CustomValidators } from '../../shared/helpers/custom-validators.helper';

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
  controls: any = {};
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
    this.getFormControls();
  }

  createForm(): void {
    this.addProjectForm = this.fb.group({
      generalInfo: this.fb.group({
        name: [
          '',
          [Validators.required, Validators.pattern(Regex.ALPHANUMERIC)],
        ],
        image: [null, [Validators.required]],
        description: [''],
      }),
      moderatorInfo: this.fb.group({
        moderator: ['', [Validators.required]],
      }),
      billingInfo: this.fb.group({
        billingPeriod: ['', [Validators.required]],
        overtimeModifier: [
          { value: 100, disabled: true },
          [CustomValidators.minValue(0), CustomValidators.maxValue(500)],
        ],
        bonusModifier: [
          { value: 100, disabled: true },
          [CustomValidators.minValue(0), CustomValidators.maxValue(500)],
        ],
        nightModifier: [
          { value: 100, disabled: true },
          [CustomValidators.minValue(0), CustomValidators.maxValue(500)],
        ],
        holidayModifier: [
          { value: 100, disabled: true },
          [CustomValidators.minValue(0), CustomValidators.maxValue(500)],
        ],
      }),
    });
  }

  getFormControls(): void {
    Object.keys(this.addProjectForm.controls).forEach(group => {
      this.controls[group] = this.addProjectForm.get([group]);
      Object.keys(
        (this.addProjectForm.get(group) as FormGroup<any>).controls
      ).forEach(field => {
        this.controls[field] = this.addProjectForm.get([group, field]);
      });
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
