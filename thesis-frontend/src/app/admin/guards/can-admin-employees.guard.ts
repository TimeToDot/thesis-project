import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { PermissionsService } from '../../shared/services/permissions.service';

@Injectable({
  providedIn: 'root',
})
export class CanAdminEmployeesGuard implements CanActivate {
  constructor(
    private permissionsService: PermissionsService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): true | UrlTree {
    const url: string = state.url;

    return this.checkEmployeePermissions(url);
  }

  checkEmployeePermissions(url: string): true | UrlTree {
    if (this.permissionsService.getEmployeePermissions().canAdminEmployees) {
      return true;
    }

    return this.router.parseUrl('/not-found');
  }
}
