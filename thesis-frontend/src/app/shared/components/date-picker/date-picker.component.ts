import {
  Component,
  ElementRef,
  HostListener,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import * as dayjs from 'dayjs';
import { Day } from '../../../calendar/models/day.model';
import { Status } from '../../enum/status.enum';
import { MonthPipe } from '../../../calendar/pipes/month.pipe';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';

@Component({
  selector: 'bvr-date-picker',
  standalone: true,
  imports: [ButtonComponent, CommonModule, MonthPipe],
  templateUrl: './date-picker.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: DatePickerComponent,
    },
  ],
})
export class DatePickerComponent implements OnInit, ControlValueAccessor {
  @Input() name: string = '';

  @ViewChild('dropdown') dropdown!: ElementRef<HTMLElement>;
  @ViewChild('dropdownContent') dropdownContent!: ElementRef<HTMLElement>;

  calendarEnabled: boolean = false;
  currentMonth!: number;
  currentYear!: number;
  currentDay!: string;
  days: string[] = [];
  gridMonthEndDay!: number;
  gridMonthStartDay!: number;
  isMonth: boolean = true;
  monthEndDay!: number;
  monthGrid: Day[][] = [];
  monthStartDay!: number;
  months: string[][] = [];
  touched: boolean = false;

  private readonly sundayOffset: number = 1;
  private wasInside: boolean = false;

  constructor() {}

  ngOnInit(): void {
    this.currentDay = dayjs().date(dayjs().date()).format('MM/DD/YYYY');
    this.currentMonth = dayjs().month();
    this.currentYear = dayjs().month(this.currentMonth).year();
    this.initDaysOfWeek();
    this.initMonths();
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

  initMonths(): void {
    this.months = [
      ['JAN', 'FEB', 'MAR'],
      ['APR', 'MAY', 'JUN'],
      ['JUL', 'AUG', 'SEP'],
      ['OCT', 'NOV', 'DEC'],
    ];
  }

  generateGrid(): void {
    this.getGridStartDay();
    this.getGridEndDay();
    this.getMonthGrid();
  }

  getGridStartDay(): void {
    const monthStartWeekday = dayjs()
      .year(this.currentYear)
      .month(this.currentMonth)
      .startOf('M')
      .day();
    this.monthStartDay = dayjs()
      .year(this.currentYear)
      .month(this.currentMonth)
      .startOf('M')
      .date();
    this.gridMonthStartDay =
      this.monthStartDay + this.sundayOffset - monthStartWeekday;
    if (this.gridMonthStartDay === 2) {
      this.gridMonthStartDay -= 7;
    }
  }

  getGridEndDay(): void {
    const monthEndWeekday = dayjs()
      .year(this.currentYear)
      .month(this.currentMonth)
      .endOf('M')
      .day();
    this.monthEndDay = dayjs()
      .year(this.currentYear)
      .month(this.currentMonth)
      .endOf('M')
      .date();
    this.gridMonthEndDay =
      this.monthEndDay + 6 + this.sundayOffset - monthEndWeekday;
  }

  getMonthGrid(): void {
    this.monthGrid = [];
    for (let i = this.gridMonthStartDay, k = 0; k < 6; ++k) {
      let week = [] as Day[];
      for (let j = 0; j < 7; ++i, ++j) {
        week.push({
          date: dayjs()
            .year(this.currentYear)
            .month(this.currentMonth)
            .date(i)
            .format('MM/DD/YYYY'),
          disabled: i < this.monthStartDay || i > this.monthEndDay,
          status: Status.None,
        });
      }
      this.monthGrid.push(week);
    }
  }

  toggleCalendar(): void {
    this.calendarEnabled = !this.calendarEnabled;
    if (this.calendarEnabled) {
      this.showDropdownUpwards();
    }
  }

  showDropdownUpwards(): void {
    setTimeout(() => {
      const dropdownRect = this.dropdown.nativeElement.getBoundingClientRect();
      const dropdownContentHeight =
        this.dropdownContent.nativeElement.offsetHeight;
      const availableSpaceBelow = window.innerHeight - dropdownRect.bottom;

      this.dropdownContent.nativeElement.style.top =
        dropdownContentHeight > availableSpaceBelow
          ? `-${dropdownContentHeight}px`
          : '100%';
    }, 0);
  }

  toggleMonth(): void {
    this.isMonth = !this.isMonth;
  }

  previousMonth(): void {
    if (this.currentMonth === 0) {
      this.currentMonth = 11;
      --this.currentYear;
    } else {
      --this.currentMonth;
    }
    this.generateGrid();
  }

  nextMonth(): void {
    if (this.currentMonth === 11) {
      this.currentMonth = 0;
      ++this.currentYear;
    } else {
      ++this.currentMonth;
    }
    this.generateGrid();
  }

  previousYear(): void {
    --this.currentYear;
    this.generateGrid();
  }

  nextYear(): void {
    ++this.currentYear;
    this.generateGrid();
  }

  selectDay(day: Day): void {
    if (!day.disabled) {
      this.markAsTouched();
      this.currentDay = formatDate(day.date, 'yyyy-MM-dd', 'en');
      this.onChange(this.currentDay);
      this.calendarEnabled = false;
    }
  }

  selectMonth(month: number): void {
    this.currentMonth = month;
    this.generateGrid();
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

  isCurrentMonth(month: number): boolean {
    return month === this.currentMonth % 12;
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
