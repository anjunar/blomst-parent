import { Pipe, PipeTransform } from '@angular/core';
import * as moment from "moment/moment";

@Pipe({
  name: 'humanize'
})
export class HumanizePipe implements PipeTransform {
  transform(value: string, ...args: unknown[]): string {
    let then = moment(value);
    return then.fromNow()
  }

}
