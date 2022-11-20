import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bvr-project-tasks',
  templateUrl: './project-tasks.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class ProjectTasksComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
