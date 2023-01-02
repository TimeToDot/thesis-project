import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ButtonComponent } from '../shared/components/button/button.component';
import { AuthService } from '../shared/services/auth.service';
import { ValidationService } from '../shared/services/validation.service';

@Component({
  selector: 'bvr-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, ReactiveFormsModule],
})
export class LoginComponent implements OnInit {
  isPasswordForgotten: boolean = false;
  loginForm = this.fb.group({
    email: ['', [Validators.required]],
    password: ['', [Validators.required]],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private validationService: ValidationService
  ) {}

  ngOnInit(): void {}

  login(): void {
    if (this.loginForm.valid) {
      this.authService.login().subscribe(isLoggedIn => {
        if (isLoggedIn) {
          this.router.navigateByUrl('/dashboard');
        }
      });
    } else {
      this.loginForm.markAllAsTouched();
    }
  }

  forgotPassword(): void {
    this.isPasswordForgotten = !this.isPasswordForgotten;
  }

  showErrors(name: string): boolean {
    return this.validationService.showErrors(this.loginForm, [name]);
  }
}
