package com.giustizia.mediazionecivile.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TypeUtility {
	
    public static <T> T castWithType(Class<T> type, Object value) {
        if (value == null) {
            return getDefaultValue(type);
        }

        // Prova a castare il valore in string per poi castarlo nella tipologia desiderata
        try {
            return getCastValue(type, String.valueOf(value));
        } catch (ClassCastException e) {
            // Se il cast non è possibile, ritorna il valore predefinito
            return getDefaultValue(type);
        }
    }
    
    public static Date castWithDate(String parse, String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(parse);
        
        if (value == null || value.isEmpty()) {
            return null;
        }
        try {
            return dateFormat.parse(value.trim());
        } catch (Exception e) {
            // Se il cast non è possibile, ritorna il valore predefinito
            return null;
        }
    }

    private static <T> T getDefaultValue(Class<T> type) {
        if (type.equals(Integer.class) || type.equals(int.class)) {
            return (T) Integer.valueOf(0);
        } 
        else if (type.equals(Double.class) || type.equals(double.class)) {
            return (T) Double.valueOf(0.0);
        
	    } 
        else if (type.equals(Long.class) || type.equals(Long.class)) {
	        return (T) Long.valueOf(0);     
	    }
        else if (type.equals(String.class)) {
            return (T) "";
        } // Aggiungi altri tipi secondo necessità

        // Se il tipo non corrisponde, ritorna null
        return null;
    }
    
    private static <T> T getCastValue(Class<T> type, String value) {
        if (type.equals(Integer.class) || type.equals(int.class)) {
            return (T) Integer.valueOf(value);
        } 
        else if (type.equals(Double.class) || type.equals(double.class)) {
            return (T) Double.valueOf(value);
        
	    } 
        else if (type.equals(Long.class) || type.equals(Long.class)) {
	        return (T) Long.valueOf(value);     
	    }
        else if (type.equals(String.class)) {
            return (T) "";
        } // Aggiungi altri tipi secondo necessità

        // Se il tipo non corrisponde, ritorna null
        return null;
    }
	
}
