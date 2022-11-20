import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ButtonComponent } from '../shared/components/button/button.component';
import { AuthService } from '../shared/services/auth.service';

@Component({
  selector: 'bvr-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [ButtonComponent],
})
export class LoginComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {}

  login(): void {
    this.authService.login().subscribe(isLoggedIn => {
      if (isLoggedIn) {
        this.router.navigateByUrl('/dashboard');
      }
    });
  }
}
