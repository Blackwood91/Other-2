import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InserimentoSedeComponent } from './inserimento-sede.component';

describe('InserimentoSedeComponent', () => {
  let component: InserimentoSedeComponent;
  let fixture: ComponentFixture<InserimentoSedeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InserimentoSedeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InserimentoSedeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
