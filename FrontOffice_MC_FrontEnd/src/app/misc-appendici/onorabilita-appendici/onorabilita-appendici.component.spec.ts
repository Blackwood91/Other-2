import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OnorabilitaAppendiciComponent } from './onorabilita-appendici.component';

describe('OnorabilitaAppendiciComponent', () => {
  let component: OnorabilitaAppendiciComponent;
  let fixture: ComponentFixture<OnorabilitaAppendiciComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OnorabilitaAppendiciComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OnorabilitaAppendiciComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
