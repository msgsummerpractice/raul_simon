import { Directive, TemplateRef, ViewContainerRef, inject, effect } from '@angular/core';
import { AuthService } from '../service/authService/auth-service';

@Directive({
  selector: '[appAuth]',
})
export class Auth {
  private readonly templateRef: TemplateRef<any> = inject(TemplateRef);
  private readonly viewContainer: ViewContainerRef = inject(ViewContainerRef);
  private readonly authService = inject(AuthService);

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
