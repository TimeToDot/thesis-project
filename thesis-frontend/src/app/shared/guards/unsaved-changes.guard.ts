import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanDeactivate,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UnsavedChangesGuard implements CanDeactivate<any> {
  canDeactivate(
    component: any,
    currentRoute: ActivatedRouteSnapshot,
    currentState: RouterStateSnapshot,
    nextState?: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    let subject = new Subject<boolean>();
    component.openCancelModal(true);
    subject = component.redirectSubject;
    const subscription = subject.subscribe(value => {
      console.log(value);
      subscription.unsubscribe();
    });
    return subject.asObservable();
  }
}
