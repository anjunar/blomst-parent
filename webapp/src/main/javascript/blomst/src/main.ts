import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';

declare global {
  function secureFetch(url : string, method? : string, data? : any) : Promise<any>
}

platformBrowserDynamic().bootstrapModule(AppModule, {
  ngZone: 'noop'
}).catch(err => console.error(err));
