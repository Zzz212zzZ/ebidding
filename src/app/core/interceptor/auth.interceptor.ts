import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  
  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authToken = localStorage.getItem('Token') || '';
    const authReq = req.clone({
      headers: req.headers.set('Authorization', authToken )
    });

    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          // Unauthorized. Clear the localStorage and navigate to login page.
          localStorage.removeItem('Token');
          localStorage.removeItem('role');
          localStorage.removeItem('name');
          this.router.navigate(['/login']);
        }
        return throwError(error);
      })
    );
  }
}
