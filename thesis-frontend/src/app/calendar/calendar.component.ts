import { Component, OnInit } from '@angular/core';
import * as dayjs from 'dayjs';

@Component({
  selector: 'bvr-calendar',
  templateUrl: './calendar.component.html',
})
export class CalendarComponent implements OnInit {
  monthEndDay!: number;
  monthEndWeekday!: number;
  monthStartDay!: number;
  monthStartWeekday!: number;

  monthGrid: string[][] = [];

  constructor() {}

  ngOnInit(): void {
    this.monthEndDay = dayjs().endOf('M').date();
    this.monthEndWeekday = dayjs().endOf('M').day();
    this.monthStartDay = dayjs().startOf('M').date();
    this.monthStartWeekday = dayjs().startOf('M').day();
    while (this.monthStartWeekday !== 0) {
      --this.monthStartDay;
      --this.monthStartWeekday;
    }
    while (this.monthEndWeekday !== 6) {
      ++this.monthEndDay;
      ++this.monthEndWeekday;
    }

    for (let i = this.monthStartDay; i < this.monthEndDay; ) {
      let week = [] as string[];
      for (let j = 0; j < 7; ++i, ++j) {
        week.push(dayjs().date(i).format('DD/MM/YYYY'));
      }
      this.monthGrid.push(week);
    }
    console.log(this.monthGrid);
  }
}
