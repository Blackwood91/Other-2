import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateMediatoreComponent } from './update-mediatore.component';

describe('UpdateMediatoreComponent', () => {
  let component: UpdateMediatoreComponent;
  let fixture: ComponentFixture<UpdateMediatoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateMediatoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateMediatoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
