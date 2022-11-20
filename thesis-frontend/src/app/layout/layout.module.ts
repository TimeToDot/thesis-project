import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SidenavComponent } from './sidenav/sidenav.component';
import { LayoutComponent } from './layout.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonComponent } from '../shared/components/button/button.component';

@NgModule({
  declarations: [LayoutComponent, SidenavComponent],
  imports: [
    BrowserAnimationsModule,
    ButtonComponent,
    CommonModule,
    RouterModule,
  ],
  exports: [LayoutComponent, SidenavComponent],
})
export class LayoutModule {}
