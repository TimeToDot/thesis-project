import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/components/button/button.component';
import {
  ActivatedRoute,
  ChildrenOutletContexts,
  Router,
  RouterLinkWithHref,
} from '@angular/router';
import { TabsComponent } from '../../shared/components/tabs/tabs.component';
import { LinkOption } from '../../shared/models/link-option.model';
import { EditBillingInfoComponent } from '../edit-billing-info/edit-billing-info.component';
import { EditGeneralInfoComponent } from '../edit-general-info/edit-general-info.component';
import { EditModeratorInfoComponent } from '../edit-moderator-info/edit-moderator-info.component';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { tabAnimation } from '../../shared/animations/tab.animation';

@Component({
  selector: 'bvr-edit-project',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    EditBillingInfoComponent,
    EditGeneralInfoComponent,
    EditModeratorInfoComponent,
    ReactiveFormsModule,
    RouterLinkWithHref,
    TabsComponent,
  ],
  templateUrl: './edit-project.component.html',
  animations: [tabAnimation],
})
export class EditProjectComponent implements OnInit {
  editProjectForm!: FormGroup;
  enableFormButtons: boolean = true;
  navbarOptions: LinkOption[] = [];
  tabIndex: number = 0;

  constructor(
    private contexts: ChildrenOutletContexts,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getCurrentTab();
    this.getNavbarOptions();
    this.createForm();
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
        projectName: ['', [Validators.required]],
        logo: [null, [Validators.required]],
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

  goToDashboard(): void {
    this.router.navigate(['..'], { relativeTo: this.route });
  }

  updateTabIndex(index: number): void {
    this.tabIndex = index;
  }

  toggleFormButtons(value: boolean): void {
    this.enableFormButtons = value;
  }
}
