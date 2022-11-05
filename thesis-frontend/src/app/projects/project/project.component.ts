import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bvr-project',
  templateUrl: './project.component.html',
  styles: [],
})
export class ProjectComponent implements OnInit {
  links: string[] = [];

  constructor() {}

  ngOnInit(): void {
    this.links = ['dashboard', 'tasks', 'users', 'approvals'];
  }
}
