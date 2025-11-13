import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AggiornamentoMediatoreComponent } from './aggiornamento-mediatore.component';

describe('AggiornamentoMediatoreComponent', () => {
  let component: AggiornamentoMediatoreComponent;
  let fixture: ComponentFixture<AggiornamentoMediatoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AggiornamentoMediatoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AggiornamentoMediatoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
