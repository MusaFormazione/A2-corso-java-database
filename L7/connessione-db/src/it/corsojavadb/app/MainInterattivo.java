package it.corsojavadb.app;

import it.corsojavadb.app.dao.PizzaDao;
import it.corsojavadb.app.model.Pizza;
import it.corsojavadb.app.db.DbConnection;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import it.corsojavadb.app.setup.Migration;
import it.corsojavadb.app.setup.MigrationRunner;
import it.corsojavadb.app.setup.migrations.V001_CreatePizzaTable;
import it.corsojavadb.app.util.CsvImporter;


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

                    case 8:
                        eseguiMigrazioni();
                        break;

                    case 9:
                        rollbackUltimaMigrazione();
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
        System.out.println("║       GESTIONE MIGRAZIONI          ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  8. Esegui migrazioni database     ║");
        System.out.println("║  9. Rollback ultima migrazione     ║");
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
        try{
            printFormattedHeadline("IMPORTAZIONE DA FILE CSV");

            System.out.println("Opzioni disponibili:");
            System.out.println("1. Importa da file esistente");
            System.out.println("2. Crea file csv di esempio e importa");
            System.out.println("0. Torna al menu principale");

            System.out.println("\nScelta:");
            int scelta = leggiIntero();

            System.out.println();//riga vuota per leggibilità

            switch(scelta){
                case 1:
                    System.out.println("Inserisci il percorso del file CSV:");
                    String filePath = scanner.nextLine();

                    System.out.println("Il file ha una riga di intestazione? (S/N): ");
                    String hasHeaderString = scanner.nextLine();
                    boolean hasHeader = hasHeaderString.equalsIgnoreCase("S") || hasHeaderString.equalsIgnoreCase("SI");

                    int recordsImported = CsvImporter.importPizzasFromCsv(filePath, hasHeader);

                    if(recordsImported > 0){
                        System.out.println("\n Importazione completata!");
                    }else{
                        System.out.println("\n Nessuna pizza è stata importata");
                    }
                    break;
                case 2:

                    //creo file di esempio
                    String sampleFilePath = "pizzas_esempio.csv";
                    //Il percorso del file di esempio è relativo alla directory da cui viene eseguito il programma

                    System.out.println("Creazione file di esempio: " + sampleFilePath);
                    CsvImporter.createSampleCsvFile(sampleFilePath);

                    System.out.println("\nImportazione file creato...");
                    int records = CsvImporter.importPizzasFromCsv(sampleFilePath);

                    if(records > 0){
                        System.out.println("\n Importazione completata!");
                    }else{
                        System.out.println("\n Nessuna pizza è stata importata");
                    }

                    break;
                case 0:
                    System.out.println("\n Operazione annullata");
                    break;
                default:
                    System.out.println("\n Scelta non valida");
            }

        }catch(Exception e){
            System.err.println("Errore durante l'importazione, verifica che il file esista e sia nel formato corretto.\n" + e.getMessage());
        }
    }

    /**
     * EXPORT CSV - Esporta pizze in un file CSV
     *
     * Questa funzionalità permette di esportare i dati delle pizze
     * dal database in un file CSV per backup o analisi.
     */
    private static void esportaPizzeInCsv() {

    }



    /**
     * ESEGUI MIGRAZIONI - Esegue tutte le migrazioni non ancora applicate
     *
     * Le migrazioni sono script che modificano la struttura del database
     * in modo controllato e tracciabile. Permettono di evolvere il database
     * mantenendo la cronologia dei cambiamenti.
     */
    private static void eseguiMigrazioni() {
        try {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║    ESECUZIONE MIGRAZIONI DATABASE  ║");
            System.out.println("╚════════════════════════════════════╝\n");

            // Creiamo la lista delle migrazioni disponibili
            List<Migration> migrations = new ArrayList<>();
            migrations.add(new V001_CreatePizzaTable());
            // Qui puoi aggiungere altre migrazioni in futuro:
            // migrations.add(new V002_AddIngredientsTable());
            // migrations.add(new V003_CreateOrdersTable());

            System.out.println("Migrazioni disponibili: " + migrations.size());
            System.out.print("\nVuoi procedere con l'esecuzione? (S/N): ");
            String conferma = scanner.nextLine();

            if (conferma.equalsIgnoreCase("S") || conferma.equalsIgnoreCase("SI")) {
                // Esegui le migrazioni
                MigrationRunner.runMigrations(migrations);
                System.out.println("\n✅ Processo di migrazione completato!");
            } else {
                System.out.println("ℹ️  Operazione annullata.");
            }

        } catch (Exception e) {
            System.err.println("❌ Errore durante l'esecuzione delle migrazioni: " + e.getMessage());
        }
    }

    /**
     * ROLLBACK - Esegue il rollback dell'ultima migrazione
     *
     * Il rollback annulla l'ultima modifica al database, riportandolo
     * allo stato precedente. Utile per correggere errori o testare migrazioni.
     *
     * ⚠️ ATTENZIONE: Il rollback può causare perdita di dati!
     */
    private static void rollbackUltimaMigrazione() {
        try {
            System.out.println("╔════════════════════════════════════╗");
            System.out.println("║   ROLLBACK ULTIMA MIGRAZIONE       ║");
            System.out.println("╚════════════════════════════════════╝\n");


            // Creiamo la lista delle migrazioni disponibili
            List<Migration> migrations = new ArrayList<>();
            migrations.add(new V001_CreatePizzaTable());
            // Aggiungi qui altre migrazioni quando le crei

            System.out.print("Sei sicuro di voler procedere con il rollback? (S/N): ");
            String conferma = scanner.nextLine();

            if (conferma.equalsIgnoreCase("S") || conferma.equalsIgnoreCase("SI")) {
                System.out.print("Conferma nuovamente digitando 'ROLLBACK': ");
                String confermaFinale = scanner.nextLine();

                if (confermaFinale.equals("ROLLBACK")) {
                    // Esegui il rollback
                    MigrationRunner.rollbackLast(migrations);
                    System.out.println("\n✅ Rollback completato!");
                } else {
                    System.out.println("ℹ️  Conferma non valida. Operazione annullata.");
                }
            } else {
                System.out.println("ℹ️  Operazione annullata.");
            }

        } catch (Exception e) {
            System.err.println("❌ Errore durante il rollback: " + e.getMessage());
        }
    }

    private static void printFormattedHeadline(String headline){
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║  "+headline+"      ║");
        System.out.println("╚════════════════════════════════════╝\n");
    }

}