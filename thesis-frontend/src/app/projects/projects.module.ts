import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsRoutingModule } from './projects-routing.module';
import { ProjectsComponent } from './projects.component';
import { ProjectModule } from './project/project.module';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [ProjectsComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    ProjectModule,
    ProjectsRoutingModule,
  ],
})
export class ProjectsModule {}
