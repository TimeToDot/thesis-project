import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalendarComponent } from './calendar.component';
import { MonthPipe } from './pipes/month.pipe';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [CalendarComponent, MonthPipe],
  imports: [CommonModule, SharedModule],
  exports: [CalendarComponent, MonthPipe],
})
export class CalendarModule {}
