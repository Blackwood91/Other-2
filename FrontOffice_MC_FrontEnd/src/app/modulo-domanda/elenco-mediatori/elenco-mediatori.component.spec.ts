import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElencoMediatoriComponent } from './elenco-mediatori.component';

describe('ElencoMediatoriComponent', () => {
  let component: ElencoMediatoriComponent;
  let fixture: ComponentFixture<ElencoMediatoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ElencoMediatoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElencoMediatoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
