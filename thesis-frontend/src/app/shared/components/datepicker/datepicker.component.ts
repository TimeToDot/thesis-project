import { Component, HostListener, Input } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import * as dayjs from 'dayjs';
import { Day } from '../../../calendar/model/day.model';
import { Status } from '../../enum/status.enum';
import { MonthPipe } from '../../../calendar/pipes/month.pipe';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'bvr-datepicker',
  standalone: true,
  imports: [ButtonComponent, CommonModule, MonthPipe],
  templateUrl: './datepicker.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: DatepickerComponent,
    },
  ],
})
export class DatepickerComponent implements ControlValueAccessor {
  @Input() name: string = '';

  currentMonth!: number;
  currentYear!: number;
  currentDay!: string;
  days: string[] = [];
  calendarEnabled: boolean = false;
  gridMonthEndDay!: number;
  gridMonthStartDay!: number;
  monthEndDay!: number;
  monthGrid: Day[][] = [];
  monthStartDay!: number;
  touched: boolean = false;

  private readonly sundayOffset: number = 1;
  private wasInside: boolean = false;

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

  toggleCalendar(): void {
    this.calendarEnabled = !this.calendarEnabled;
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
      this.markAsTouched();
      this.currentDay = formatDate(day.date, 'yyyy-MM-dd', 'en');
      this.onChange(this.currentDay);
    }
  }

  markAsTouched() {
    if (!this.touched) {
      this.onTouched();
      this.touched = true;
    }
  }

  isCurrentDay(day: Day): boolean {
    return formatDate(day.date, 'yyyy-MM-dd', 'en') === this.currentDay;
  }

  writeValue(currentDay: string): void {
    this.currentDay = currentDay;
  }

  onChange = (currentDay: string) => {};

  registerOnChange(onChange: any): void {
    this.onChange = onChange;
  }

  onTouched = () => {};

  registerOnTouched(onTouched: any): void {
    this.onTouched = onTouched;
  }

  @HostListener('click')
  click(): void {
    this.wasInside = true;
  }

  @HostListener('document:click')
  hideDatepicker(): void {
    if (!this.wasInside) {
      this.calendarEnabled = false;
    }
    this.wasInside = false;
  }
}
