import { formatDate } from '@angular/common';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CalendarService {
  private _currentDay: BehaviorSubject<string> = new BehaviorSubject<string>(
    formatDate(new Date(Date.now()), 'MM/dd/yyyy', 'en')
  );
  private _refreshCalendar: Subject<string> = new Subject<string>();

  public readonly currentDay: Observable<string> =
    this._currentDay.asObservable();
  public readonly refreshCalendar: Observable<string> =
    this._refreshCalendar.asObservable();

  constructor() {}

  updateCurrentDay(currentDay: string): void {
    this._currentDay.next(currentDay);
  }

  triggerCalendarRefresh(): void {
    this._refreshCalendar.next(this._currentDay.value);
  }
}
