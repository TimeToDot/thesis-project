import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { SidenavComponent } from './sidenav/sidenav.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { LayoutComponent } from './layout.component';
import { MatListModule } from '@angular/material/list';

@NgModule({
  declarations: [LayoutComponent, SidenavComponent],
  imports: [CommonModule, MatListModule, MatSidenavModule, RouterModule],
  exports: [LayoutComponent, SidenavComponent],
})
export class LayoutModule {}
