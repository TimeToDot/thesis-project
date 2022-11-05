import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PermissionsService } from '../../shared/services/permissions.service';
import { LinkOption } from '../../shared/model/link-option.model';
import { AuthService } from '../../shared/services/auth.service';

@Component({
  selector: 'bvr-sidenav',
  templateUrl: './sidenav.component.html',
  styles: [],
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
    this.navMenuOptions.push({ name: 'Dashboard', path: '/dashboard' });
    this.navMenuOptions.push({ name: 'Tracker', path: '/tracker' });
    this.navMenuOptions.push({ name: 'Projects', path: '/projects' });
  }

  getAdditionalNavMenuOptions(): void {
    const permissions = this.permissionsService.getUserPermissions();

    if (permissions.canAdminUsers) {
      this.navMenuOptions.push({ name: 'Users', path: '/admin/users' });
    }

    if (permissions.canAdminSettings) {
      this.navMenuOptions.push({
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
