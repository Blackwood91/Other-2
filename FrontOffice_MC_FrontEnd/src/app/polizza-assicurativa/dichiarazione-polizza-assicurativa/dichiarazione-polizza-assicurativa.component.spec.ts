import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DichiarazionePolizzaAssicurativaComponent } from './dichiarazione-polizza-assicurativa.component';

describe('DichiarazionePolizzaAssicurativaComponent', () => {
  let component: DichiarazionePolizzaAssicurativaComponent;
  let fixture: ComponentFixture<DichiarazionePolizzaAssicurativaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DichiarazionePolizzaAssicurativaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DichiarazionePolizzaAssicurativaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
