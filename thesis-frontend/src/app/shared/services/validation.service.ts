import { Injectable } from '@angular/core';
import { FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root',
})
export class ValidationService {
  constructor() {}

  isRequired(form: FormGroup, path: string[]): boolean {
    return form.get(path)?.hasValidator(Validators.required) ? true : false;
  }

  showErrors(form: FormGroup, path: string[]): boolean {
    if (path.length > 0) {
      return !!(
        form.get(path)?.invalid &&
        form.get(path)?.errors &&
        (form.get(path)?.dirty || form.get(path)?.touched)
      );
    } else {
      return !!(form.invalid && form.errors && (form.dirty || form.touched));
    }
  }
}
