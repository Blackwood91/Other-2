import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-top-page',
  templateUrl: './top-page.component.html',
  styleUrls: ['../../../style/bootstrap-italia/css/bootstrap-italia.min.css', '../../../style/bootstrap-italia/assets/docs.min.css', './top-page.component.css']
})
export class TopPageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  scrollTop(){
    window.scrollTo({ top: 0, behavior: 'smooth'});
  }

}
