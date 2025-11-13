import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SchedaCompOrgAmAndCompSocComponent } from './scheda-comp-org-am-and-comp-soc.component';

describe('SchedaCompOrgAmAndCompSocComponent', () => {
  let component: SchedaCompOrgAmAndCompSocComponent;
  let fixture: ComponentFixture<SchedaCompOrgAmAndCompSocComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SchedaCompOrgAmAndCompSocComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SchedaCompOrgAmAndCompSocComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
