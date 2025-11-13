import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SocietaUtenteComponent } from './societa-utente.component';

describe('SocietaUtenteComponent', () => {
  let component: SocietaUtenteComponent;
  let fixture: ComponentFixture<SocietaUtenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SocietaUtenteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SocietaUtenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
