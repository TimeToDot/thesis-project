import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ChildrenOutletContexts, RouterOutlet } from '@angular/router';
import { sidenavAnimation } from './animations/sidenav.animation';
import { SidenavComponent } from './sidenav/sidenav.component';

@Component({
  selector: 'bvr-layout',
  templateUrl: './layout.component.html',
  standalone: true,
  imports: [CommonModule, SidenavComponent, RouterOutlet],
  animations: [sidenavAnimation],
})
export class LayoutComponent {
  constructor(private contexts: ChildrenOutletContexts) {}

  getRouteAnimationData() {
    return this.contexts.getContext('primary')?.route?.snapshot.data[
      'sidenavTabs'
    ];
  }
}
