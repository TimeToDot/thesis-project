import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalendarComponent } from './calendar.component';
import { MonthPipe } from './pipes/month.pipe';

@NgModule({
  declarations: [CalendarComponent, MonthPipe],
  imports: [CommonModule],
  exports: [CalendarComponent, MonthPipe],
})
export class CalendarModule {}
