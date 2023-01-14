import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import * as dayjs from 'dayjs';
import { ButtonComponent } from '../shared/components/button/button.component';
import { Status } from '../shared/enum/status.enum';
import { Day } from './models/day.model';
import { MonthPipe } from './pipes/month.pipe';

@Component({
  selector: 'bvr-calendar',
  templateUrl: './calendar.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, MonthPipe],
})
export class CalendarComponent implements OnInit {
  currentMonth!: number;
  currentYear!: number;
  currentDay!: string;
  days: string[] = [];
  gridMonthEndDay!: number;
  gridMonthStartDay!: number;
  monthEndDay!: number;
  monthGrid: Day[][] = [];
  monthStartDay!: number;

  private readonly sundayOffset: number = 1;

  constructor() {}

  ngOnInit(): void {
    this.currentDay = dayjs().date(dayjs().date()).format('MM/DD/YYYY');
    this.currentMonth = dayjs().month();
    this.currentYear = dayjs().month(this.currentMonth).year();
    this.initDaysOfWeek();
    this.generateGrid();
  }

  initDaysOfWeek(): void {
    this.days = [
      'Monday',
      'Tuesday',
      'Wednesday',
      'Thursday',
      'Friday',
      'Saturday',
      'Sunday',
    ];
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
    this.monthStartDay = dayjs().month(this.currentMonth).startOf('M').date();
    this.gridMonthStartDay =
      this.monthStartDay + this.sundayOffset - monthStartWeekday;
    if (this.gridMonthStartDay === 2) {
      this.gridMonthStartDay -= 7;
    }
  }

  getGridEndDay(): void {
    const monthEndWeekday = dayjs().month(this.currentMonth).endOf('M').day();
    this.monthEndDay = dayjs().month(this.currentMonth).endOf('M').date();
    this.gridMonthEndDay =
      this.monthEndDay + 6 + this.sundayOffset - monthEndWeekday;
  }

  getMonthGrid(): void {
    this.monthGrid = [];
    for (let i = this.gridMonthStartDay, k = 0; k < 6; ++k) {
      let week = [] as Day[];
      for (let j = 0; j < 7; ++i, ++j) {
        week.push({
          date: dayjs().month(this.currentMonth).date(i).format('MM/DD/YYYY'),
          disabled: i < this.monthStartDay || i > this.monthEndDay,
          status: Status.None,
        });
      }
      this.monthGrid.push(week);
    }
  }

  previousMonth(): void {
    --this.currentMonth;
    this.currentYear = dayjs().month(this.currentMonth).year();
    this.generateGrid();
  }

  nextMonth(): void {
    ++this.currentMonth;
    this.currentYear = dayjs().month(this.currentMonth).year();
    this.generateGrid();
  }

  selectDay(day: Day): void {
    if (!day.disabled) {
      this.currentDay = day.date;
    }
  }

  isCurrentDay(day: Day): boolean {
    return day.date === this.currentDay;
  }
}
