import { Component, HostListener, OnInit } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import { ControlValueAccessor, NG_VALUE_ACCESSOR } from '@angular/forms';
import { ButtonComponent } from '../button/button.component';
import * as dayjs from 'dayjs';

@Component({
  selector: 'bvr-time-picker',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './time-picker.component.html',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: TimePickerComponent,
    },
  ],
})
export class TimePickerComponent implements OnInit, ControlValueAccessor {
  hour: number = 0;
  minutes: number = 0;
  selectedTime: string = '00:00';
  timePickerEnabled: boolean = false;

  private wasInside: boolean = false;

  constructor() {}

  ngOnInit(): void {
    this.getCurrentTime();
  }

  getCurrentTime(): void {
    const time = this.roundToMinutes(15);
    this.hour = parseInt(time.split(':')[0]);
    this.minutes = parseInt(time.split(':')[1]);
  }

  roundToMinutes(minutes: number): string {
    const ms = 1000 * 60 * minutes;
    return formatDate(
      new Date(Math.round(new Date().getTime() / ms) * ms),
      'HH:mm',
      'en'
    );
  }

  previousHour(): void {
    this.hour === 0 ? (this.hour = 23) : --this.hour;
    this.updateSelectedTime();
  }

  previousMinutes(): void {
    this.minutes === 0 ? (this.minutes = 45) : (this.minutes -= 15);
    this.updateSelectedTime();
  }

  nextHour(): void {
    this.hour === 24 ? (this.hour = 0) : ++this.hour;
    this.updateSelectedTime();
  }

  nextMinutes(): void {
    this.minutes === 45 ? (this.minutes = 0) : (this.minutes += 15);
    this.updateSelectedTime();
  }

  updateSelectedTime(): void {
    this.selectedTime = `${this.hour}:${this.minutes}`;
  }

  toggleTimePicker(): void {
    this.timePickerEnabled = !this.timePickerEnabled;
  }

  writeValue(currentDay: string): void {
    this.selectedTime = currentDay;
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
      this.timePickerEnabled = false;
    }
    this.wasInside = false;
  }
}
