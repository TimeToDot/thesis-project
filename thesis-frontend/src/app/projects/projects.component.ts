import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ButtonComponent } from '../shared/components/button/button.component';

@Component({
  selector: 'bvr-projects',
  templateUrl: './projects.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterModule],
})
export class ProjectsComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
