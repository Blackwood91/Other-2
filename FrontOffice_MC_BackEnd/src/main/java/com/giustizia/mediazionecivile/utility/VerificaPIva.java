// package com.giustizia.mediazionecivile.util.metodi;

// //import java.util.regex.*;

// public class VerificaPIva {

//     public Boolean verificaPIva(String partitaIva) {

//         partitaIva = partitaIva.trim();
//         if (partitaIva.length() == 0)
//             return false;

//         else if (partitaIva.length() != 11) {
//             System.out.println("Lunghezza non valida");
//             return false;
//         }
//         if (!partitaIva.matches("^[0-9]{11}$")) {
//             System.out.println("Partita Iva non valida");
//             return false;
//         }
//         int s = 0;
//         for (int i = 0; i < 11; i++) {
//             int n = partitaIva.charAt(i) - '0';
//             if ((i & 1) == 1) {
//                 n *= 2;
//                 if (n > 9)
//                     n -= 9;
//             }
//             s += n;
//         }
//         if (s % 10 != 0)
//             return false;

//         // controllo partita Iva valida, non 11 numeri a caso

//         final int[] listaPari = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
//         int num = 0;
//         int num2 = 0;

//         while (true) {
//             String text = partitaIva.substring(num2, num2 + 1);
//             int num3 = "0123456789".indexOf(text);

//             if (num3 == -1) {
//                 break;
//             }

//             int num4 = Integer.parseInt(text);

//             if (num2 % 2 == 1) {
//                 num4 = listaPari[num3];
//             }

//             num += num4;
//             num2++;

//             if (num2 > 10) {
//                 System.out.println("Partita Iva corretta");
//                 return num % 10 == 0 && num != 0;
//             }
//         }
//         System.out.println("Partita Iva non valida");
//         return false;

//     }
// }

package com.giustizia.mediazionecivile.utility;

public class VerificaPIva {

    public boolean verificaPIva(String partitaIva) {
        partitaIva = partitaIva.trim();

        // Verifica la lunghezza
        if (partitaIva.length() != 11) {
            System.out.println("Lunghezza non valida");
            return false;
        }

        // Verifica i caratteri validi
        if (!partitaIva.matches("^[0-9]{11}$")) {
            System.out.println("Partita Iva non valida");
            return false;
        }

        // Calcola il controllo
        int s = 0;
        for (int i = 0; i < 11; i++) {
            int n = partitaIva.charAt(i) - '0';
            if ((i & 1) == 1) {
                n *= 2;
                if (n > 9)
                    n -= 9;
            }
            s += n;
        }

        // Verifica il controllo
        if (s % 10 != 0) {
            System.out.println("Partita Iva non valida");
            return false;
        }

        // Controllo sulla validità della Partita IVA
        final int[] listaPari = { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };
        int num = 0;
        int num2 = 0;

        while (num2 < 11) {
            char c = partitaIva.charAt(num2);
            if (!Character.isDigit(c)) {
                break;
            }

            int num3 = Character.getNumericValue(c);
            int num4 = (num2 % 2 == 1) ? listaPari[num3] : num3;

            num += num4;
            num2++;

            if (num2 > 10) {
                System.out.println("Partita Iva corretta");
                return num % 10 == 0 && num != 0;
            }
        }

        System.out.println("Partita Iva non ammesa");
        return false;
    }
}

