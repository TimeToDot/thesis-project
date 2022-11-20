import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsRoutingModule } from './projects-routing.module';
import { ProjectsComponent } from './projects.component';
import { ProjectModule } from './project/project.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [ProjectsComponent],
  imports: [CommonModule, ProjectModule, ProjectsRoutingModule, SharedModule],
})
export class ProjectsModule {}
