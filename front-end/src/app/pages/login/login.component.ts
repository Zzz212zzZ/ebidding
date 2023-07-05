
// import { Component, OnInit } from '@angular/core';
// import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
// import { Router } from '@angular/router';  // 导入 Router 服务



// @Component({
//   selector: 'app-login',
//   templateUrl: './login.component.html',
//   styleUrls: ['./login.component.less']
// })


// export class LoginComponent implements OnInit {
//   // 在构造函数中注入 Router 服务

//   constructor(private fb: UntypedFormBuilder, private router: Router) { }


//   validateForm!: UntypedFormGroup;

//   submitForm(): void {
//     if (this.validateForm.valid) {
//       console.log('submit', this.validateForm.value);

//       let token = '123456';
//       localStorage.setItem('Token', token);

//       // 登录成功后，跳转到 dashboard
//       this.router.navigate(['/dashboard']);

//       //       // 假设 login() 方法返回一个包含 Token 的响应
//       //   //   this.authService.login(this.model.username, this.model.password).subscribe(response => {
//       //   //   // // 将 Token 存储到 localStorage
//       //   //   // localStorage.setItem('Token', response.token);


//     } else {
//       Object.values(this.validateForm.controls).forEach(control => {
//         if (control.invalid) {
//           control.markAsDirty();
//           control.updateValueAndValidity({ onlySelf: true });
//         }
//       });
//     }



//   }

//   ngOnInit(): void {
//         // 检查是否存在 Token
//     if (localStorage.getItem('Token')) {
//       // 如果存在 Token，自动跳转到 dashboard
//       this.router.navigate(['/dashboard']);
//     }


//     this.validateForm = this.fb.group({
//       userName: [null, [Validators.required]],
//       password: [null, [Validators.required]],
//       remember: [true]
//     });
//   }
// }





import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';  // 导入 Router 服务

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {
  constructor(private router: Router) { }

  signupForm!: FormGroup;
  loginForm!: FormGroup;
  selectedIndex = 1;

  ngOnInit(): void {

    // 检查是否存在 Token
    if (localStorage.getItem('Token')) {
      // 如果存在 Token，自动跳转到 dashboard
      this.router.navigate(['/layout']);
    }


    this.signupForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('', [Validators.required]),
    });

    this.loginForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
    });

  }

  submitSignUp(): void {
    if (this.signupForm.valid) {
      // 检查 password 和 confirmPassword 是否匹配
      if (this.signupForm.value.password !== this.signupForm.value.confirmPassword) {
        // 如果不匹配，显示错误消息
        alert('Passwords do not match.');
        return;
      }
      // 实现注册逻辑
    }
  }

  submitLogin(): void {
    if (this.loginForm.valid) {
      // 实现登录逻辑
      let token = '123456';
      localStorage.setItem('Token', token);

      // 登录成功后，跳转到 dashboard
      this.router.navigate(['/dashboard']);
    }
  }


}
