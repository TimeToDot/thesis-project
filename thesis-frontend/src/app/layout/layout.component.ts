import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SidenavComponent } from './sidenav/sidenav.component';

@Component({
  selector: 'bvr-layout',
  templateUrl: './layout.component.html',
  standalone: true,
  imports: [SidenavComponent, RouterModule],
})
export class LayoutComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
