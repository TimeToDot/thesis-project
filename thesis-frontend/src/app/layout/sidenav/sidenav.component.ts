import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { RouterLinkActive, RouterLinkWithHref } from '@angular/router';
import { PermissionsService } from '../../shared/services/permissions.service';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { CommonModule } from '@angular/common';
import { LinkGroup } from '../../shared/models/link-group.model';
import { EmployeesService } from '../../admin/services/employees.service';
import { Employee } from '../../shared/models/employee.model';
import { first } from 'rxjs';
import { ModalComponent } from '../../shared/components/modal/modal.component';
import { Account } from '../../shared/models/account.model';
import { TokenService } from '../../shared/services/token.service';

@Component({
  selector: 'bvr-sidenav',
  templateUrl: './sidenav.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    ModalComponent,
    RouterLinkActive,
    RouterLinkWithHref,
  ],
})
export class SidenavComponent implements OnInit {
  @Output() openLogoutModal: EventEmitter<void> = new EventEmitter();

  currentEmployee!: Account;
  navMenuGroups: LinkGroup[] = [];

  constructor(
    private employeesService: EmployeesService,
    private permissionsService: PermissionsService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.employeesService
      .getEmployee(this.tokenService.getEmployee())
      .pipe(first())
      .subscribe(employee => (this.currentEmployee = employee));
    this.getNavMenuOptions();
    this.getAdditionalNavMenuOptions();
  }

  getNavMenuOptions(): void {
    const generalOptions: LinkGroup = { name: 'General', options: [] };
    generalOptions.options.push({
      icon: 'dashboard',
      name: 'Dashboard',
      path: '/dashboard',
    });
    generalOptions.options.push({
      icon: 'schedule',
      name: 'Tracker',
      path: '/tracker',
    });
    generalOptions.options.push({
      icon: 'desktop_windows',
      name: 'Projects',
      path: '/projects',
    });
    this.navMenuGroups.push(generalOptions);
  }

  getAdditionalNavMenuOptions(): void {
    const permissions = this.permissionsService.getEmployeePermissions();
    const managementOptions: LinkGroup = { name: 'Management', options: [] };

    if (permissions.canAdminEmployees) {
      managementOptions.options.push({
        icon: 'groups',
        name: 'Employees',
        path: '/admin/employees',
      });
    }

    if (permissions.canAdminPositions) {
      managementOptions.options.push({
        icon: 'work_outline',
        name: 'Positions',
        path: '/admin/positions',
      });
    }

    if (permissions.canAdminSettings) {
      managementOptions.options.push({
        icon: 'settings',
        name: 'Global settings',
        path: '/admin/settings',
      });
    }
    this.navMenuGroups.push(managementOptions);
  }
}
