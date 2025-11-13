import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RichiestaIntegrazioneComponent } from './richiesta-integrazione.component';

describe('RichiestaIntegrazioneComponent', () => {
  let component: RichiestaIntegrazioneComponent;
  let fixture: ComponentFixture<RichiestaIntegrazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RichiestaIntegrazioneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RichiestaIntegrazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
