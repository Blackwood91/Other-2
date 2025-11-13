import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveSedeComponent } from './save-sede.component';

describe('SaveSedeComponent', () => {
  let component: SaveSedeComponent;
  let fixture: ComponentFixture<SaveSedeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaveSedeComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaveSedeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
