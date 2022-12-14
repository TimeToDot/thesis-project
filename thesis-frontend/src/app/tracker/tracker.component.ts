import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NavigationStart, Router, RouterModule } from '@angular/router';
import { filter, Subscription } from 'rxjs';
import { CalendarComponent } from '../calendar/calendar.component';
import { ButtonComponent } from '../shared/components/button/button.component';
import { TabsComponent } from '../shared/components/tabs/tabs.component';
import { LinkOption } from '../shared/model/link-option.model';

@Component({
  selector: 'bvr-tracker',
  templateUrl: './tracker.component.html',
  standalone: true,
  imports: [
    ButtonComponent,
    CalendarComponent,
    CommonModule,
    RouterModule,
    TabsComponent,
  ],
})
export class TrackerComponent implements OnInit, OnDestroy {
  navbarOptions: LinkOption[] = [
    { name: 'Tasks List', path: 'tasks-list' },
    { name: 'Add New Task', path: 'add-new-task' },
  ];
  taskListVisible: boolean = false;

  private routerSubscription!: Subscription;

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.observeTabNameChange();
  }

  ngOnDestroy(): void {
    this.routerSubscription.unsubscribe();
  }

  showTaskList(value: boolean): void {
    this.taskListVisible = value;
  }

  observeTabNameChange(): void {
    this.routerSubscription = this.router.events
      .pipe(filter(event => event instanceof NavigationStart))
      .subscribe(event => {
        const e = event as NavigationStart;
        if (
          e.url.includes('edit-task') &&
          this.isOptionInNavbarOptions('edit-task')
        ) {
          this.updateNavbarOptions('Add New Task', 'Edit Task', 'edit-task');
        } else if (this.isOptionInNavbarOptions('add-new-task')) {
          this.updateNavbarOptions('Edit Task', 'Add New Task', 'add-new-task');
        }
      });
  }

  isOptionInNavbarOptions(path: string): boolean {
    return this.navbarOptions.findIndex(value => value.path === path) === -1;
  }

  updateNavbarOptions(nameToReplace: string, name: string, path: string): void {
    this.navbarOptions = this.navbarOptions.filter(
      option => option.name !== nameToReplace
    );
    this.navbarOptions.push({ name: name, path: path });
  }
}
