import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ChildrenOutletContexts, Router, RouterOutlet } from '@angular/router';
import { ModalComponent } from '../shared/components/modal/modal.component';
import { AuthService } from '../shared/services/auth.service';
import { sidenavAnimation } from './animations/sidenav.animation';
import { SidenavComponent } from './sidenav/sidenav.component';

@Component({
  selector: 'bvr-layout',
  templateUrl: './layout.component.html',
  standalone: true,
  imports: [CommonModule, ModalComponent, SidenavComponent, RouterOutlet],
  animations: [sidenavAnimation],
})
export class LayoutComponent {
  isLogoutModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private authService: AuthService,
    private contexts: ChildrenOutletContexts,
    private router: Router
  ) {}

  getRouteAnimationData() {
    return this.contexts.getContext('primary')?.route?.snapshot.data[
      'sidenavTabs'
    ];
  }

  openLogoutModal(): void {
    this.isLogoutModalOpen = true;
    this.modalDescription = 'Are you sure you want to leave?';
  }

  logout(value: boolean): void {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }
}
