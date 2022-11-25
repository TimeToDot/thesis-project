import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfirmUserComponent } from '../confirm-user/confirm-user.component';
import { FindUserComponent } from '../find-user/find-user.component';
import { SelectWageComponent } from '../select-wage/select-wage.component';

@Component({
  selector: 'bvr-add-project-user',
  standalone: true,
  imports: [
    CommonModule,
    ConfirmUserComponent,
    FindUserComponent,
    SelectWageComponent,
  ],
  templateUrl: './add-project-user.component.html',
})
export class AddProjectUserComponent {
  step: number = 1;

  nextStep(): void {
    ++this.step;
  }

  previousStep(): void {
    --this.step;
  }
}