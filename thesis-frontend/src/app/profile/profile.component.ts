import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  ChildrenOutletContexts,
  RouterLinkWithHref,
  RouterOutlet,
} from '@angular/router';
import { first } from 'rxjs';
import { EmployeesService } from '../admin/services/employees.service';
import { tabAnimation } from '../shared/animations/tab.animation';
import { ButtonComponent } from '../shared/components/button/button.component';
import { TabsComponent } from '../shared/components/tabs/tabs.component';
import { Account } from '../shared/models/account.model';
import { LinkOption } from '../shared/models/link-option.model';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'bvr-profile',
  templateUrl: './profile.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    RouterLinkWithHref,
    RouterOutlet,
    TabsComponent,
  ],
  animations: [tabAnimation],
})
export class ProfileComponent implements OnInit {
  employee!: Account;
  navbarOptions: LinkOption[] = [];

  constructor(
    private authService: AuthService,
    private contexts: ChildrenOutletContexts,
    private employeesService: EmployeesService
  ) {}

  ngOnInit(): void {
    this.getEmployee();
    this.getNavbarOptions();
  }

  getEmployee(): void {
    const employeeId = this.authService.getLoggedEmployeeId();
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

  onOutletLoaded(component: any): void {
    component.employee = this.employee;
  }

  getRouteAnimationData() {
    return this.contexts.getContext('primary')?.route?.snapshot.data['tabs'];
  }
}
