import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bvr-project-settings',
  templateUrl: './project-settings.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class ProjectSettingsComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
