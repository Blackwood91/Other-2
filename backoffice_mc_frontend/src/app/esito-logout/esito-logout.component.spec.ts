import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EsitoLogoutComponent } from './esito-logout.component';

describe('EsitoLogoutComponent', () => {
  let component: EsitoLogoutComponent;
  let fixture: ComponentFixture<EsitoLogoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EsitoLogoutComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EsitoLogoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
