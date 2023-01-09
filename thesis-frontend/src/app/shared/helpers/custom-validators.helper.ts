import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { Regex } from './regex.helper';

export class CustomValidators {
  static minValue(min: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.value < min ? { minValue: min } : null;
    };
  }

  static maxValue(max: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return control.value > max ? { maxValue: max } : null;
    };
  }

  static password(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      return Regex.PASSWORD.test(control.value) ? null : { password: true };
    };
  }

  static passwordMatchingValidator(
    field1: string,
    field2: string
  ): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const password = control.get(field1)?.value;
      const repeatedPassword = control.get(field2)?.value;
      if (password && repeatedPassword) {
        return password.localeCompare(repeatedPassword) === 0
          ? null
          : { passwordMatching: true };
      }
      return null;
    };
  }
}
