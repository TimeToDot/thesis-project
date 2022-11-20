import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bvr-project-dashboard',
  templateUrl: './project-dashboard.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class ProjectDashboardComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
