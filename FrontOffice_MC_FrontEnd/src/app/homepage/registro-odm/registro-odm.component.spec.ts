import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegistroOdmComponent } from './registro-odm.component';

describe('RegistroOdmComponent', () => {
  let component: RegistroOdmComponent;
  let fixture: ComponentFixture<RegistroOdmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegistroOdmComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegistroOdmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
