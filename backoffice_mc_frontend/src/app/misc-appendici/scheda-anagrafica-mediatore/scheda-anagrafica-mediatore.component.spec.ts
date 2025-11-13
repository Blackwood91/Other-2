import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedaAnagraficaMediatoreComponent } from './scheda-anagrafica-mediatore.component';

describe('SchedaAnagraficaMediatoreComponent', () => {
  let component: SchedaAnagraficaMediatoreComponent;
  let fixture: ComponentFixture<SchedaAnagraficaMediatoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchedaAnagraficaMediatoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchedaAnagraficaMediatoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
