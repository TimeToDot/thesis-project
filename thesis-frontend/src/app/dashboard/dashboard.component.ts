import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bvr-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class DashboardComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
