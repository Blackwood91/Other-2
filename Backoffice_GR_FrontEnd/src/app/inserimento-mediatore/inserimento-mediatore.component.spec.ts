import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InserimentoMediatoreComponent } from './inserimento-mediatore.component';

describe('InserimentoElezioneComponent', () => {
  let component: InserimentoMediatoreComponent;
  let fixture: ComponentFixture<InserimentoMediatoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InserimentoMediatoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InserimentoMediatoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
