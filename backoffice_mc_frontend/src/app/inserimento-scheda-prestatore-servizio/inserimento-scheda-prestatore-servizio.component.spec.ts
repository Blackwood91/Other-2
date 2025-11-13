import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InserimentoSchedaPrestatoreServizioComponent } from './inserimento-scheda-prestatore-servizio.component';

describe('InserimentoSchedaPrestatoreServizioComponent', () => {
  let component: InserimentoSchedaPrestatoreServizioComponent;
  let fixture: ComponentFixture<InserimentoSchedaPrestatoreServizioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InserimentoSchedaPrestatoreServizioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InserimentoSchedaPrestatoreServizioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
