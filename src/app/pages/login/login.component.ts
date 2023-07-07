import { Component, OnInit, ViewEncapsulation } from '@angular/core';

import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { FormsModule, } from '@angular/forms';
// import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzTabsModule } from 'ng-zorro-antd/tabs';
import { Router } from '@angular/router';  // 导入 Router 服务
import { SpinService } from 'src/app/core/services/spin.service';
import { AccountService, UserInfo } from 'src/app/core/services/account.service';


const fnCheckLoginForm = function checkLoginForm(form: FormGroup): boolean {
  // 检查登录表单的字段
  ['username', 'password'].forEach(key => {
    const control = form.controls[key];

    // 检查字段是否存在
    if (control) {
      control.markAsDirty();
      control.updateValueAndValidity();
    }
  });

  return !form.invalid;
};

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less'],
  standalone: true,
  // encapsulation: ViewEncapsulation.None,  // 添加这行
  imports: [
    FormsModule,
    ReactiveFormsModule,
    // BrowserAnimationsModule,
    NzButtonModule,
    NzInputModule,
    NzFormModule,
    NzTabsModule,
  ]
})


export class LoginComponent implements OnInit {
  constructor(private router: Router, private spinService: SpinService, private accountService: AccountService) { }

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
      username: new FormControl('', [Validators.required]),
      password: new FormControl('', [Validators.required]),
      confirmPassword: new FormControl('', [Validators.required]),
    });

    this.loginForm = new FormGroup({
      username: new FormControl('', [Validators.required]),
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

    if (!fnCheckLoginForm(this.loginForm)) {
      return;
    }
    const param = this.loginForm.getRawValue();

    this.spinService.setCurrentGlobalSpinStore(true);
    console.log(param);
    this.accountService.login(param).subscribe((data: UserInfo) => {

        let token = data.token;
        let role = data.role.toLocaleLowerCase();

       // localStorage.setItem('Authorization', `Bearer ${data.token as string}`);
       localStorage.setItem('Token', `Bearer ${token}`);
       //设置localStorage的role
       localStorage.setItem('role', role);

      if(role === 'client'){
        console.log(role);
        console.log(token);
        this.router.navigateByUrl('/layout/client/homepage');
      }
      if(role === 'trader'){
        // this.router.navigateByUrl('/layout/sales/admin');
        this.router.navigateByUrl('/layout/trader/homepage');
      }
      this.spinService.setCurrentGlobalSpinStore(false);
  });

  }

}

export enum Role { CLIENT, TRADER };
