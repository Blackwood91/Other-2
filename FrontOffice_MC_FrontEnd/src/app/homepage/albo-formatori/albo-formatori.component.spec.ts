import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlboFormatoriComponent } from './albo-formatori.component';

describe('AlboFormatoriComponent', () => {
  let component: AlboFormatoriComponent;
  let fixture: ComponentFixture<AlboFormatoriComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlboFormatoriComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlboFormatoriComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
