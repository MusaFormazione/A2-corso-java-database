package it.corsojavadb.app;

import it.corsojavadb.app.dao.PizzaDao;
import it.corsojavadb.app.model.Pizza;
import it.corsojavadb.app.db.DbConnection;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;


public class MainInterattivo {

    //Oggetto scanner per leggere l'input dell'utente
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try(Connection conn = DbConnection.open()){

            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║           GESTIONE PIZZERIA        ║");
            System.out.println("╚════════════════════════════════════╝");

            //Creiamo un oggetto PizzaDao per gestire le operazioni sulle pizze
            PizzaDao pizzaDao = new PizzaDao(conn);

            //Variabile per controllare il loop del menu
            boolean continua = true;

            //Loop principale del programma
            while(continua){
                //mostriamo il menu
                mostraMenu();

                //leggiamo la scelta dell'utente
                System.out.println("Scelta: ");
                int scelta = leggiIntero();

                System.out.println();//riga vuota per leggibilitò

                switch (scelta){
                    case 1:
                        inserisciPizza(pizzaDao);
                        break;

                    case 2:
                        visualizzaTutteLePizze(pizzaDao);
                        break;

                    case 3:
                        cercaPizzaPerID(pizzaDao);
                        break;

                    case 4:
                        modificaPizza(pizzaDao);
                        break;

                    case 5:
                        eliminaPizza(pizzaDao);
                        break;

                    case 6:
                        importaPizzeDaCsv();
                        break;

                    case 7:
                        esportaPizzeInCsv();
                        break;

                    case 0:
                        continua = false;
                        System.out.println("Arrivederci!!");
                        break;

                    default:
                        System.out.println("Scelta non valida! Riprova.");
                }

            }



        }catch(Exception e){
            System.err.println("Errore: " + e.getMessage());
        }finally {
            //chiudiamo lo scanner alla fine
            scanner.close();
        }

    }

    /**
     * Mostra il menu principale all'utente
     */
    private static void mostraMenu() {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║            MENU PRINCIPALE         ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  1. Inserisci nuova pizza          ║");
        System.out.println("║  2. Visualizza tutte le pizze      ║");
        System.out.println("║  3. Cerca pizza per ID             ║");
        System.out.println("║  4. Modifica pizza                 ║");
        System.out.println("║  5. Elimina pizza                  ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║      FUNZIONALITÀ AVANZATE         ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  6. Importa pizze da file CSV      ║");
        System.out.println("║  7. Esporta pizze in file CSV      ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  0. Esci                           ║");
        System.out.println("╚════════════════════════════════════╝");
    }

    /**
     * CREATE - Inserisce una nuova pizza nel database
     */
    private static void inserisciPizza(PizzaDao pizzaDao) {

    }

    /**
     * READ ALL - Visualizza tutte le pizze del database
     */
    private static void visualizzaTutteLePizze(PizzaDao pizzaDao) {

    }

    /**
     * READ BY ID - Cerca una pizza specifica per ID
     */
    private static void cercaPizzaPerID(PizzaDao pizzaDao) {

    }

    /**
     * UPDATE - Modifica una pizza esistente
     */
    private static void modificaPizza(PizzaDao pizzaDao) {

    }

    /**
     * DELETE - Elimina una pizza dal database
     */
    private static void eliminaPizza(PizzaDao pizzaDao) {

    }

    // ========== METODI HELPER ==========

    /**
     * lEGGE UN NUMERO INTERO DALLO SCANNER GESTENDO GLI ERRORI
     */
    private static int leggiIntero(){
        while(true){
            try{
                int numero = Integer.parseInt(scanner.nextLine());
                return numero;
            }catch(NumberFormatException e){
                System.out.println("Input non valido. Inserisci un numero intero: ");
            }
        }
    }

    /**
     * Legge un numero decimale dallo scanner, gestendo gli errori
     */
    private static double leggiDouble(){
        while(true){
            try{
                double numero = Double.parseDouble(scanner.nextLine());
                return numero;
            }catch(NumberFormatException e){
                System.out.println("Input non valido. Inserisci un numero decimale: ");
            }
        }
    }

    /**
     * Taglia una stringa
     */


    // ========== FUNZIONALITÀ AVANZATE ==========
    // Questa sezione contiene funzionalità che verranno spiegate in lezioni successive

    /**
     * IMPORT CSV - Importa pizze da un file CSV
     *
     * Questa funzionalità permette di caricare rapidamente molte pizze
     * da un file CSV invece di inserirle una per una manualmente.
     */
    private static void importaPizzeDaCsv() {

    }

    /**
     * EXPORT CSV - Esporta pizze in un file CSV
     *
     * Questa funzionalità permette di esportare i dati delle pizze
     * dal database in un file CSV per backup o analisi.
     */
    private static void esportaPizzeInCsv() {

    }

}