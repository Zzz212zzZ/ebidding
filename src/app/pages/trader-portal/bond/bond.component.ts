import { Component, OnInit } from '@angular/core';
import { CommonModule} from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzTabsModule } from 'ng-zorro-antd/tabs';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { BwicService } from 'src/app/core/services/bwic.service';

export interface Bonds{
  bondId: String;
  coupon: String;
  cusip: String;
  issuer: String;
  maturityDate: String;
  rating: String;
  transaction_counts: Number;
}

let nowBond : Bonds;

const fnCheckBwicForm = function checkBwicForm(form: FormGroup): boolean {
  // 检查登录表单的字段
  ['startPrice', 'size' , 'StartTime' , 'DueTime'].forEach(key => {
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
  selector: 'app-bond',
  standalone: true,
  imports: [
    CommonModule,
    NzLayoutModule,
    NzMenuModule,
    NzFormModule,
    NzInputModule,     
    NzButtonModule,     
    NzGridModule,       
    NzCheckboxModule,   
    ReactiveFormsModule,
    NzTabsModule,
    NzSpinModule,
    NzCardModule,
    NzTableModule,
    NzDividerModule,
    NzDrawerModule,
    NzSelectModule,
    NzDatePickerModule,
    NzIconModule,
  ],
  templateUrl: './bond.component.html',
  styleUrls: ['./bond.component.less']
})
export class BondComponent implements OnInit{

  constructor(private bwicService: BwicService){}
  bwicForm = new FormGroup({
    startPrice : new FormControl('',[Validators.required]),
    size : new FormControl('',[Validators.required]),
    StartTime : new FormControl('',[Validators.required]),
    DueTime : new FormControl('',[Validators.required])
  })

  visible = false;
  listOfBonds: Bonds[] = [];//初始化
  submitForm(): void {
    console.log('submit', this.bwicForm.value);
  }

  open(data: Bonds): void {
    this.visible = true;
    console.log(data);
    nowBond = data;
  }

  close(): void {
    this.visible = false;
    this.bwicForm.reset();
  }

  CreateBWIC(): void{
    if (!fnCheckBwicForm(this.bwicForm)) {
      alert("All values need to be entered !!!")
      return;
    }
    // this.visible = false;
    const formData = this.bwicForm.getRawValue();
    const param = {
      bondId: String(nowBond.bondId),
      startPrice: formData.startPrice || '',
      startTime: formData.StartTime || '',
      dueTime: formData.DueTime || '',
      size: formData.size || ''
    };
    this.bwicService.createBwic(param).subscribe(() => {
      alert("BWIC created successfully"); // 创建成功后显示提示消息
      this.bwicForm.reset();
    },
    (error) => {
      // 创建失败时的错误处理
      alert("BWIC creation failed");
      console.error("Failed to create BWIC:", error);
    });
  }

  ngOnInit() {
    this.bwicService.getAllBonds().subscribe((data: Bonds[]) => {
      this.listOfBonds = data;
      // console.log(this.listOfBonds);
    })
  }

}
