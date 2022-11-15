import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalendarComponent } from './calendar.component';
import { MatButtonModule } from '@angular/material/button';
import { MonthPipe } from './pipes/month.pipe';

@NgModule({
  declarations: [CalendarComponent, MonthPipe],
  imports: [MatButtonModule, CommonModule],
  exports: [CalendarComponent],
})
export class CalendarModule {}
