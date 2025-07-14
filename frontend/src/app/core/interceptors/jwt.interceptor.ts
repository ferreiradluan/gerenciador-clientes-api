import { Injectable, inject } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpInterceptorFn
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // Obter o token JWT do AuthService
    const token = this.authService.getToken();
    
    // Verificar se não é uma requisição de login
    const isLoginRequest = request.url.includes('/auth/login');
    
    // Se existe token e não é requisição de login, adicionar header Authorization
    if (token && !isLoginRequest) {
      const clonedRequest = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
      return next.handle(clonedRequest);
    }
    
    // Caso contrário, enviar a requisição original
    return next.handle(request);
  }
}

// Functional interceptor para uso com withInterceptors()
export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const token = authService.getToken();
  const isLoginRequest = req.url.includes('/auth/login');
  
  if (token && !isLoginRequest) {
    const clonedRequest = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(clonedRequest);
  }
  
  return next(req);
};
