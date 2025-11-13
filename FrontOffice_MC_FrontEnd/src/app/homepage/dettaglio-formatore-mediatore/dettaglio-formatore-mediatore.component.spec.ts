import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DettaglioFormatoreMediatoreComponent } from './dettaglio-formatore-mediatore.component';

describe('DettaglioFormatoreMediatoreComponent', () => {
  let component: DettaglioFormatoreMediatoreComponent;
  let fixture: ComponentFixture<DettaglioFormatoreMediatoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DettaglioFormatoreMediatoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DettaglioFormatoreMediatoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
