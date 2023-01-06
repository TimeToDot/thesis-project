import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { first, Subject } from 'rxjs';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import {
  ActivatedRoute,
  ChildrenOutletContexts,
  Router,
  RouterLinkWithHref,
  RouterOutlet,
} from '@angular/router';
import { Account } from '../../../shared/models/account.model';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { TabsComponent } from '../../../shared/components/tabs/tabs.component';
import { LinkOption } from '../../../shared/models/link-option.model';
import { tabAnimation } from '../../../shared/animations/tab.animation';
import { EmployeesService } from '../../services/employees.service';

@Component({
  selector: 'bvr-view-employee',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    RouterLinkWithHref,
    RouterOutlet,
    TabsComponent,
    ToastComponent,
  ],
  templateUrl: './view-employee.component.html',
  animations: [tabAnimation],
})
export class ViewEmployeeComponent {
  employee!: Account;
  enableFormButtons: boolean = true;
  isArchiveModalOpen: boolean = false;
  isFromGuard: boolean = false;
  isGuardDisabled: boolean = false;
  modalDescription: string = '';
  navbarOptions: LinkOption[] = [];
  redirectSubject: Subject<boolean> = new Subject<boolean>();

  constructor(
    private contexts: ChildrenOutletContexts,
    private employeesService: EmployeesService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getEmployee();
    this.getNavbarOptions();
  }

  getEmployee(): void {
    const employeeId = this.route.snapshot.paramMap.get('id');
    if (employeeId) {
      this.employeesService
        .getEmployee(employeeId)
        .pipe(first())
        .subscribe(employee => {
          this.employee = employee;
        });
    }
  }

  getNavbarOptions(): void {
    this.navbarOptions.push({ name: 'Personal', path: 'personal-info' });
    this.navbarOptions.push({ name: 'Address', path: 'address-info' });
    this.navbarOptions.push({ name: 'Employment', path: 'employment-info' });
    this.navbarOptions.push({ name: 'Account', path: 'account-info' });
    this.navbarOptions.push({ name: 'Projects', path: 'projects-info' });
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive ${this.employee.firstName} ${this.employee.lastName}? This action cannot be undone.`;
  }

  archive(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () => this.toastService.showToast(ToastState.Info, 'Employee archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }

  disableGuard(value: boolean): void {
    this.isGuardDisabled = true;
    this.redirectSubject.next(value);
  }

  onOutletLoaded(component: any): void {
    component.employee = this.employee;
  }

  getRouteAnimationData() {
    return this.contexts.getContext('primary')?.route?.snapshot.data['tabs'];
  }

  toggleFormButtons(value: boolean): void {
    this.enableFormButtons = value;
  }
}
