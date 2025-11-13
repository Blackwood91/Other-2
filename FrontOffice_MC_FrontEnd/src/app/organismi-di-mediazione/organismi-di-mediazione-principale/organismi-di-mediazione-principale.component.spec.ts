import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganismiDiMediazionePrincipaleComponent } from './organismi-di-mediazione-principale.component';

describe('OrganismiDiMediazionePrincipaleComponent', () => {
  let component: OrganismiDiMediazionePrincipaleComponent;
  let fixture: ComponentFixture<OrganismiDiMediazionePrincipaleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrganismiDiMediazionePrincipaleComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganismiDiMediazionePrincipaleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
