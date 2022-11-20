import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { ButtonComponent } from '../shared/components/button/button.component';

@NgModule({
  declarations: [LoginComponent],
  imports: [ButtonComponent, CommonModule],
})
export class LoginModule {}
