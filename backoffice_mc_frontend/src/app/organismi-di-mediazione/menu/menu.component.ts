import { Component, EventEmitter, OnInit, Output, NgModule, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedService } from 'src/app/shared.service';
import { Subscription } from 'rxjs';
import { MediazioneService } from 'src/app/mediazione.service';
import { HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  @NgModule({
    imports: [CommonModule],
  })

  @Output() componentSelezionato = new EventEmitter<string>();
  autonomo: boolean = false;
  idRichiesta: number = 0;
  nomeComponent: string = ""
  isMenuRidotto: boolean = false;
  // Status Moduli
  statusDomaIsc: string = "";
  statusAttoRieMedE: string = "";
  statusSedi: string = "";
  statusAttoCostOdm: string = "";
  statusStatutoOrg: string = "";
  statusRegProc: string = "";
  statusSpeseMed: string = "";
  statusCodEti: string = "";
  statusBilCertBan: string = "";
  statusEleMed: string = "";
  statusCollapseTwo: string = "";
  statusEleRap: string = "";
  statusEleCompOrgAmm: string = "";
  statusCollapseTh: string = "";
  statusDicPolAss: string = "";
  statusPolAss: string = "";
  statusAttoCostOdmSoc: string = "";
  statusStatutoSoc: string = "";
  statusElencoPSO: string = "";
  statusEleMedGen: string = "";
  statusEleMedInt: string = "";
  statusEleMedCons: string = "";
  // Status Sotto Menu Moduli
  statusModDoma: string = "";
  statusAppD: string = "";
  statusAppPSO: string = "";
  statusAppA: string = "";
  statusAppB: string = "";
  statusAppC: string = "";
  statusAppPA: string = "";
  // Campi la convalidazione
  muduliConvalidati: any = [];

  constructor(private route: ActivatedRoute, private router: Router, private serviceME: MediazioneService, private sharedService: SharedService) {
    // Per aggionrare la vista del menu selezionata
    this.sharedService.observableSourceChangeViewMenuODM$.subscribe(data => {
      this.changeComponent(data.nomeComponent);
    });

    // Per aggionrare il menu con i dati aggiornati
    this.sharedService.observableSourceUpdateMenuODM$.subscribe(data => {
      this.updateMenu();
    });

  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idRichiesta = params['idRichiesta'];

      if(!this.idRichiesta) {
        this.router.navigate(['/paginaInfo'], {
          queryParams: {
            typeError: "request_not_valid"
          }
        })
        
        return;
      }

      this.loadAllStatus();
    });
  }

  openItemStates: { [itemId: string]: boolean } = {};

  changeComponentSelect(itemId: string) {
    this.openItemStates[itemId] = !this.openItemStates[itemId];
  }

  updateMenu() {
    this.loadAllStatus();
  }

  loadAllStatus() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    // PER VEDERE LA CONVALIDAZIONE DI PIU ROW ASSEGNATE ALLO STESSO IDMODULO  
    this.loadStatusSedi();
    this.loadIsAutonomo();
    this.loadStatusAppendiceA();
    this.loadStatusAppendiceB();
    this.loadStatusAppendiceC();
    this.loadStatusAppDEleRapEleCOA();
    this.loadStatusEleMedGen();
    this.loadStatusEleMedInt();
    this.loadStatusEleMedCons();

    this.serviceME.getAllModuloConvalidato('status/getAllModuloConvalidato', params)
      .subscribe({
        next: (res: any) => {
          this.muduliConvalidati = res;
          this.loadStatusModuli(0);
        },
        error: (error: any) => {
          this.sharedService.onMessage('error', error);
        }
      })

  }

  loadStatusModuli(remo: number) {
    // MOMENTANEO. IN ATTESA CHE TUTTI I MODULI VENGANO CREATI TO DO
    let idModulo = 0;
    if (this.muduliConvalidati.some((item: any) => item.idModulo === 1 && item.validato === 1)) {
      this.statusDomaIsc = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 1 && item.annullato === 1)) {
      this.statusDomaIsc = "a";
    }
    else {
      this.statusDomaIsc = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 3 && item.validato === 1)) {
      this.statusAttoRieMedE = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 3 && item.annullato === 1)) {
      this.statusAttoRieMedE = "a";
    }
    else {
      this.statusAttoRieMedE = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 4 && item.validato === 1)) {
      this.statusAttoCostOdm = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 4 && item.annullato === 1)) {
      this.statusAttoCostOdm = "a";
    }
    else {
      this.statusAttoCostOdm = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 5 && item.validato === 1)) {
      this.statusStatutoOrg = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 5 && item.annullato === 1)) {
      this.statusStatutoOrg = "a";
    }
    else {
      this.statusStatutoOrg = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 16 && item.validato === 1)) {
      this.statusRegProc = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 16 && item.annullato === 1)) {
      this.statusRegProc = "a";
    }
    else {
      this.statusRegProc = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 73 && item.validato === 1)) {
      this.statusSpeseMed = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 73 && item.annullato === 1)) {
      this.statusSpeseMed = "a";
    }
    else {
      this.statusSpeseMed = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 17 && item.validato === 1)) {
      this.statusCodEti = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 17 && item.annullato === 1)) {
      this.statusCodEti = "a";
    }
    else {
      this.statusCodEti = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 23 && item.validato === 1)) {
      this.statusBilCertBan = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 23 && item.annullato === 1)) {
      this.statusBilCertBan = "a";
    }
    else {
      this.statusBilCertBan = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 26 && item.validato === 1)) {
      this.statusEleMed = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 26 && item.annullato === 1)) {      
      this.statusEleMed = "a";
    }
    else {
      this.statusEleMed = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === idModulo && item.validato === 1)) {
      this.statusCollapseTwo = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === idModulo && item.annullato === 1)) {
      this.statusCollapseTwo = "a";
    }
    else {
      this.statusCollapseTwo = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === idModulo && item.validato === 1)) {
      this.statusCollapseTh = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === idModulo && item.annullato === 1)) {
      this.statusCollapseTh = "a";
    }
    else {
      this.statusCollapseTh = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 58 && item.validato === 1)) {
      this.statusDicPolAss = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 58 && item.annullato === 1)) {
      this.statusDicPolAss = "a";
    }
    else {
      this.statusDicPolAss = "";
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 59 && item.validato === 1)) {
      this.statusPolAss = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 59 && item.annullato === 1)) {
      this.statusPolAss = "a";
    }
    else {
      this.statusPolAss = "";
    }

    // SOLO SE NON AUTONOME VERRA' PREDISPOSTA LA LOGICA PER LE ICONE DELLO STATUS
    if (this.autonomo === false) {
      if (this.muduliConvalidati.some((item: any) => item.idModulo === 71 && item.validato === 1)) {
        this.statusAttoCostOdmSoc = "v";
      }
      else if (this.muduliConvalidati.some((item: any) => item.idModulo === 71 && item.annullato === 1)) {
        this.statusAttoCostOdmSoc = "a";
      }
      else {
        this.statusAttoCostOdmSoc = "";
      }

      if (this.muduliConvalidati.some((item: any) => item.idModulo === 72 && item.validato === 1)) {
        this.statusStatutoSoc = "v";
      }
      else if (this.muduliConvalidati.some((item: any) => item.idModulo === 72 && item.annullato === 1)) {
        this.statusStatutoSoc = "a";
      }
      else {
        this.statusStatutoSoc = "";
      }
    }

    if (this.muduliConvalidati.some((item: any) => item.idModulo === 35 && item.validato === 1)) {
      this.statusElencoPSO = "v";
    }
    else if (this.muduliConvalidati.some((item: any) => item.idModulo === 35 && item.annullato === 1)) {
      this.statusElencoPSO = "a";
    }
    else {
      this.statusElencoPSO = "";
    }

    // CARICAMENTO STATUS DEI CONTENITORI DEI MODULI
    this.loadStatusContenitoriMod();
  }

  loadStatusContenitoriMod() {
    // RESET DEI VALORI DI QUESTI DETERMINATI CONTENITORI
    this.statusModDoma = "";
    this.statusAppPA = "";
    this.statusAppPSO = "";

    this.loadStatusModDom();
    this.loadStatusAppPA();
    this.loadStatusAppPSO();
  }

  loadStatusModDom() {
    // IN CASO VENGA RISCONTRATO UN SOLO MODULO ANNULLATO ALLORA LA SEZIONE VERRA' CONSIDERATA ANNULLATA
    if (this.statusDomaIsc === "a") {
      this.statusModDoma = "a";
    }
    else if (this.statusAttoRieMedE === "a") {
      this.statusModDoma = "a";
    }
    else if (this.statusAttoCostOdm === "a") {
      this.statusModDoma = "a";
    }
    else if (this.statusStatutoOrg === "a") {
      this.statusModDoma = "a";
    }
    else if (this.statusRegProc === "a") {
      this.statusModDoma = "a";
    }
    else if (this.statusSpeseMed === "a") {
      this.statusModDoma = "a";
    }
    else if (this.statusCodEti === "a") {
      this.statusModDoma = "a";
    }
    else if (this.statusBilCertBan === "a") {
      this.statusModDoma = "a";
    }
    else if (this.statusEleMed === "a") {
      this.statusModDoma = "a";
    }

    // VERANNO VERICATE LE CONDIZIONI SOLO SE LO statusModDoma ANCORA NON HA UNO STATUS ASSEGNA
    if (this.statusModDoma === "") {
      // SE modDomaConvalidato RIMARRA' TRUE FINO A FINE CONTROLLI DEGLI ALTRI MODULI, ALLORA LA SEZIONE VERRA' CONSIDERATA CONVALIDATA
      let modDomaConvalidato = true;
      if (this.statusDomaIsc !== "v") {
        modDomaConvalidato = false;
      }
      else if (this.statusAttoRieMedE !== "v") {
        modDomaConvalidato = false;
      }
      else if (this.statusAttoCostOdm !== "v") {
        modDomaConvalidato = false;
      }
      else if (this.statusStatutoOrg !== "v") {
        modDomaConvalidato = false;
      }
      else if (this.statusRegProc !== "v") {
        modDomaConvalidato = false;
      }
      else if (this.statusSpeseMed !== "v") {
        modDomaConvalidato = false;
      }
      else if (this.statusCodEti !== "v") {
        modDomaConvalidato = false;
      }
      else if (this.statusBilCertBan !== "v") {
        modDomaConvalidato = false;
      }
      else if (this.statusEleMed !== "v") {
        modDomaConvalidato = false;
      }

      if (modDomaConvalidato) {
        this.statusModDoma = "v";
      }
    }
  }

  loadStatusAppDEleRapEleCOA() {
    this.loadStatusEleRap();
  }

  loadStatusEleRap() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getStatusElencoRapLRespOrg("status/getStatusElencoRapLRespOrg", params)
      .subscribe({
        next: (res: any) => {
            this.statusEleRap = res.status;
            this.loadStatusEleCompOrgAmm();
        }
      })
  }

  loadStatusEleCompOrgAmm() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getStatusEleCompOrgAmm("status/getStatusElenCompOrgAm", params)
      .subscribe({
        next: (res: any) => {
            this.statusEleCompOrgAmm = res.status;
            this.loadStatusAppD();
        }
      })
  }

  loadStatusEleMedGen() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getStatusElencoRapLRespOrg("status/getStatusElencoMedGen", params)
      .subscribe({
        next: (res: any) => {
            this.statusEleMedGen = res.status;
            this.loadStatusEleCompOrgAmm();
        }
      })
  }

  loadStatusEleMedInt() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getStatusElencoRapLRespOrg("status/getStatusElencoMedInt", params)
      .subscribe({
        next: (res: any) => {
            this.statusEleMedInt = res.status;
            this.loadStatusEleCompOrgAmm();
        }
      })
  }

  loadStatusEleMedCons() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getStatusElencoRapLRespOrg("status/getStatusElencoMedCons", params)
      .subscribe({
        next: (res: any) => {
            this.statusEleMedCons = res.status;
            this.loadStatusEleCompOrgAmm();
        }
      })
  }

  loadStatusAppD() {
    if(this.statusEleRap === 'a' || this.statusEleCompOrgAmm === 'a') [
      this.statusAppD = "a"
    ]
    else if(this.statusEleRap === '' || this.statusEleCompOrgAmm === '') {
      this.statusAppD = "";
    }
    else {
      this.statusAppD = "v";
    }
  }

  loadStatusAppPA() {
    if (this.statusDicPolAss == "a") {
      this.statusAppPA = "a";
    }
    else if (this.statusPolAss == "a") {
      this.statusAppPA = "a";
    }

    // VERANNO VERICATE LE CONDIZIONI SOLO SE LO statusAppD ANCORA NON HA UNO STATUS ASSEGNA
    if (this.statusAppPA === "") {
      // SE statusAppD RIMARRA' TRUE FINO A FINE CONTROLLI DEGLI ALTRI MODULI, ALLORA LA SEZIONE VERRA' CONSIDERATA CONVALIDATA
      let modAppPAConvalidato = true;
      if (this.statusDicPolAss !== "v") {
        modAppPAConvalidato = false;
      }
      else if (this.statusPolAss !== "v") {
        modAppPAConvalidato = false;
      }

      if (modAppPAConvalidato) {
        this.statusAppPA = "v";
      }
    }

    // SE LO statusAppD CONTINUA AD ESSERE VUOTO VUOL DIRE CHE E' ANCORA IN COMPILAZIONE
  }

  loadStatusAppPSO() {
    if (this.statusElencoPSO == "a") {
      this.statusAppPSO = "a";
    }

    // VERANNO VERICATE LE CONDIZIONI SOLO SE LO statusAppD ANCORA NON HA UNO STATUS ASSEGNA
    if (this.statusAppPSO === "") {
      // SE statusAppD RIMARRA' TRUE FINO A FINE CONTROLLI DEGLI ALTRI MODULI, ALLORA LA SEZIONE VERRA' CONSIDERATA CONVALIDATA
      let modAppPSOConvalidato = true;
      if (this.statusElencoPSO !== "v") {
        modAppPSOConvalidato = false;
      }
      else if (this.statusElencoPSO !== "v") {
        modAppPSOConvalidato = false;
      }

      if (modAppPSOConvalidato) {
        this.statusAppPSO = "v";
      }
    }
  }

  loadStatusSedi() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.getStatusSedi('status/getStatusSedi', params)
      .subscribe({
        next: (res: any) => {
            this.statusSedi = res.status;
        },
        error: (error: any) => {
          this.statusSedi = "";
        }
      })
  }

  loadIsAutonomo() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta);

    this.serviceME.richiestaNaturaIsAutonomo('richiesta/richiestaNaturaIsAutonomo', params)
      .subscribe({
        next: (res: any) => {
          if (res.autonomo) {
            this.autonomo = true;
          }
          else {
            this.autonomo = false;
          }
        }
      })
  }

  loadStatusAppendiceA() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)

    this.serviceME.getStatusAppendiceA('status/getStatusAppendiceA', params)
      .subscribe({
        next: (res: any) => {
            this.statusAppA = res.status;
        }
      })

  }

  loadStatusAppendiceB() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)

    this.serviceME.getStatusAppendiceB('status/getStatusAppendiceB', params)
      .subscribe({
        next: (res: any) => {
          this.statusAppB = res.status;
        }
      })
  }

  loadStatusAppendiceC() {
    const params = new HttpParams()
      .set('idRichiesta', this.idRichiesta)

    this.serviceME.getStatusAppendiceC('status/getStatusAppendiceC', params)
      .subscribe({
        next: (res: any) => {
          this.statusAppC = res.status;
        }
      })
  }

  //FUNZIONI COMPONENT
  changeComponent(nomeComponent: string) {
    // Se richiesto open delle drop down automatico prevedere classi con lo stesso nomeComponent che arriva dalla uri
    window.scrollTo({ top: 105, behavior: 'smooth' });
    this.nomeComponent = nomeComponent;
    this.componentSelezionato.emit(nomeComponent);
  }

  showMenu(event?: any) {
    if (event) {
      this.isMenuRidotto = !this.isMenuRidotto;
    }

    let divMenu = document.getElementById("div-menu-odm");
    let divSidebar = document.getElementById("sidebar-menu-odm");

    if (this.isMenuRidotto) {
      if (divMenu && divSidebar) {
        divMenu.style.display = "block";
        divSidebar.style.display = "none";
      }
    } else {
      if (divMenu && divSidebar) {
        divMenu.style.display = "flex";
        divSidebar.style.display = "block";
      }
    }
  }

  //FINE FUNZIONI COMPONENT------------------


}