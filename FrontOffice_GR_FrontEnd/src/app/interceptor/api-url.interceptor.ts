import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Router} from '@angular/router';
import { catchError, finalize, Observable, throwError} from 'rxjs';
import { environment } from 'src/environments/environment';
import { SharedService } from '../shared.service';
import { APP_ENVIRONMENT, AppEnvironment } from 'src/main';


@Injectable()
export class ApiUrlInterceptor implements HttpInterceptor {
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
                this.sharedService.offLoad();
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
}