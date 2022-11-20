import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from './components/button/button.component';
import { TabsComponent } from './components/tabs/tabs.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [ButtonComponent, TabsComponent],
  imports: [CommonModule, RouterModule],
  exports: [ButtonComponent, TabsComponent],
})
export class SharedModule {}
