import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediatoriComponent } from './mediatori.component';

describe('ElezioniComponent', () => {
  let component: MediatoriComponent;
  let fixture: ComponentFixture<MediatoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MediatoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MediatoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
