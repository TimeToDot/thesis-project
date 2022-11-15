import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TrackerComponent } from './tracker.component';
import { CalendarModule } from '../calendar/calendar.module';

@NgModule({
  declarations: [TrackerComponent],
  imports: [CalendarModule, CommonModule],
})
export class TrackerModule {}
