// package giustiziariparativa.backoffice.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "T_ENTE_FORMATORE")
// public class TipologiaEnteFormatore {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     @Column(name = "ID_TIPOLOGIA_ENTE_FORMATORE")
//     private Long idTipologiaEnteFormatore;

//     @Column(name = "DESCRIZIONE_ENTE")
//     private String descrizioneEnteFormatore;

//     public TipologiaEnteFormatore() {
//     }

//     public TipologiaEnteFormatore(Long idTipologiaEnteFormatore, String descrizioneEnteFormatore) {
//         this.idTipologiaEnteFormatore = idTipologiaEnteFormatore;
//         this.descrizioneEnteFormatore = descrizioneEnteFormatore;
//     }

//     public Long getIdTipologiaEnteFormatore() {
//         return idTipologiaEnteFormatore;
//     }

//     public String getDescrizioneEnteFormatore() {
//         return descrizioneEnteFormatore;
//     }

//     public void setDescrizioneEnteFormatore(String descrizioneEnteFormatore) {
//         this.descrizioneEnteFormatore = descrizioneEnteFormatore;
//     }

//     @Override
//     public String toString() {
//         return "TipologiaEnteFormatore [idTipologiaEnteFormatore=" + idTipologiaEnteFormatore
//                 + ", descrizioneEnteFormatore=" + descrizioneEnteFormatore + "]";
//     }

// }
