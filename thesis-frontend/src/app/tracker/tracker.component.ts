import { Component, OnInit } from '@angular/core';
import { CalendarComponent } from '../calendar/calendar.component';

@Component({
  selector: 'bvr-tracker',
  templateUrl: './tracker.component.html',
  standalone: true,
  imports: [CalendarComponent],
})
export class TrackerComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
