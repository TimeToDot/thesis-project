import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BillingInfoComponent } from '../billing-info/billing-info.component';
import { ContactInfoComponent } from '../contact-info/contact-info.component';
import { GeneralInfoComponent } from '../general-info/general-info.component';
import { PersonalInfoComponent } from '../personal-info/personal-info.component';

@Component({
  selector: 'bvr-create-user',
  standalone: true,
  imports: [
    BillingInfoComponent,
    CommonModule,
    ContactInfoComponent,
    GeneralInfoComponent,
    PersonalInfoComponent,
  ],
  templateUrl: './create-user.component.html',
  styles: [],
})
export class CreateUserComponent {
  step: number = 1;

  nextStep(): void {
    ++this.step;
  }

  previousStep(): void {
    --this.step;
  }
}