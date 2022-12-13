import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { PermissionsService } from '../../shared/services/permissions.service';
import { AuthService } from '../../shared/services/auth.service';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { CommonModule } from '@angular/common';
import { LinkGroup } from '../../shared/model/link-group.model';
import { EmployeesService } from '../../admin/services/employees.service';
import { Employee } from '../../shared/model/employee.model';

@Component({
  selector: 'bvr-sidenav',
  templateUrl: './sidenav.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
})
export class SidenavComponent implements OnInit {
  currentEmployee: Employee = {
    id: '',
    firstName: '',
    lastName: '',
    email: '',
    image: '',
    position: '',
    employmentDate: '',
    workingTime: 0,
    active: false,
  };
  navMenuGroups: LinkGroup[] = [];

  constructor(
    private employeesService: EmployeesService,
    private authService: AuthService,
    private permissionsService: PermissionsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentEmployee = this.employeesService.getEmployee('1');
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

  logout(): void {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }
}
