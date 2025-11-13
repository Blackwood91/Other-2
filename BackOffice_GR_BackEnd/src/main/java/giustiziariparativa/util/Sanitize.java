package giustiziariparativa.util;

public class Sanitize {

    public static String sanitizeFileName(String fileName) {
        // Logica di sicurezza per il nome del file
        if (fileName == null || fileName.contains("..")) {
        	return null;
        }
        else {
        	return fileName.replaceAll("[^a-zA-Z0-9.-]", "_");
        }
    }
	
}