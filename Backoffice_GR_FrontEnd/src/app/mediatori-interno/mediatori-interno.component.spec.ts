import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MediatoriInternoComponent } from './mediatori-interno.component';

describe('MediatoriInternoComponent', () => {
  let component: MediatoriInternoComponent;
  let fixture: ComponentFixture<MediatoriInternoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MediatoriInternoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MediatoriInternoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
