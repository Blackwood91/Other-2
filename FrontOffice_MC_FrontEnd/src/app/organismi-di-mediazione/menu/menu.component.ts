import { CommonModule } from "@angular/common";
import { HttpParams } from "@angular/common/http";
import {
  Component,
  EventEmitter,
  NgModule,
  OnInit,
  Output,
} from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-menu",
  templateUrl: "./menu.component.html",
  styleUrls: ["./menu.component.css"],
})
export class MenuComponent implements OnInit {
  @NgModule({
    imports: [CommonModule],
  })
  @Output()
  componentSelezionato = new EventEmitter<string>();
  autonomo: boolean = false;
  idRichiesta: number = 0;
  nomeComponent: string = "";
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
  // Status Macro Menu Moduli
  statusModDoma: string = "";
  statusAppD: string = "";
  statusAppPSO: string = "";
  statusAppA: string = "";
  statusAppB: string = "";
  statusAppC: string = "";
  statusAppPA: string = "";
  // Campi la convalidazione
  muduliConvalidati: any = [];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private serviceME: MediazioneService,
    private sharedService: SharedService
  ) {
    // Per aggionrare la vista del menu selezionata
    this.sharedService.observableSourceChangeViewMenuODM$.subscribe((data) => {
      this.changeComponent(data.nomeComponent);
    });

    // Per aggionrare il menu con i dati aggiornati
    this.sharedService.observableSourceUpdateMenuODM$.subscribe((data) => {
      this.updateMenu();
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.idRichiesta = params["idRichiesta"];

      if (!this.idRichiesta) {
        this.router.navigate(["/paginaInfo"], {
          queryParams: {
            typeError: "request_not_valid",
          },
        });

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
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

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

    this.serviceME
      .getAllModuloConvalidato("status/getAllModuloConvalidato", params)
      .subscribe({
        next: (res: any) => {
          this.muduliConvalidati = res;
          this.loadStatusModuli(0);
        },
        error: (error: any) => {
          this.sharedService.onMessage("error", error);
        },
      });
  }

  loadStatusModuli(remo: number) {
    // MOMENTANEO. IN ATTESA CHE TUTTI I MODULI VENGANO CREATI TO DO
    let idModulo = 0;
    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 1 && item.completato === 1
      )
    ) {
      this.statusDomaIsc = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 1 && item.annullato === 1
      )
    ) {
      this.statusDomaIsc = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 1 && item.validato === 1
      )
    ) {
      this.statusDomaIsc = "v";
    } else {
      this.statusDomaIsc = "";
    }
    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 3 && item.completato === 1
      )
    ) {
      this.statusAttoRieMedE = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 3 && item.annullato === 1
      )
    ) {
      this.statusAttoRieMedE = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 3 && item.validato === 1
      )
    ) {
      this.statusAttoRieMedE = "v";
    } else {
      this.statusAttoRieMedE = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 4 && item.completato === 1
      )
    ) {
      this.statusAttoCostOdm = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 4 && item.annullato === 1
      )
    ) {
      this.statusAttoCostOdm = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 4 && item.validato === 1
      )
    ) {
      this.statusAttoCostOdm = "v";
    } else {
      this.statusAttoCostOdm = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 5 && item.completato === 1
      )
    ) {
      this.statusStatutoOrg = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 5 && item.annullato === 1
      )
    ) {
      this.statusStatutoOrg = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 5 && item.validato === 1
      )
    ) {
      this.statusStatutoOrg = "v";
    } else {
      this.statusStatutoOrg = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 16 && item.completato === 1
      )
    ) {
      this.statusRegProc = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 16 && item.annullato === 1
      )
    ) {
      this.statusRegProc = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 16 && item.validato === 1
      )
    ) {
      this.statusRegProc = "v";
    } else {
      this.statusRegProc = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 73 && item.completato === 1
      )
    ) {
      this.statusSpeseMed = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 73 && item.annullato === 1
      )
    ) {
      this.statusSpeseMed = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 73 && item.validato === 1
      )
    ) {
      this.statusSpeseMed = "v";
    } else {
      this.statusSpeseMed = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 17 && item.completato === 1
      )
    ) {
      this.statusCodEti = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 17 && item.annullato === 1
      )
    ) {
      this.statusCodEti = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 17 && item.validato === 1
      )
    ) {
      this.statusCodEti = "v";
    } else {
      this.statusCodEti = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 23 && item.completato === 1
      )
    ) {
      this.statusBilCertBan = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 23 && item.annullato === 1
      )
    ) {
      this.statusBilCertBan = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 23 && item.validato === 1
      )
    ) {
      this.statusBilCertBan = "v";
    } else {
      this.statusBilCertBan = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 26 && item.completato === 1
      )
    ) {
      this.statusEleMed = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 26 && item.annullato === 1
      )
    ) {
      this.statusEleMed = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 26 && item.validato === 1
      )
    ) {
      this.statusEleMed = "v";
    } else {
      this.statusEleMed = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === idModulo && item.completato === 1
      )
    ) {
      this.statusCollapseTwo = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === idModulo && item.annullato === 1
      )
    ) {
      this.statusCollapseTwo = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === idModulo && item.validato === 1
      )
    ) {
      this.statusCollapseTwo = "v";
    } else {
      this.statusCollapseTwo = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === idModulo && item.completato === 1
      )
    ) {
      this.statusCollapseTh = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === idModulo && item.validato === 1
      )
    ) {
      this.statusCollapseTh = "v";
    } else {
      this.statusCollapseTh = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 58 && item.completato === 1
      )
    ) {
      this.statusDicPolAss = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 58 && item.validato === 1
      )
    ) {
      this.statusDicPolAss = "v";
    } else {
      this.statusDicPolAss = "";
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 59 && item.completato === 1
      )
    ) {
      this.statusPolAss = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 59 && item.validato === 1
      )
    ) {
      this.statusPolAss = "v";
    } else {
      this.statusPolAss = "";
    }

    // SOLO SE NON AUTONOME VERRA' PREDISPOSTA LA LOGICA PER LE ICONE DELLO STATUS
    if (this.autonomo === false) {
      if (
        this.muduliConvalidati.some(
          (item: any) => item.idModulo === 71 && item.completato === 1
        )
      ) {
        this.statusAttoCostOdmSoc = "c";
      } else if (
        this.muduliConvalidati.some(
          (item: any) => item.idModulo === 71 && item.annullato === 1
        )
      ) {
        this.statusAttoCostOdmSoc = "";
      } else if (
        this.muduliConvalidati.some(
          (item: any) => item.idModulo === 71 && item.validato === 1
        )
      ) {
        this.statusAttoCostOdmSoc = "v";
      } else {
        this.statusAttoCostOdmSoc = "";
      }

      if (
        this.muduliConvalidati.some(
          (item: any) => item.idModulo === 72 && item.completato === 1
        )
      ) {
        this.statusStatutoSoc = "c";
      } else if (
        this.muduliConvalidati.some(
          (item: any) => item.idModulo === 72 && item.annullato === 1
        )
      ) {
        this.statusStatutoSoc = "";
      } else if (
        this.muduliConvalidati.some(
          (item: any) => item.idModulo === 72 && item.validato === 1
        )
      ) {
        this.statusStatutoSoc = "v";
      } else {
        this.statusStatutoSoc = "";
      }
    }

    if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 35 && item.completato === 1
      )
    ) {
      this.statusElencoPSO = "c";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 35 && item.annullato === 1
      )
    ) {
      this.statusElencoPSO = "";
    } else if (
      this.muduliConvalidati.some(
        (item: any) => item.idModulo === 35 && item.validato === 1
      )
    ) {
      this.statusElencoPSO = "v";
    } else {
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
    this.loadStatusAppD();
    this.loadStatusAppPA();
    this.loadStatusAppPSO();
  }

  loadStatusModDom() {
    // IN CASO VENGA RISCONTRATO UN SOLO MODULO ANNULLATO O VUOTA ALLORA LA SEZIONE VERRA' CONSIDERATA IN COMPILAZIONE
    if (this.statusDomaIsc === "a" || this.statusDomaIsc === "") {
      this.statusModDoma = "";
    } else if (
      this.statusAttoRieMedE === "a" ||
      this.statusAttoRieMedE === ""
    ) {
      this.statusModDoma = "";
    } else if (
      this.statusAttoCostOdm === "a" ||
      this.statusAttoCostOdm === ""
    ) {
      this.statusModDoma = "";
    } else if (this.statusStatutoOrg === "a" || this.statusStatutoOrg === "") {
      this.statusModDoma = "";
    } else if (this.statusRegProc === "a" || this.statusRegProc === "") {
      this.statusModDoma = "";
    } else if (this.statusSpeseMed === "a" || this.statusSpeseMed === "") {
      this.statusModDoma = "";
    } else if (this.statusCodEti === "a" || this.statusCodEti === "") {
      this.statusModDoma = "";
    } else if (this.statusBilCertBan === "a" || this.statusBilCertBan === "") {
      this.statusModDoma = "";
    } else if (this.statusEleMed === "a" || this.statusEleMed === "") {
      this.statusModDoma = "";
    } else {
      // SE NON ARRIVERA QUI VUOL DIRE CHE TUTTI I MODULI SONO COMPLETATI O VALIDATI
      this.statusModDoma = "c";
    }
  }

  loadStatusAppDEleRapEleCOA() {
    this.loadStatusEleRap();
  }

  loadStatusEleRap() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getStatusElencoRapLRespOrg("status/getStatusElencoRapLRespOrg", params)
      .subscribe({
        next: (res: any) => {
          this.statusEleRap = res.status;
          this.loadStatusEleCompOrgAmm();
        },
      });
  }

  loadStatusEleCompOrgAmm() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getStatusEleCompOrgAmm("status/getStatusElenCompOrgAm", params)
      .subscribe({
        next: (res: any) => {
          this.statusEleCompOrgAmm = res.status;
          this.loadStatusAppD();
        },
      });
  }

  loadStatusAppD() {
    if (this.statusEleRap === "a" || this.statusEleCompOrgAmm === "a")
      [(this.statusAppD = "a")];
    else if (this.statusEleRap === "" || this.statusEleCompOrgAmm === "") {
      this.statusAppD = "";
    } else {
      this.statusAppD = "v";
    }
  }

  loadStatusAppPA() {
    if (this.statusDicPolAss == "a") {
      this.statusAppPA = "";
    } else if (this.statusPolAss == "a") {
      this.statusAppPA = "";
    }

    // VERANNO VERICATE LE CONDIZIONI SOLO SE LO statusAppD ANCORA NON HA UNO STATUS ASSEGNA
    if (this.statusAppPA === "") {
      // SE statusAppD RIMARRA' TRUE FINO A FINE CONTROLLI DEGLI ALTRI MODULI, ALLORA LA SEZIONE VERRA' CONSIDERATA CONVALIDATA
      let modAppPAConvalidato = true;
      if (this.statusDicPolAss !== "c" && this.statusDicPolAss !== "v") {
        modAppPAConvalidato = false;
      } else if (this.statusPolAss !== "c" && this.statusPolAss !== "v") {
        modAppPAConvalidato = false;
      }

      // LO STATUS DI UN INSIEME DI PIU SEZIONI COMPLETATE O VALIDATE SARANNO CONSIDERATE COMPLETATE
      if (modAppPAConvalidato) {
        this.statusAppPA = "c";
      }
    }

    // SE LO statusAppD CONTINUA AD ESSERE VUOTO VUOL DIRE CHE E' ANCORA IN COMPILAZIONE
  }

  loadStatusAppPSO() {
    if (this.statusElencoPSO == "a") {
      this.statusAppPSO = "";
    }

    // VERANNO VERICATE LE CONDIZIONI SOLO SE LO statusAppD ANCORA NON HA UNO STATUS ASSEGNA
    if (this.statusAppPSO === "") {
      // SE statusAppD RIMARRA' TRUE FINO A FINE CONTROLLI DEGLI ALTRI MODULI, ALLORA LA SEZIONE VERRA' CONSIDERATA CONVALIDATA
      let modAppPSOConvalidato = true;
      if (this.statusElencoPSO !== "c" && this.statusElencoPSO !== "v") {
        modAppPSOConvalidato = false;
      }

      // LO STATUS DI UN INSIEME DI PIU SEZIONI COMPLETATE O VALIDATE SARANNO CONSIDERATE COMPLETATE
      if (modAppPSOConvalidato) {
        this.statusAppPSO = "c";
      }
    }
  }

  loadStatusSedi() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME.getStatusSedi("status/getStatusSedi", params).subscribe({
      next: (res: any) => {
        this.statusSedi = res.status;
      },
      error: (error: any) => {
        this.statusSedi = "";
      },
    });
  }

  loadIsAutonomo() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .richiestaNaturaIsAutonomo("richiesta/richiestaNaturaIsAutonomo", params)
      .subscribe({
        next: (res: any) => {
          if (res.autonomo) {
            this.autonomo = true;
          } else {
            this.autonomo = false;
          }
        },
      });
  }

  loadStatusEleMedGen() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getStatusElencoRapLRespOrg("status/getStatusElencoMedGen", params)
      .subscribe({
        next: (res: any) => {
          this.statusEleMedGen = res.status;
          this.loadStatusEleCompOrgAmm();
        },
      });
  }

  loadStatusEleMedInt() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getStatusElencoRapLRespOrg("status/getStatusElencoMedInt", params)
      .subscribe({
        next: (res: any) => {
          this.statusEleMedInt = res.status;
          this.loadStatusEleCompOrgAmm();
        },
      });
  }

  loadStatusEleMedCons() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getStatusElencoRapLRespOrg("status/getStatusElencoMedCons", params)
      .subscribe({
        next: (res: any) => {
          this.statusEleMedCons = res.status;
          this.loadStatusEleCompOrgAmm();
        },
      });
  }

  loadStatusAppendiceA() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getStatusAppendiceA("status/getStatusAppendiceA", params)
      .subscribe({
        next: (res: any) => {
          this.statusAppA = res.status;
        },
      });
  }

  loadStatusAppendiceB() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getStatusAppendiceB("status/getStatusAppendiceB", params)
      .subscribe({
        next: (res: any) => {
          this.statusAppB = res.status;
        },
      });
  }

  loadStatusAppendiceC() {
    const params = new HttpParams().set("idRichiesta", this.idRichiesta);

    this.serviceME
      .getStatusAppendiceC("status/getStatusAppendiceC", params)
      .subscribe({
        next: (res: any) => {
          this.statusAppC = res.status;
        },
      });
  }

  //FUNZIONI COMPONENT
  changeComponent(nomeComponent: string) {
    // Se richiesto open delle drop down automatico prevedere classi con lo stesso nomeComponent che arriva dalla uri
    window.scrollTo({ top: 105, behavior: "smooth" });
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
