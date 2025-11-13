import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinalizzaRichiestaComponent } from './finalizza-richiesta.component';

describe('FinalizzaRichiestaComponent', () => {
  let component: FinalizzaRichiestaComponent;
  let fixture: ComponentFixture<FinalizzaRichiestaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FinalizzaRichiestaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FinalizzaRichiestaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
