import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import { Inject, Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {catchError, finalize, Observable, throwError} from 'rxjs';
import { environment } from 'src/environments/environment';
import { SharedService } from '../shared.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';


@Injectable()
export class ApiUrlInterceptor implements HttpInterceptor {
    loadActive: boolean = false;
    timeOutMessage?: NodeJS.Timeout;
    private apiUrl = this.env.pathApi;
    
    constructor(private router: Router, 
    			private sharedService: SharedService, 
    			@Inject(APP_ENVIRONMENT) private env:AppEnvironment) { }

   intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // Per skippare l'interceptor con il reindirizzamento della pagina e la rimozione del caricamento
        if (req.url.startsWith('/assets')) { 
            return next.handle(req);
        }
        else if (req.url.endsWith('startSecurity') || req.url.endsWith('login') || 
                 req.url.endsWith('logout') || req.url.endsWith('getUltimaModificaMediatore')) {
            const request = req.clone({
                url: this.env.pathApi + req.url,
                withCredentials: true, // Per l'utilizzo dei cookie in sessione
            });
            
            return next.handle(request);
        }
        
        // Attivazione caricamento del service 
        this.sharedService.onLoadService();

        const request = req.clone({
            url: this.env.pathApi + req.url,
            withCredentials: true, // Per l'utilizzo dei cookie in sessione
        });
        
        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => this.handleErrorRes(error)),
            finalize(() => {
                this.timeRequestOffLoad(req.url);
            })
        );
    }

    private handleErrorRes(error: HttpErrorResponse): Observable<never> {  
        // Rimozione del caricamento
        this.sharedService.offLoad();
        // Cattura per errori standard      
        switch (error.status) {
            case 401:
                this.router.navigateByUrl('/login', {replaceUrl: true});
                break;
        }
        // Se l'errore non è elencato nello switch viene lanciato al service
        return throwError(() => error);
    }

    private timeRequestOffLoad(url: string) {
        const endUrl = url.substring(url.lastIndexOf("/") + 1);
        // Solo negli endpoint dei form verrà utilizzato il ritardo
        switch (endUrl) {
            case "UpdateMediatori":
                // Verra diattivato il caricamento dopo la risposta del server + 3 secondi (Fix per evitare doppio click dell'invio) 
                this.setTimeOffLoad(3000);
                break;
            case "setMediatori":
                this.setTimeOffLoad(3000);
                break;
            case "saveMediatoriFileCSV":
                this.setTimeOffLoad(3000);
                break;
            default:
                // Solo se non è in corso un caricamento di una richiesta precedente verrà disattivato (per evitare problemi di asincronicità)
                if (this.loadActive == false) {
                    this.sharedService.offLoad();
                }
                break;
        }
    }

    private setTimeOffLoad(time: number) {
        this.loadActive = true;
        this.timeOutMessage = setTimeout(() => {
            this.sharedService.offLoad();
            this.loadActive = false;
        }, time);
    }
}