import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedaAnagraficaCompOrgAmComponent } from './scheda-anagrafica-comp-org-am.component';

describe('SchedaAnagraficaCompOrgAmComponent', () => {
  let component: SchedaAnagraficaCompOrgAmComponent;
  let fixture: ComponentFixture<SchedaAnagraficaCompOrgAmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchedaAnagraficaCompOrgAmComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchedaAnagraficaCompOrgAmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
