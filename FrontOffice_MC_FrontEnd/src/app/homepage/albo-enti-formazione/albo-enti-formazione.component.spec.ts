import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AlboEntiFormazioneComponent } from './albo-enti-formazione.component';

describe('AlboEntiFormazioneComponent', () => {
  let component: AlboEntiFormazioneComponent;
  let fixture: ComponentFixture<AlboEntiFormazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AlboEntiFormazioneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AlboEntiFormazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
