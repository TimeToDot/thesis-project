import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { sidenavAnimation } from './layout/animations/sidenav.animation';

@Component({
  standalone: true,
  selector: 'bvr-root',
  templateUrl: './app.component.html',
  imports: [CommonModule, RouterOutlet],
  animations: [sidenavAnimation],
})
export class AppComponent {
  title = 'thesis-frontend';

  constructor() {}
}
