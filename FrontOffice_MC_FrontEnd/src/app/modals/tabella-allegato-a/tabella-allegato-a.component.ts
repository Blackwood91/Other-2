import { Component, OnInit } from "@angular/core";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-tabella-allegato-a",
  templateUrl: "./tabella-allegato-a.component.html",
  styleUrls: ["./tabella-allegato-a.component.css"],
})
export class TabellaAllegatoAComponent implements OnInit {
  scaglioni = [
    { range: "fino a euro 1.000,00", min: "80,00", max: "160,00" },
    { range: "da euro 1.001,00 a euro 5.000,00", min: "160,00", max: "290,00" },
    {
      range: "da euro 5.001,00 a euro 10.000,00",
      min: "290,00",
      max: "440,00",
    },
    {
      range: "da euro 10.001,00 a euro 25.000,00",
      min: "440,00",
      max: "720,00",
    },
    {
      range: "da euro 25.001,00 a euro 50.000,00",
      min: "720,00",
      max: "1200,00",
    },
    {
      range: "da euro 50.001,00 a euro 150.000,00",
      min: "1.200,00",
      max: "1200,00",
    },
    {
      range: "da euro 150.001,00 a euro 250.000,00",
      min: "1.500,00",
      max: "2.500,00",
    },
    {
      range: "da euro 250.001,00 a euro 500.000,00",
      min: "2.500,00",
      max: "3.900,00",
    },
    {
      range: "da euro 500.001,00 a euro 1.500.000,00",
      min: "3.900,00",
      max: "4.600,00",
    },
    {
      range: "da euro 1.500.001,00 a euro 2.500.000,00",
      min: "4.600,00",
      max: "6.500,00",
    },
    {
      range: "da euro 2.500.001,00 a euro 5.000.000,00",
      min: "6.500,00",
      max: "10.000,00",
    },
  ];

  constructor(private sharedService: SharedService) {}

  ngOnInit(): void {}

  openModal() {
    const buttonActiveModal = document.getElementById(
      "activeModalTabellaAllegatoA"
    );
    // Esegui il click sul bottone nascosto "activeModalSaveSede"
    buttonActiveModal != null ? buttonActiveModal.click() : "";
  }
}
