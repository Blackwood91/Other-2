import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UscitaComponent } from './uscita.component';

describe('UscitaComponent', () => {
  let component: UscitaComponent;
  let fixture: ComponentFixture<UscitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UscitaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UscitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
