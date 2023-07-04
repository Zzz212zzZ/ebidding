// import { Component, OnInit} from '@angular/core';
// import { NgForm } from '@angular/forms';
// import { Router } from '@angular/router';  // 导入 Router 服务

// @Component({
//   selector: 'app-login',
//   templateUrl: './login.component.html',
//   styleUrls: ['./login.component.less']
// })
// export class LoginComponent implements OnInit {
//   model = {
//     username: '',
//     password: ''
//   };
//    // 在构造函数中注入 Router 服务
//    constructor(private router: Router) { } ;


//   ngOnInit() {
//     // 检查是否存在 Token
//     if (localStorage.getItem('Token')) {
//       // 如果存在 Token，自动跳转到 dashboard
//       this.router.navigate(['/dashboard']);
//     }
//   }





//   onSubmit() {
//     if(this.model.username && this.model.password){
//       console.log("Username: " + this.model.username);
//       console.log("Password: " + this.model.password);



//       // -----------------------------------------------login logic--------------------------------------- 
//       // 假设 login() 方法返回一个包含 Token 的响应
//   //   this.authService.login(this.model.username, this.model.password).subscribe(response => {
//   //   // // 将 Token 存储到 localStorage
//   //   // localStorage.setItem('Token', response.token);

//   //   // // 登录成功后，跳转到 dashboard
//   //   // this.router.navigate(['/dashboard']);
//   // }

//     let token='123456';
//     localStorage.setItem('Token', token);

//     // 登录成功后，跳转到 dashboard
//     this.router.navigate(['/dashboard']);


//     }else{
//       console.log("Please fill all fields");
//     }
//   }
// }




import { Component, OnInit } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';  // 导入 Router 服务



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})


export class LoginComponent implements OnInit {
  // 在构造函数中注入 Router 服务

  constructor(private fb: UntypedFormBuilder, private router: Router) { }


  validateForm!: UntypedFormGroup;

  submitForm(): void {
    if (this.validateForm.valid) {
      console.log('submit', this.validateForm.value);

      let token = '123456';
      localStorage.setItem('Token', token);

      // 登录成功后，跳转到 dashboard
      this.router.navigate(['/dashboard']);

      //       // 假设 login() 方法返回一个包含 Token 的响应
      //   //   this.authService.login(this.model.username, this.model.password).subscribe(response => {
      //   //   // // 将 Token 存储到 localStorage
      //   //   // localStorage.setItem('Token', response.token);


    } else {
      Object.values(this.validateForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }



  }

  ngOnInit(): void {
        // 检查是否存在 Token
    if (localStorage.getItem('Token')) {
      // 如果存在 Token，自动跳转到 dashboard
      this.router.navigate(['/dashboard']);
    }


    this.validateForm = this.fb.group({
      userName: [null, [Validators.required]],
      password: [null, [Validators.required]],
      remember: [true]
    });
  }
}
