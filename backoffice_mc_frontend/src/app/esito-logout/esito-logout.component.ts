import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-esito-logout',
  templateUrl: './esito-logout.component.html',
  styleUrls: ['./esito-logout.component.css']
})
export class EsitoLogoutComponent implements OnInit {
  esitoLogout: string = "";

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.esitoLogout = this.route.snapshot.queryParamMap.get('esito')!;
  }

}
