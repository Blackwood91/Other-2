import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedaRappLegaleComponent } from './scheda-rapp-legale.component';

describe('SchedaRappLegaleComponent', () => {
  let component: SchedaRappLegaleComponent;
  let fixture: ComponentFixture<SchedaRappLegaleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchedaRappLegaleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchedaRappLegaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
