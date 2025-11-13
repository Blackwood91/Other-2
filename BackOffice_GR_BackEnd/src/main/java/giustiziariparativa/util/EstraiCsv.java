package giustiziariparativa.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.BufferedReader;
import java.util.List;
import java.util.Scanner;  // Import the Scanner class
import java.nio.charset.StandardCharsets;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList; // import the ArrayList class


import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;


public class EstraiCsv {
	
	public EstraiCsv(){
		super();
	}
		
	
    private static void copyFile(File src, File dest) throws IOException {
        Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    
    
    //METODO PER COPIARE UN FILE IN UN CARTELLA
    public static void copyFileInDirectory(String PathSource) {
        File from = new File("C:/Users/User/eclipse-workspace/dati.csv"); //FILE CHE PROVIENE DALL'UTENTE - PASSARLO TRAMITE JAVASCRIPT
        File to = new File("C:/Users/User/eclipse-workspace/FileCSV.csv"); //
 
        try {
            copyFile(from, to);
            System.out.println("File copied successfully.");
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	public ArrayList<String[]> ReadCsv(MultipartFile file) throws IOException {
	
		if(file != null && Sanitize.sanitizeFileName(file.getOriginalFilename()) != null) {
			
		    File tempFile = File.createTempFile("prefix", "suffix");
		    /*
		          IN CASO SI DECIDERA' DI METTERE UN PERCORSO PERSONALIZZATO
		          // Specificare il percorso personalizzato per il file temporaneo
					 String customTempPath = "/path/to/your/custom/temp/directory";
				     File tempFile = File.createTempFile("prefix", "suffix", new File(customTempPath));
		     */
		    
		    // Per trasferire il file della request nel percorso del file temporaneo
		    file.transferTo(tempFile);

	        Reader fileReader = new InputStreamReader(new FileInputStream(tempFile), StandardCharsets.ISO_8859_1);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
	        // Crea un ArrayList per salvare le righe del file CSV
	        ArrayList<String[]> rows = new ArrayList<>();
	
	        try {
	           // Legge una riga del file CSV alla volta
	            String line;
	            while ((line = bufferedReader.readLine()) != null) {
	                // Divide la riga in un array di stringhe
	                String[] columns = line.split(",");
	
	                // Aggiunge l'array di stringhe all'ArrayList
	                rows.add(columns);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	
	        // Chiude il file CSV
	        bufferedReader.close();
	
	        // Restituisce l'ArrayList con le righe del file CSV
	        return rows;
		}
		else {
            throw new IllegalArgumentException("Il percorso del file è nullo o non valido.");
		}
	}	
}