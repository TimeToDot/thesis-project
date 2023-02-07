import { inject } from '@angular/core';
import {
  HttpRequest,
  HttpEvent,
  HttpInterceptorFn,
  HttpHandlerFn,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TokenService } from '../services/token.service';

const TOKEN_HEADER_KEY = 'Authorization';

export const authInterceptor: HttpInterceptorFn = (
  request: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<HttpEvent<unknown>> => {
  let authReq = request;
  const tokenService = inject(TokenService);
  const token = tokenService.getToken();
  const employeeId = tokenService.getEmployee();
  if (token && employeeId) {
    authReq = request.clone({
      headers: request.headers
        .set(TOKEN_HEADER_KEY, `thesis=${token}`)
        .set('employeeId', employeeId),
      withCredentials: true,
    });
  }
  return next(authReq);
};
