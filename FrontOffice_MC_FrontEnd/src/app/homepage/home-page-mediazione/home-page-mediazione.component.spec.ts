import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomePageMediazioneComponent } from './home-page-mediazione.component';

describe('HomePageMediazioneComponent', () => {
  let component: HomePageMediazioneComponent;
  let fixture: ComponentFixture<HomePageMediazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomePageMediazioneComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomePageMediazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
