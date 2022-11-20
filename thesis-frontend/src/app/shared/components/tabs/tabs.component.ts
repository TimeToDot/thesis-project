import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { LinkOption } from '../../model/link-option.model';

@Component({
  selector: 'bvr-tabs',
  templateUrl: './tabs.component.html',
  standalone: true,
  imports: [CommonModule, RouterModule],
})
export class TabsComponent {
  @Input() navbarOptions: LinkOption[] = [];
}
