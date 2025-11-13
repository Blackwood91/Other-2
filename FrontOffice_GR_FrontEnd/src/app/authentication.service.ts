import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import {BehaviorSubject, Observable, catchError, of, switchMap, tap, throwError} from "rxjs";

@Injectable({providedIn: 'root'})
export class AuthenticationService {

    private isAuthenticatedSubject = new BehaviorSubject<boolean | null>(null);
    private currentUserSubject = new BehaviorSubject<any | null>(null);

    constructor(private http: HttpClient) {}
  
    startSecurity(): Observable<any> {
        return this.http.get<any>('user/startSecurity').pipe(
            catchError((error: HttpErrorResponse) => {
                return throwError("Si è verificato un errore nella comunicazione con il backend");
            })
        )
    }

    utenteLoggato(): Observable<any> {
        return this.http.get<any>('user/utentLoggato').pipe(
            catchError((error: HttpErrorResponse) => {
              // restituisce un messaggio di errore che verra visualizzato nel subscribe
              return throwError(error.error);
            })
        )
    }


    login(username: string, password: string): Observable<any> {
        const params = new HttpParams().set('username', username).set('password', password);
        const headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
        return this.http.post<void>('login', params, { headers }).pipe(
            catchError((error: HttpErrorResponse) => {
              // restituisce un messaggio di errore che verra visualizzato nel subscribe
              return throwError(error.error);
            })
        )
    }

    logout(): Observable<void> {
        return this.http.post<void>('logout', {}).pipe(
            tap({
                next: () => this.isAuthenticatedSubject.next(false)
            })
        );
    }


    get isAuthenticated(): Observable<boolean | null> {
        return this.isAuthenticatedSubject.asObservable();
    }

    get currentUser(): Observable<any | null> {
        return this.currentUserSubject.asObservable();
    }
}