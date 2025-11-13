import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiornamentoSocietaComponent } from './aggiornamento-societa.component';

describe('AggiornamentoSocietaComponent', () => {
  let component: AggiornamentoSocietaComponent;
  let fixture: ComponentFixture<AggiornamentoSocietaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AggiornamentoSocietaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AggiornamentoSocietaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
