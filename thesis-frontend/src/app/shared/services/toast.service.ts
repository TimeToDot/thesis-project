import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ToastState } from '../enum/toast-state';

@Injectable({
  providedIn: 'root',
})
export class ToastService {
  showToast$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  toastMessage$: BehaviorSubject<string> = new BehaviorSubject<string>('');
  toastState$: BehaviorSubject<string> = new BehaviorSubject<string>(
    ToastState.Success
  );

  constructor() {}

  showToast(toastState: string, message: string): void {
    this.toastState$.next(toastState);
    this.toastMessage$.next(message);
    this.showToast$.next(true);
  }

  dismissToast(): void {
    this.showToast$.next(false);
  }
}
