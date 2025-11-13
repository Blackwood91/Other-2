package com.giustizia.mediazionecivile.utility;

import org.springframework.stereotype.Component;

//@Component
// E' STATO SCELTO UN ENUM PER LA POSSIBILITA DI AGGIUNGERE PIU VALORI IMUTABILI ASSOCIATI A OGNI MODULO
public enum Moduli {
    // DEFINIZIONE DEI MODULI ASSOCIATI ALL'ID
    SEPESE_DI_MEDIAZIONE(73L),
    PLANIMETRIA(69L),
    POLIZZA(59L);


    // VARIABILE CHE RAPPRESENTA L'IDMODULO DELL'ENUM
    public final Long idModulo;

    // Costruttore privato per l'inizializzazione
    private Moduli(Long idModulo) {
        this.idModulo = idModulo;
    }
}