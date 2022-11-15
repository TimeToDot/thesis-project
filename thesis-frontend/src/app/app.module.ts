import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProjectsModule } from './projects/projects.module';
import { LayoutModule } from './layout/layout.module';
import { AdminModule } from './admin/admin.module';
import { LoginModule } from './login/login.module';
import { CalendarModule } from './calendar/calendar.module';
import { TrackerModule } from './tracker/tracker.module';

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    CalendarModule,
    LayoutModule,
    LoginModule,
    TrackerModule,

    AdminModule,
    ProjectsModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
