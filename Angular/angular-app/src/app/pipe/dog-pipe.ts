import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'dogPipe',
})
export class DogPipe implements PipeTransform {
  transform(value: string, times: number): string {
    value = value + ' ';
    return value.repeat(times);
  }
}
