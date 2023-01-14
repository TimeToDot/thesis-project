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
  @Input() control!: AbstractControl | AbstractControlDirective;

  errorsList: string[] = [];
  errorMessages: { [name: string]: (param: any) => string } = {
    required: (params: any) => `This field is required.`,
  };

  getErrorList(): string[] {
    if (this.control && this.control.errors) {
      this.errorsList = [];
      Object.keys(this.control.errors).map(error => {
        this.control.touched || this.control.dirty
          ? this.errorsList.push(
              this.errorMessages[error](this.control.errors![error])
            )
          : '';
      });
      return this.errorsList;
    }
    return [];
  }
}
