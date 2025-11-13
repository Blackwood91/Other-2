import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedaAnagraficaCompOrgAmRiepilogoComponent } from './scheda-anagrafica-comp-org-am-riepilogo.component';

describe('SchedaAnagraficaCompOrgAmRiepilogoComponent', () => {
  let component: SchedaAnagraficaCompOrgAmRiepilogoComponent;
  let fixture: ComponentFixture<SchedaAnagraficaCompOrgAmRiepilogoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchedaAnagraficaCompOrgAmRiepilogoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchedaAnagraficaCompOrgAmRiepilogoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
