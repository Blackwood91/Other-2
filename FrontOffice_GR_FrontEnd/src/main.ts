import { InjectionToken, enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';

export interface AppEnvironment {
  pathApi: string;

  uriConfig: {
    clientId: string;
    path_iang: string;
    path_iang_logout: string;
    logout_redirect_uri: string;
    scope: string;
    redirectUri: string;
    response_mode: string; 
    response_type: string;
    code_challenge: string;
    code_challenge_method: string;
  }
  uriLogout: string;
  rowsTable: number;

}

//platformBrowserDynamic().bootstrapModule(AppModule)
 // .catch(err => console.error(err));

if (environment.production) {
  enableProdMode();
}
  
export const APP_ENVIRONMENT = new InjectionToken<AppEnvironment>('App Enviromnent Injection Token');

/**
 * @returns if present in the url replace %%ORIGIN%% with window.location.origi 
 * If %%ORIGIN%% isn't present it return the url.
 */
function formatUrl(url:string): string {
  return url.includes('%%ORIGIN%%') ? url.replace('%%ORIGIN%%', window.location.origin) : url;
}

window.fetch('assets/environment.json')
  .then(response => response.json())
  .then((appEnvironment: AppEnvironment) => {
  // add prefix to be api endpoint
    appEnvironment.pathApi = formatUrl(appEnvironment.pathApi);    
    appEnvironment.rowsTable = appEnvironment.rowsTable;
    appEnvironment.uriLogout = formatUrl(appEnvironment.uriLogout);

    platformBrowserDynamic([{ provide: APP_ENVIRONMENT, useValue: appEnvironment }]).bootstrapModule(AppModule)
      .catch(err => console.error(err));
})