import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaricamentoFileOdmComponent } from './caricamento-file-odm.component';

describe('CaricamentoFileOdmComponent', () => {
  let component: CaricamentoFileOdmComponent;
  let fixture: ComponentFixture<CaricamentoFileOdmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CaricamentoFileOdmComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaricamentoFileOdmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
