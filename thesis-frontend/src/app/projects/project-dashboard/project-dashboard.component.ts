import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';

@Component({
  selector: 'bvr-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
})
export class ProjectDashboardComponent {}
