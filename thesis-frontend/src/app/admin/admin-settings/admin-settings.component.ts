import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bvr-admin-settings',
  templateUrl: './admin-settings.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class AdminSettingsComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
