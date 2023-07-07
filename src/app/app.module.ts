import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { IconsProviderModule } from './icons-provider.module';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzCheckboxModule } from 'ng-zorro-antd/checkbox';
import { ReactiveFormsModule } from '@angular/forms';
import { NzTabsModule } from 'ng-zorro-antd/tabs';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { authInterceptor } from './core/interceptor/auth.interceptor';



registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    IconsProviderModule,
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
    
  ],
  providers: [
    { provide: NZ_I18N, useValue: en_US },
    provideHttpClient(
      withInterceptors(
        [authInterceptor]
      )
    ),
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
