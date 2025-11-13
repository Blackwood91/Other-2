import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PolizzaAssicurativaComponent } from './polizza-assicurativa.component';

describe('PolizzaAssicurativaComponent', () => {
  let component: PolizzaAssicurativaComponent;
  let fixture: ComponentFixture<PolizzaAssicurativaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PolizzaAssicurativaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PolizzaAssicurativaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
