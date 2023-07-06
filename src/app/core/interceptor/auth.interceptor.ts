import { HttpInterceptorFn } from '@angular/common/http';

export const TokenKey = 'Authorization'; 
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authToken = localStorage.getItem('Token') || '';

  console.log('authInterceptor', authToken);

  // Clone the request and replace the original headers with
  // cloned headers, updated with the authorization.
  const authReq = req.clone({
    headers: req.headers.set(TokenKey, authToken )
  });

  // send cloned request with header to the next handler.
  return next(authReq);
};