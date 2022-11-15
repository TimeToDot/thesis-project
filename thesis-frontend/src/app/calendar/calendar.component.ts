import { Component, OnInit } from '@angular/core';
import * as dayjs from 'dayjs';

@Component({
  selector: 'bvr-calendar',
  templateUrl: './calendar.component.html',
})
export class CalendarComponent implements OnInit {
  currentMonth!: number;
  monthEndDay!: number;
  monthGrid: string[][] = [];
  monthStartDay!: number;

  constructor() {}

  ngOnInit(): void {
    this.currentMonth = dayjs().month();
    this.generateGrid();
  }

  generateGrid(): void {
    this.getGridStartDay();
    this.getGridEndDay();
    this.getMonthGrid();
  }

  getGridStartDay(): void {
    const monthStartWeekday = dayjs()
      .month(this.currentMonth)
      .startOf('M')
      .day();
    this.monthStartDay =
      dayjs().month(this.currentMonth).startOf('M').date() - monthStartWeekday;
  }

  getGridEndDay(): void {
    const monthEndWeekday = dayjs().month(this.currentMonth).endOf('M').day();
    this.monthEndDay =
      dayjs().month(this.currentMonth).endOf('M').date() + 6 - monthEndWeekday;
  }

  getMonthGrid(): void {
    this.monthGrid = [];
    for (let i = this.monthStartDay; i < this.monthEndDay; ) {
      let week = [] as string[];
      for (let j = 0; j < 7; ++i, ++j) {
        week.push(
          dayjs().month(this.currentMonth).date(i).format('DD/MM/YYYY')
        );
      }
      this.monthGrid.push(week);
    }
  }

  previousMonth(): void {
    --this.currentMonth;
    this.generateGrid();
  }

  nextMonth(): void {
    ++this.currentMonth;
    this.generateGrid();
  }
}
