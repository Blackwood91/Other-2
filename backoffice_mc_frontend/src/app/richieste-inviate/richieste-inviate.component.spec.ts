import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RichiesteInviateComponent } from './richieste-inviate.component';

describe('RichiesteInviateComponent', () => {
  let component: RichiesteInviateComponent;
  let fixture: ComponentFixture<RichiesteInviateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RichiesteInviateComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RichiesteInviateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
