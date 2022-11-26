import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { PermissionsService } from '../../shared/services/permissions.service';
import { LinkOption } from '../../shared/model/link-option.model';
import { AuthService } from '../../shared/services/auth.service';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'bvr-sidenav',
  templateUrl: './sidenav.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
})
export class SidenavComponent implements OnInit {
  navMenuOptions: LinkOption[] = [];

  constructor(
    private authService: AuthService,
    private permissionsService: PermissionsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.getNavMenuOptions();
    this.getAdditionalNavMenuOptions();
  }

  getNavMenuOptions(): void {
    this.navMenuOptions.push({
      icon: 'dashboard',
      name: 'Dashboard',
      path: '/dashboard',
    });
    this.navMenuOptions.push({
      icon: 'schedule',
      name: 'Tracker',
      path: '/tracker',
    });
    this.navMenuOptions.push({
      icon: 'desktop_windows',
      name: 'Projects',
      path: '/projects',
    });
  }

  getAdditionalNavMenuOptions(): void {
    const permissions = this.permissionsService.getUserPermissions();

    if (permissions.canAdminUsers) {
      this.navMenuOptions.push({
        icon: 'group',
        name: 'Users',
        path: '/admin/users',
      });
    }

    if (permissions.canAdminPositions) {
      this.navMenuOptions.push({
        icon: 'cases',
        name: 'Positions',
        path: '/admin/positions',
      });
    }

    if (permissions.canAdminSettings) {
      this.navMenuOptions.push({
        icon: 'settings',
        name: 'Global settings',
        path: '/admin/settings',
      });
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }
}
