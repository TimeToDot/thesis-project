import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CalendarComponent } from './calendar.component';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [CalendarComponent],
  imports: [MatButtonModule, CommonModule],
  exports: [CalendarComponent],
})
export class CalendarModule {}
