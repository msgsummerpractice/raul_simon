import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const cloned = req.clone({
    setHeaders: {
      Authorization: 'Bearer fumhEVZZpWF4AW3n34N4RET2TX7k9z25es2s2jT6StQ',
    },
  });
  return next(cloned);
};
