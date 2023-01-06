import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Account } from '../../../../shared/models/account.model';
import { FormFieldComponent } from '../../../../shared/components/form-field/form-field.component';

@Component({
  selector: 'bvr-view-account-info',
  standalone: true,
  imports: [CommonModule, FormFieldComponent],
  templateUrl: './view-account-info.component.html',
})
export class ViewAccountInfoComponent {
  @Input() employee!: Account;
}
