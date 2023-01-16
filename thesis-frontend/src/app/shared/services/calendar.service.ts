import { formatDate } from '@angular/common';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CalendarService {
  private _currentDay: BehaviorSubject<string> = new BehaviorSubject<string>(
    formatDate(new Date(Date.now()), 'MM/dd/yyyy', 'en')
  );

  public readonly currentDay: Observable<string> =
    this._currentDay.asObservable();

  constructor() {}

  updateCurrentDay(currentDay: string): void {
    this._currentDay.next(currentDay);
  }
}
