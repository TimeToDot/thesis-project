import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { ButtonComponent } from '../../shared/components/button/button.component';
import {
  ActivatedRoute,
  ChildrenOutletContexts,
  Router,
  RouterLinkWithHref,
} from '@angular/router';
import { TabsComponent } from '../../shared/components/tabs/tabs.component';
import { LinkOption } from '../../shared/models/link-option.model';
import { EditBillingInfoComponent } from './edit-billing-info/edit-billing-info.component';
import { EditGeneralInfoComponent } from './edit-general-info/edit-general-info.component';
import { EditModeratorInfoComponent } from './edit-moderator-info/edit-moderator-info.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { tabAnimation } from '../../shared/animations/tab.animation';
import { ProjectsService } from '../../shared/services/projects.service';
import { first, Subject } from 'rxjs';
import { Project } from '../models/project.model';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';
import { ToastState } from '../../shared/enum/toast-state';
import { ToastService } from '../../shared/services/toast.service';
import { Regex } from '../../shared/helpers/regex.helper';
import { CustomValidators } from '../../shared/helpers/custom-validators.helper';

@Component({
  selector: 'bvr-edit-project',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    EditBillingInfoComponent,
    EditGeneralInfoComponent,
    EditModeratorInfoComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterLinkWithHref,
    TabsComponent,
    ToastComponent,
  ],
  templateUrl: './edit-project.component.html',
  animations: [tabAnimation],
})
export class EditProjectComponent implements OnInit {
  controls: any = {};
  editProjectForm!: FormGroup;
  enableFormButtons: boolean = true;
  isArchiveModalOpen: boolean = false;
  isCancelModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  isSaveModalOpen: boolean = false;
  modalDescription: string = '';
  navbarOptions: LinkOption[] = [];
  project!: Project;
  redirectSubject: Subject<boolean> = new Subject<boolean>();
  tabIndex: number = 0;

  constructor(
    private contexts: ChildrenOutletContexts,
    private fb: FormBuilder,
    private location: Location,
    private projectsService: ProjectsService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getCurrentTab();
    this.getNavbarOptions();
    this.createForm();
    this.getFormControls();
    this.getProject();
  }

  getCurrentTab(): void {
    this.tabIndex =
      this.contexts.getContext('primary')?.route?.snapshot.data['tabs'];
  }

  getNavbarOptions(): void {
    this.navbarOptions.push({ name: 'General', path: 'general-info' });
    this.navbarOptions.push({ name: 'Moderator', path: 'moderator-info' });
    this.navbarOptions.push({ name: 'Modifiers', path: 'modifiers-info' });
  }

  createForm(): void {
    this.editProjectForm = this.fb.group({
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
    Object.keys(this.editProjectForm.controls).forEach(group => {
      this.controls[group] = this.editProjectForm.get([group]);
      Object.keys(
        (this.editProjectForm.get(group) as FormGroup<any>).controls
      ).forEach(field => {
        this.controls[field] = this.editProjectForm.get([group, field]);
      });
    });
  }

  getProject(): void {
    const projectId = this.route.snapshot.paramMap.get('id');
    if (projectId) {
      this.projectsService
        .getProject(projectId)
        .pipe(first())
        .subscribe(project => {
          this.project = project;
          this.updateFormFields();
        });
    }
  }

  updateFormFields(): void {
    Object.keys(this.editProjectForm.controls).forEach(group => {
      if (this.editProjectForm.get(group) instanceof FormGroup<any>) {
        Object.keys(
          (this.editProjectForm.get(group) as FormGroup<any>).controls
        ).forEach(field => {
          this.editProjectForm
            .get([group, field])
            ?.setValue(this.project[field as keyof Project]);
        });
      }
    });
  }

  updateTabIndex(index: number): void {
    this.tabIndex = index;
  }

  toggleFormButtons(value: boolean): void {
    this.enableFormButtons = value;
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${this.project.name}? This action cannot be undone.`;
  }

  openCancelModal(fromGuard: boolean): void {
    this.isCancelModalOpen = true;
    this.isFromGuard = fromGuard;
    this.modalDescription = `Are you sure you want to leave? You will lose your unsaved changes if you continue.`;
  }

  openSaveModal(): void {
    if (this.editProjectForm.valid) {
      this.isSaveModalOpen = true;
      this.modalDescription = 'Are you sure you want to save changes?';
    } else {
      this.editProjectForm.markAllAsTouched();
      this.toastService.showToast(ToastState.Error, 'Form invalid');
      setTimeout(() => this.toastService.dismissToast(), 3000);
    }
  }

  archive(): void {
    this.disableGuard(true);
    this.router.navigate(['/projects']).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Info, 'Project archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
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

  save(value: boolean): void {
    this.disableGuard(true);
    if (value) {
      this.projectsService
        .updateProject(this.getProjectData())
        .pipe(first())
        .subscribe(() => {
          this.redirectAfterEdit();
        });
    }
  }

  getProjectData(): Project {
    return {
      id: this.project.id,
      name: this.controls.name?.value,
      image: this.controls.image?.value,
      description: this.controls.description?.value,
      moderator: this.controls.moderator?.value,
      employeesCount: this.controls.employeesCount?.value,
      creationDate: this.project.creationDate,
      billingPeriod: this.controls.billingPeriod?.value,
      overtimeModifier: this.controls.overtimeModifier?.value,
      bonusModifier: this.controls.bonusModifier?.value,
      nightModifier: this.controls.nightModifier?.value,
      holidayModifier: this.controls.holidayModifier?.value,
      active: true,
    };
  }

  redirectAfterEdit(): void {
    new Promise((resolve, _) => {
      this.location.back();
      resolve('done');
    }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Success, 'Project edited'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }
}
