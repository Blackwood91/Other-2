import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroElencoSediComponent } from './registro-elenco-sedi.component';

describe('RegistroOdmSediComponent', () => {
  let component: RegistroElencoSediComponent;
  let fixture: ComponentFixture<RegistroElencoSediComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistroElencoSediComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistroElencoSediComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
