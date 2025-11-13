import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioSedeComponent } from './dettaglio-sede.component';

describe('DettaglioSedeComponent', () => {
  let component: DettaglioSedeComponent;
  let fixture: ComponentFixture<DettaglioSedeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DettaglioSedeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DettaglioSedeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
