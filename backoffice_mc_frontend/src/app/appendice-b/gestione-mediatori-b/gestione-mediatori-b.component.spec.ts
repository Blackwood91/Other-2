import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestioneMediatoriBComponent } from './gestione-mediatori-b.component';

describe('GestioneMediatoriBComponent', () => {
  let component: GestioneMediatoriBComponent;
  let fixture: ComponentFixture<GestioneMediatoriBComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GestioneMediatoriBComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestioneMediatoriBComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
