import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlboMediatoriComponent } from './albo-mediatori.component';

describe('AlboMediatoriComponent', () => {
  let component: AlboMediatoriComponent;
  let fixture: ComponentFixture<AlboMediatoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlboMediatoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlboMediatoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
