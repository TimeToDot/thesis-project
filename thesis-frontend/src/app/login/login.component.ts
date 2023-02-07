import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ButtonComponent } from '../shared/components/button/button.component';
import { LoginData } from '../shared/models/login-data.model';
import { AuthService } from '../shared/services/auth.service';
import { ValidationService } from '../shared/services/validation.service';

@Component({
  selector: 'bvr-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, ReactiveFormsModule],
})
export class LoginComponent implements OnInit {
  controls: any = {};
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

  ngOnInit(): void {
    this.getFormControls();
  }

  getFormControls(): void {
    Object.keys(this.loginForm.controls).forEach(control => {
      this.controls[control] = this.loginForm.get([control]);
    });
  }

  login(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.getLoginData()).subscribe(isLoggedIn => {
        // console.log(isLoggedIn);
        // if (isLoggedIn) {
        //   this.router.navigateByUrl('/dashboard');
        // }
      });
    } else {
      this.loginForm.markAllAsTouched();
    }
  }

  getLoginData(): LoginData {
    return {
      username: this.controls.email?.value,
      password: this.controls.password?.value,
    };
  }

  // login(): void {
  //   if (this.loginForm.valid) {
  //     this.authService.login().subscribe(isLoggedIn => {
  //       if (isLoggedIn) {
  //         this.router.navigateByUrl('/dashboard');
  //       }
  //     });
  //   } else {
  //     this.loginForm.markAllAsTouched();
  //   }
  // }

  forgotPassword(): void {
    this.isPasswordForgotten = !this.isPasswordForgotten;
  }

  showErrors(name: string): boolean {
    return this.validationService.showErrors(this.loginForm, [name]);
  }
}
