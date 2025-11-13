import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthenticationService } from "src/app/authentication.service";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-registrazione-utente",
  templateUrl: "./registrazione-utente.component.html",
  styleUrls: ["./registrazione-utente.component.css"],
})
export class RegistrazioneUtenteComponent implements OnInit {
  nome: string = "";
  cognome: string = "";
  codiceFiscale: string = "";
  pIva: string = "";
  ruolo: string = "Legale rappresentante";
  email: string = "";
  pec: string = "";
  tipoRichiesta: string = "";
  ragioneSociale: string = "";

  constructor(
    private serviceME: MediazioneService,
    private route: ActivatedRoute,
    private router: Router,
    private autentication: AuthenticationService,
    private sharedService: SharedService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.nome = params["nome"];
      this.cognome = params["cognome"];
      this.codiceFiscale = params["codiceFiscale"];
    });
  }

  insertRichiestaRegistrazioneUser() {
    const formData: {
      nome: string;
      cognome: string;
      codiceFiscale: string;
      pIva: string;
      ruolo: string;
      email: string;
      pec: string;
      tipoRichiesta: string;
      ragioneSociale: string;
    } = {
      nome: this.nome,
      cognome: this.cognome,
      codiceFiscale: this.codiceFiscale,
      pIva: this.pIva,
      ruolo: this.ruolo,
      email: this.email,
      pec: this.pec,
      tipoRichiesta: this.tipoRichiesta,
      ragioneSociale: this.ragioneSociale,
    };

    this.autentication.registrazioneUtente(formData).subscribe({
      next: (res: any) => {
        this.sharedService.onMessage(
          "success",
          "La registrazione è avvenuta con successo!"
        );
        this.router.navigate(["/homePage"]);
      },
      error: (error: any) => {
        this.sharedService.onMessage(
          "error",
          "La registrazione non è andata a buon fine."
        );
      },
    });
  }

  backHome() {}
}
