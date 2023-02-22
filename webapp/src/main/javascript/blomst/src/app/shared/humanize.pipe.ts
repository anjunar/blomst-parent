import { Pipe, PipeTransform } from '@angular/core';
import * as moment from "moment/moment";

@Pipe({
  name: 'humanize'
})
export class HumanizePipe implements PipeTransform {
  transform(value: string, ...args: unknown[]): string {
    let now = moment(new Date())
    let then = moment(value);
    let duration = moment.duration(now.diff(then));
    return duration.humanize();
  }

}
