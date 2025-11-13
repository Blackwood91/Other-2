import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneMediatoriAComponent } from './gestione-mediatori-a.component';

describe('GestioneMediatoriAComponent', () => {
  let component: GestioneMediatoriAComponent;
  let fixture: ComponentFixture<GestioneMediatoriAComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestioneMediatoriAComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestioneMediatoriAComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
