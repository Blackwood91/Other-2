import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneMediatoriCComponent } from './gestione-mediatori-c.component';

describe('GestioneMediatoriCComponent', () => {
  let component: GestioneMediatoriCComponent;
  let fixture: ComponentFixture<GestioneMediatoriCComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestioneMediatoriCComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestioneMediatoriCComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
