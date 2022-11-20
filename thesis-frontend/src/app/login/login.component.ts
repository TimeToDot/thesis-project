import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ButtonComponent } from '../shared/components/button/button.component';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'bvr-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, ReactiveFormsModule],
})
export class LoginComponent implements OnInit {
  loginForm = this.fb.group({
    email: [''],
    password: [''],
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {}

  login(): void {
    this.authService.login().subscribe(isLoggedIn => {
      if (isLoggedIn) {
        this.router.navigateByUrl('/dashboard');
      }
    });
  }
}
