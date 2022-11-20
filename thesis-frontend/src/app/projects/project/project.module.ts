import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProjectComponent } from './project.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';

@NgModule({
  declarations: [ProjectComponent],
  imports: [CommonModule, RouterModule, SharedModule],
})
export class ProjectModule {}
