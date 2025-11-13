import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateCertificazioneComponent } from './update-certificazione.component';

describe('UpdateCertificazioneComponent', () => {
  let component: UpdateCertificazioneComponent;
  let fixture: ComponentFixture<UpdateCertificazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateCertificazioneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateCertificazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
