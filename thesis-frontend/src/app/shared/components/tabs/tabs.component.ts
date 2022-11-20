import { Component, Input } from '@angular/core';
import { LinkOption } from '../../model/link-option.model';

@Component({
  selector: 'bvr-tabs',
  templateUrl: './tabs.component.html',
  styles: [],
})
export class TabsComponent {
  @Input() navbarOptions: LinkOption[] = [];
}
