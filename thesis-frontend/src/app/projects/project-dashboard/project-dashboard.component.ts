import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { ToastComponent } from '../../shared/components/toast/toast.component';

@Component({
  selector: 'bvr-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref, ToastComponent],
})
export class ProjectDashboardComponent {}
