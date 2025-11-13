import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalMediatoriComponent } from './modal-mediatori.component';

describe('ModalMediatoriComponent', () => {
  let component: ModalMediatoriComponent;
  let fixture: ComponentFixture<ModalMediatoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalMediatoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalMediatoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
