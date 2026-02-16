package it.corsojavadb.app.util;

import java.util.Scanner;

/**
 * Utility per operazioni di input da console e funzioni helper comuni.
 * Metodi statici progettati per essere riutilizzati dall'applicazione.
 */
public class ConsoleUtils {

    /**
     * Legge un numero intero dallo Scanner passato, gestendo l'errore di formato.
     */
    public static int leggiIntero(Scanner scanner){
        while(true){
            try{
                return Integer.parseInt(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Input non valido. Inserisci un numero intero: ");
            }
        }
    }

    /**
     * Legge un numero decimale dallo Scanner passato, gestendo l'errore di formato.
     */
    public static double leggiDouble(Scanner scanner){
        while(true){
            try{
                return Double.parseDouble(scanner.nextLine());
            }catch(NumberFormatException e){
                System.out.println("Input non valido. Inserisci un numero decimale: ");
            }
        }
    }

    public static boolean consoleConfirm(String value){
        return value.equalsIgnoreCase("S") || value.equalsIgnoreCase("SI");
    }

}
