import { CanActivateFn, Router, ActivatedRouteSnapshot } from '@angular/router';
import { inject } from '@angular/core';

export const AuthGuardService: CanActivateFn = (route: ActivatedRouteSnapshot, state) => {
  const router = inject(Router);

  const expectedRole = route.data['expectedRole'];
  const token = localStorage.getItem('Token');
  const userRole = localStorage.getItem('role'); // assuming you store user role in local storage
  console.log(`AuthGuardService: expectedRole: ${expectedRole}, token: ${token}, userRole: ${userRole}`);


  if (!token || !userRole || (userRole !== 'trader' && userRole !== 'client')) {
    // if token or userRole is null, or userRole is not valid, redirect to login page
    return router.parseUrl('/login');
  } 

  if (state.url === '/layout') {
    if (token && userRole) {
      // If token and user role are present, redirect to the correct path
      const redirectUrl = `/layout/${(userRole ?? 'client').toLowerCase()}`; // construct the URL based on the userRole
      console.log(`Redirecting to: ${redirectUrl}`);
      return router.parseUrl(redirectUrl);
    } 
  }

  if (state.url.includes('/client') && userRole !== 'client') {
    // If URL includes '/client' but userRole is not 'client', redirect to trader homepage
    console.log(`Redirecting to: /layout/trader/homepage`);
    return router.parseUrl('/layout/trader/homepage');
  } 

  if (state.url.includes('/trader') && userRole !== 'trader') {
    // If URL includes '/trader' but userRole is not 'trader', redirect to client homepage
    console.log(`Redirecting to: /layout/client/homepage`);
    return router.parseUrl('/layout/client/homepage');
  } 


  
  // if everything is fine, do not interrupt navigation
  return true;
};
