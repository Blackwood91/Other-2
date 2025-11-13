import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedaPrestatoreServizioComponent } from './scheda-prestatore-servizio.component';

describe('SchedaPrestatoreServizioComponent', () => {
  let component: SchedaPrestatoreServizioComponent;
  let fixture: ComponentFixture<SchedaPrestatoreServizioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchedaPrestatoreServizioComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchedaPrestatoreServizioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
