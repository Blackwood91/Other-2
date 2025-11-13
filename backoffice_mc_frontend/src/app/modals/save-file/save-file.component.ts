import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-save-file',
  templateUrl: './save-file.component.html',
  styleUrls: ['./save-file.component.css']
})
export class SaveFileComponent implements OnInit {
  @Input()
  component!: string;
  @Output() sendFileComponent = new EventEmitter<void>();
  idRiferimento: number = 0;
  selectedFilePDF: any = null;
  titolo: string = "";
  id: number = 0;

  constructor() { }

  ngOnInit(): void {    
  }

  openModal(titolo: string, id: number) {
      this.titolo = titolo;
      this.id = id;
      const buttonActiveModal = document.getElementById("activeModalSaveFile");
      buttonActiveModal!.click();
  }

  onFilePdf(event: any) {
    this.selectedFilePDF = event.target.files[0];
  }

  sendFile() {
    const params: any = {selectedFilePDF: this.selectedFilePDF, id: this.id};
    this.sendFileComponent.emit(params);
    const buttonActiveModal = document.getElementById("closeModal");
    buttonActiveModal!.click();
  }

}
