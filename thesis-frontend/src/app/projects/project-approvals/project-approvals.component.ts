import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bvr-project-approvals',
  templateUrl: './project-approvals.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class ProjectApprovalsComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
