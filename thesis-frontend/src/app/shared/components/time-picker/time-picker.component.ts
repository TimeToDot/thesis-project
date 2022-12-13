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
  touched: boolean = false;

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

  getPreviousHour(): number {
    return this.hour === 0 ? 23 : this.hour - 1;
  }

  previousHour(): void {
    this.hour = this.getPreviousHour();
    this.updateSelectedTime();
  }
  getPreviousMinutes(): number {
    return this.minutes === 0 ? 45 : this.minutes - 15;
  }

  previousMinutes(): void {
    this.minutes = this.getPreviousMinutes();
    this.updateSelectedTime();
  }

  getNextHour(): number {
    return this.hour === 23 ? 0 : this.hour + 1;
  }

  nextHour(): void {
    this.hour = this.getNextHour();
    this.updateSelectedTime();
  }

  getNextMinutes(): number {
    return this.minutes === 45 ? 0 : this.minutes + 15;
  }

  nextMinutes(): void {
    this.minutes = this.getNextMinutes();
    this.updateSelectedTime();
  }

  updateSelectedTime(): void {
    this.markAsTouched();
    this.selectedTime = `${this.hour < 10 ? '0' + this.hour : this.hour}:${
      this.minutes === 0 ? '00' : this.minutes
    }`;
    this.onChange(this.selectedTime);
  }

  toggleTimePicker(): void {
    this.timePickerEnabled = !this.timePickerEnabled;
  }

  markAsTouched() {
    if (!this.touched) {
      this.onTouched();
      this.touched = true;
    }
  }

  writeValue(currentTime: string): void {
    this.selectedTime = currentTime;
  }

  onChange = (currentTime: string) => {};

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
