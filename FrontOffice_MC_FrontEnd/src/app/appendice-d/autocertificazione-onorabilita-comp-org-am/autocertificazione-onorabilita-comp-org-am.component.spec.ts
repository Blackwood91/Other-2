import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AutocertificazioneOnorabilitaCompOrgAmComponent } from './autocertificazione-onorabilita-comp-org-am.component';

describe('AutocertificazioneOnorabilitaCompOrgAmComponent', () => {
  let component: AutocertificazioneOnorabilitaCompOrgAmComponent;
  let fixture: ComponentFixture<AutocertificazioneOnorabilitaCompOrgAmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AutocertificazioneOnorabilitaCompOrgAmComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AutocertificazioneOnorabilitaCompOrgAmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
