import { Directive, TemplateRef, ViewContainerRef, inject, effect } from '@angular/core';
import { AuthService } from '../authService/auth-service';

@Directive({
  selector: '[appAuth]',
})
export class Auth {
  private templateRef: TemplateRef<any> = inject(TemplateRef);
  private viewContainer: ViewContainerRef = inject(ViewContainerRef);
  private authService = inject(AuthService);

  constructor() {
    effect(() => {
      if (this.authService.isAuthentificated()) {
        this.viewContainer.createEmbeddedView(this.templateRef);
      } else {
        this.viewContainer.clear();
      }
    });
  }
}
