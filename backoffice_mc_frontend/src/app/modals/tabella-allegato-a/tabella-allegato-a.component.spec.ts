import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TabellaAllegatoAComponent } from './tabella-allegato-a.component';

describe('TabellaAllegatoAComponent', () => {
  let component: TabellaAllegatoAComponent;
  let fixture: ComponentFixture<TabellaAllegatoAComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TabellaAllegatoAComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TabellaAllegatoAComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
