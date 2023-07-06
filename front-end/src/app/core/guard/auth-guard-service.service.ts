import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';




export const AuthGuardService: CanActivateFn = (route, state) => {

  const router = inject(Router);
  console.log(localStorage.getItem('Token'))
    if (!localStorage.getItem('Token')) {
      return router.parseUrl('/login');
    }
    return true;
};

// export class AuthGuardService {

//   constructor(private router: Router) { }

//   CanActivateFn()=> {
   
//   }
// }
