import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TasksToRejectService {
  private _tasksToReject: BehaviorSubject<string[]> = new BehaviorSubject<
    string[]
  >([]);

  public readonly tasksToReject: Observable<string[]> =
    this._tasksToReject.asObservable();

  constructor() {}

  addTask(employeeTaskId: string): void {
    this._tasksToReject.getValue().push(employeeTaskId);
    this._tasksToReject.next(this._tasksToReject.getValue());
  }

  removeTask(employeeTaskId: string): void {
    this._tasksToReject.next(
      this._tasksToReject.getValue().filter(task => task !== employeeTaskId)
    );
  }
}
