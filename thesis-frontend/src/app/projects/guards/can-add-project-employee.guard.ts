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
export class CanAddProjectEmployeeGuard implements CanActivate {
  private projectIdPosition: number = 2;

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
    const projectId = url.split('/')[this.projectIdPosition];
    const permissions =
      this.permissionsService.getProjectPermissions(projectId);
    if (permissions && permissions.canAddProjectEmployee) {
      return true;
    }

    return this.router.parseUrl('/not-found');
  }
}
