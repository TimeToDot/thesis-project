import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalendarComponent } from './calendar.component';
import { MonthPipe } from './pipes/month.pipe';
import { ButtonComponent } from '../shared/components/button/button.component';

@NgModule({
  declarations: [CalendarComponent, MonthPipe],
  imports: [ButtonComponent, CommonModule],
  exports: [CalendarComponent, MonthPipe],
})
export class CalendarModule {}
