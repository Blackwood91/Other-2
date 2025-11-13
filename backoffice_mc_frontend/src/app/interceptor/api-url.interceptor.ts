import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Inject, Injectable} from '@angular/core';
import { Router } from '@angular/router';
import { catchError, finalize, Observable, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SharedService } from '../shared.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';

@Injectable()
export class ApiUrlInterceptor implements HttpInterceptor {
    loadActive: boolean = false;
    timeOutMessage?: NodeJS.Timeout;
    ritardoResponse: boolean = false;

    constructor(private router: Router, 
                private sharedService: SharedService,
                @Inject(APP_ENVIRONMENT) private env:AppEnvironment ) { }

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

        let request;
        if (req.url.includes("-ritardoRisposta")) {
            this.ritardoResponse = true;
            request = req.clone({
                // Viene formattata in modo corretto il path della richiesta
                url: this.env.pathApi + req.url.replace("-ritardoRisposta", ""),
                withCredentials: true, // Per l'utilizzo dei cookie in sessione
            });
        }
        else {
            request = req.clone({
                url: this.env.pathApi + req.url,
                withCredentials: true, // Per l'utilizzo dei cookie in sessione
            });
        }

        // Attivazione caricamento del service 
        this.sharedService.onLoadService();

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
                this.router.navigateByUrl('/login', { replaceUrl: true });
                break;
        }
        // Se l'errore non è elencato nello switch viene lanciato al service
        return throwError(() => error);
    }

    private timeRequestOffLoad(url: string) {
        const endUrl = url.substring(url.lastIndexOf("/") + 1);

        // Solo se non è in corso un caricamento di una richiesta precedente verrà disattivato (per evitare problemi di asincronicità)
        if (this.ritardoResponse == true) {
            this.setTimeOffLoad(3000);
            this.ritardoResponse = false;
        }
        else {
            if (this.loadActive == false) {
                this.sharedService.offLoad();
            }
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
