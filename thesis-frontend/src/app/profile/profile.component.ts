import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'bvr-profile',
  templateUrl: './profile.component.html',
  standalone: true,
  imports: [CommonModule],
})
export class ProfileComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
