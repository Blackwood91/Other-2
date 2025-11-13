import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RappresentanteLegaleComponent } from './rappresentante-legale.component';

describe('RappresentanteLegaleComponent', () => {
  let component: RappresentanteLegaleComponent;
  let fixture: ComponentFixture<RappresentanteLegaleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RappresentanteLegaleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RappresentanteLegaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
