package com.giustizia.mediazionecivile.utility;

import org.springframework.stereotype.Component;

@Component
public class DtoUtility {

    public static <T> T castWithType(Class<T> type, Object value) {
        if (value == null) {
            return getDefaultValue(type);
        }

        try {
            return type.cast(value);
        } catch (ClassCastException e) {
            // Se il cast non è possibile, ritorna il valore predefinito
            return getDefaultValue(type);
        }
    }

    private static <T> T getDefaultValue(Class<T> type) {
        if (type.equals(Integer.class) || type.equals(int.class)) {
            return (T) Integer.valueOf(0);
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            return (T) Double.valueOf(0.0);
        } else if (type.equals(String.class)) {
            return (T) "";
        } // Aggiungi altri tipi secondo necessità

        // Se il tipo non corrisponde, ritorna null
        return null;
    }
	
}
