import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AbstractControl, AbstractControlDirective } from '@angular/forms';

@Component({
  selector: 'bvr-error',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './error.component.html',
})
export class ErrorComponent {
  @Input() control!: AbstractControl | AbstractControlDirective | null;

  errorsList: string[] = [];
  errorMessages: { [name: string]: (param: any) => string } = {
    required: (params: any) => `This field is required`,
    minlength: (params: any) => `Value is too short`,
    maxlength: (params: any) => `Value is too long`,
    minValue: (params: any) => `Minimum value is ${params}`,
    maxValue: (params: any) => `Maximum value is ${params}`,
    password: (params: any) =>
      `Password must be at least 8 characters long, must include lower and upper case characters and at least one number and symbol`,
    passwordMatching: (params: any) => `Passwords don't match`,
    dateRange: (params: any) => `Start date cannot be later than end date`,
    timeRange: (params: any) =>
      `Start time and date cannot be later than end time and date`,
    pattern: (params: any) => `Invalid value`,
  };

  getErrorList(): string[] {
    if (this.control && this.control.errors) {
      this.errorsList = [];
      Object.keys(this.control.errors).map(error => {
        if (this.control) {
          this.control.touched || this.control.dirty
            ? this.errorsList.push(
                this.errorMessages[error](this.control.errors![error])
              )
            : '';
        }
      });
      return this.errorsList;
    }
    return [];
  }
}
