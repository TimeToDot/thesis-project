import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectsRoutingModule } from './projects-routing.module';
import { ProjectsComponent } from './projects.component';
import { ProjectApprovalsComponent } from './project-approvals/project-approvals.component';
import { MatTabsModule } from '@angular/material/tabs';
import { ProjectComponent } from './project/project.component';

@NgModule({
  declarations: [ProjectsComponent, ProjectApprovalsComponent, ProjectComponent],
  imports: [CommonModule, MatTabsModule, ProjectsRoutingModule],
})
export class ProjectsModule {}
