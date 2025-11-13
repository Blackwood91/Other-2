import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroElencoPdgComponent } from './registro-elenco-pdg.component';

describe('RegistroElencoPdgComponent', () => {
  let component: RegistroElencoPdgComponent;
  let fixture: ComponentFixture<RegistroElencoPdgComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistroElencoPdgComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistroElencoPdgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
