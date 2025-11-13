import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LetturaPdgComponent } from './lettura-pdg.component';

describe('LetturaPdgComponent', () => {
  let component: LetturaPdgComponent;
  let fixture: ComponentFixture<LetturaPdgComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LetturaPdgComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LetturaPdgComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
