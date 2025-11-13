import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CaricamentoFileAppendiciComponent } from './caricamento-file-appendici.component';

describe('CaricamentoFileAppendiciComponent', () => {
  let component: CaricamentoFileAppendiciComponent;
  let fixture: ComponentFixture<CaricamentoFileAppendiciComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CaricamentoFileAppendiciComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CaricamentoFileAppendiciComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
