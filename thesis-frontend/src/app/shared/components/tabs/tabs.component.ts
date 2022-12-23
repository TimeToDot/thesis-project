import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { LinkOption } from '../../models/link-option.model';

@Component({
  selector: 'bvr-tabs',
  templateUrl: './tabs.component.html',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
})
export class TabsComponent {
  @Input() navbarOptions: LinkOption[] = [];
}
