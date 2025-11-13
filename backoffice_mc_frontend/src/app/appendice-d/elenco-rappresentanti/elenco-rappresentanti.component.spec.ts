import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ElencoRappresentantiComponent } from './elenco-rappresentanti.component';

describe('ElencoRappresentantiComponent', () => {
  let component: ElencoRappresentantiComponent;
  let fixture: ComponentFixture<ElencoRappresentantiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ElencoRappresentantiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ElencoRappresentantiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
