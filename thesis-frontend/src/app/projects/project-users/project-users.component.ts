import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bvr-project-users',
  templateUrl: './project-users.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class ProjectUsersComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
