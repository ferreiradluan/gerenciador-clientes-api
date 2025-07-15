import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { provideNgxMask } from 'ngx-mask';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { jwtInterceptor } from './core/interceptors/jwt.interceptor';
import { AuthModule } from './features/auth/auth.module';

@NgModule({
  declarations: [
    App
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule
  ],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(
      withFetch(), // Resolve warnings NG02813-NG02818
      withInterceptors([jwtInterceptor])
    ),
    provideNgxMask()
  ],
  bootstrap: [App]
})
export class AppModule { }
