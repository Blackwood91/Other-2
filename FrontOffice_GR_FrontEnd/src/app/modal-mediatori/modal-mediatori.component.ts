import {
  Component,
  OnInit,
  Input,
  Output,
  EventEmitter,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'app-modal-mediatori',
  templateUrl: './modal-mediatori.component.html',
  styleUrls: [
    './modal-mediatori.component.css',
    '../../style/bootstrap-italia/css/bootstrap-italia.min.css',
    '../../style/bootstrap-italia/assets/docs.min.css',
  ],
})
export class ModalMediatoriComponent implements OnInit {
  constructor() {}

  @Input()
  mediatore!: any;
  @Output() onMediatore = new EventEmitter<string>();
  selectedFilePDF : any;

  ngOnInit(): void {  }

  ngOnChanges(changes: SimpleChanges) {
    // Caricare campo input file con il blob string
    //this.mediatore?.data_provvedimento = (moment(mediatore?.data_provvedimento)).format('YYYY-MM-DD');
    this.selectedFilePDF = this.convertiStringaBlobAFile(this.mediatore?.provvedimento); 
  }

  // Per formattare stringa in file pdf
  convertiStringaBlobAFile(dati: string): File {
    const byteCharacters = atob(dati);
    const byteNumbers = new Array(byteCharacters.length);
  
    for (let i = 0; i < byteCharacters.length; i++) {
      byteNumbers[i] = byteCharacters.charCodeAt(i);
    }
  
    const byteArray = new Uint8Array(byteNumbers);
    const blobFile = new Blob([byteArray], {type: 'application/pdf'});
  
    return new File([blobFile], 'file');
  }

  openPdfFile() {    
    var file = new Blob([this.selectedFilePDF], {type: 'application/pdf'});
    var fileURL = URL.createObjectURL(file);
    window.open(fileURL, '_blank')
  }
}
