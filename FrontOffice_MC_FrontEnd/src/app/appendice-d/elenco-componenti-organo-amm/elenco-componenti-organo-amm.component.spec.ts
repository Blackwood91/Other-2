import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElencoComponentiOrganoAmmComponent } from './elenco-componenti-organo-amm.component';

describe('ElencoComponentiOrganoAmmComponent', () => {
  let component: ElencoComponentiOrganoAmmComponent;
  let fixture: ComponentFixture<ElencoComponentiOrganoAmmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ElencoComponentiOrganoAmmComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElencoComponentiOrganoAmmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
