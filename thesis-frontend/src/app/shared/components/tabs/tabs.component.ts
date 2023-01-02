import { CommonModule, Location } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import {
  ActivatedRoute,
  Router,
  RouterLink,
  RouterLinkActive,
} from '@angular/router';
import { LinkOption } from '../../models/link-option.model';

@Component({
  selector: 'bvr-tabs',
  templateUrl: './tabs.component.html',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
})
export class TabsComponent {
  @Input() hasRouting: boolean = true;
  @Input() navbarOptions: LinkOption[] = [];
  @Input() selectedIndex: number = 0;

  @Output() selectedIndexChange: EventEmitter<number> = new EventEmitter();

  constructor(private location: Location, private router: Router) {}

  selectTab(index: number): void {
    this.selectedIndex = index;
    this.selectedIndexChange.emit(this.selectedIndex);
    this.updateUrl();
  }

  updateUrl(): void {
    const url = this.router.url.split('/');
    url.pop();
    url.push(this.navbarOptions[this.selectedIndex].path);
    url.join('/');
    this.location.replaceState(url.join('/'));
  }
}
