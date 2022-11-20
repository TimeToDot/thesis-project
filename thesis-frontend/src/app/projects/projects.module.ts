import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsRoutingModule } from './projects-routing.module';
import { ProjectsComponent } from './projects.component';
import { ProjectModule } from './project/project.module';
import { ButtonComponent } from '../shared/components/button/button.component';

@NgModule({
  declarations: [ProjectsComponent],
  imports: [
    ButtonComponent,
    CommonModule,
    ProjectModule,
    ProjectsRoutingModule,
  ],
})
export class ProjectsModule {}
