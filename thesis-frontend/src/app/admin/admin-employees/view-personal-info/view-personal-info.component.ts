import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { Account } from '../../../shared/models/account.model';

@Component({
  selector: 'bvr-view-personal-info',
  standalone: true,
  imports: [CommonModule, FormFieldComponent],
  templateUrl: './view-personal-info.component.html',
})
export class ViewPersonalInfoComponent {
  @Input() account!: Account;
}
