import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import * as dayjs from 'dayjs';
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

  static dateRangeValidator(startDate: string, endDate: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const startDateValue = control.get(startDate)?.value;
      const endDateValue = control.get(endDate)?.value;
      if (startDateValue && endDateValue) {
        const isRangeValid = dayjs(startDateValue).isAfter(dayjs(endDateValue));
        return !isRangeValid ? null : { dateRange: true };
      }
      return null;
    };
  }

  static timeRangeValidator(
    startDate: string,
    startTime: string,
    endDate: string,
    endTime: string
  ): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const startDateValue = control.get(startDate)?.value;
      const startTimeValue = control.get(startTime)?.value;
      const endDateValue = control.get(endDate)?.value;
      const endTimeValue = control.get(endTime)?.value;
      if (startDateValue && startTimeValue && endDateValue && endTimeValue) {
        const isRangeValid = dayjs(
          `${startDateValue} ${startTimeValue}`
        ).isAfter(dayjs(`${endDateValue} ${endTimeValue}`));
        return !isRangeValid ? null : { timeRange: true };
      }
      return null;
    };
  }
}
