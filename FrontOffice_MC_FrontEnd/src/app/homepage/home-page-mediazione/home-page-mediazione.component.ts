import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AccessibilitaService } from "src/accessibilita.service";
import { MediazioneService } from "src/app/mediazione.service";
import { SharedService } from "src/app/shared.service";

@Component({
  selector: "app-home-page-mediazione",
  templateUrl: "./home-page-mediazione.component.html",
  styleUrls: ["./home-page-mediazione.component.css"],
})
export class HomePageMediazioneComponent implements OnInit {
  constructor(private router: Router, private route: ActivatedRoute,
              private accessibilita: AccessibilitaService, private sharedService: SharedService,
              private serviceME: MediazioneService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      if(params["error"]) {
        this.sharedService.onMessage('error', params["error"]);
      }
    });
  }
}
