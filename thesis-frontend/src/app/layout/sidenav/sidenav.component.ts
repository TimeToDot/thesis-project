import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { PermissionsService } from '../../shared/services/permissions.service';
import { LinkOption } from '../../shared/model/link-option.model';
import { AuthService } from '../../shared/services/auth.service';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { CommonModule } from '@angular/common';
import { LinkGroup } from '../../shared/model/link-group.model';
import { AccountService } from '../../shared/services/account.service';
import { Account } from '../../shared/model/account.model';

@Component({
  selector: 'bvr-sidenav',
  templateUrl: './sidenav.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
})
export class SidenavComponent implements OnInit {
  currentUser: Account = { email: '', firstName: '', image: '', lastName: '' };
  navMenuGroups: LinkGroup[] = [];

  constructor(
    private accountService: AccountService,
    private authService: AuthService,
    private permissionsService: PermissionsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.currentUser = this.accountService.getUserAccount();
    this.getNavMenuOptions();
    this.getAdditionalNavMenuOptions();
    console.log(this.navMenuGroups);
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
    const permissions = this.permissionsService.getUserPermissions();
    const managementOptions: LinkGroup = { name: 'Management', options: [] };

    if (permissions.canAdminUsers) {
      managementOptions.options.push({
        icon: 'group',
        name: 'Users',
        path: '/admin/users',
      });
    }

    if (permissions.canAdminPositions) {
      managementOptions.options.push({
        icon: 'cases',
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
