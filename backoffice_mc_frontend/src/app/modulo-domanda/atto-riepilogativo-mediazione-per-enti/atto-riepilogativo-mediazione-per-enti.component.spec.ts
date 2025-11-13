import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttoRiepilogativoMediazionePerEntiComponent } from './atto-riepilogativo-mediazione-per-enti.component';

describe('AttoRiepilogativoMediazionePerEntiComponent', () => {
  let component: AttoRiepilogativoMediazionePerEntiComponent;
  let fixture: ComponentFixture<AttoRiepilogativoMediazionePerEntiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AttoRiepilogativoMediazionePerEntiComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AttoRiepilogativoMediazionePerEntiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
