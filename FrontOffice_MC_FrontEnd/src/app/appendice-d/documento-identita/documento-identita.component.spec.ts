import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DocumentoIdentitaComponent } from './documento-identita.component';

describe('DocumentoIdentitaComponent', () => {
  let component: DocumentoIdentitaComponent;
  let fixture: ComponentFixture<DocumentoIdentitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DocumentoIdentitaComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DocumentoIdentitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
