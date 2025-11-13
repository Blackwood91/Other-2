import { Component, OnInit } from "@angular/core";

@Component({
  selector: "app-load-page",
  templateUrl: "./load-page.component.html",
  styleUrls: ["./load-page.component.css"],
})
export class LoadPageComponent implements OnInit {
  constructor() {}

  classLoad: string = "loader-container";

  ngOnInit(): void {}

  onLoad() {
    this.classLoad = "loader-container activeLoad";
  }

  onLoadService() {
    this.classLoad = "loader-container activeLoad service";
  }

  offLoad() {
    this.classLoad = "loader-container";
  }
}
