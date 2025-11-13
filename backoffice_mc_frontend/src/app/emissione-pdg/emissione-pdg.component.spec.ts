import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmissionePdgComponent } from './emissione-pdg.component';

describe('EmissionePdgComponent', () => {
  let component: EmissionePdgComponent;
  let fixture: ComponentFixture<EmissionePdgComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmissionePdgComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EmissionePdgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
