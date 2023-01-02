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
export class CanAddProjectGuard implements CanActivate {
  constructor(
    private permissionsService: PermissionsService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): true | UrlTree {
    return this.checkEmployeePermissions();
  }

  checkEmployeePermissions(): true | UrlTree {
    const permissions = this.permissionsService.getEmployeePermissions();
    if (permissions && permissions.canAddProject) {
      return true;
    }

    return this.router.parseUrl('/not-found');
  }
}
