package it.corsojavadb.app.util;


import it.corsojavadb.app.db.DbConnection;
import java.sql.Connection;

import it.corsojavadb.app.model.Pizza;
import it.corsojavadb.app.dao.PizzaDao;

import java.io.PrintWriter;
import java.io.FileWriter;

import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

/**
 * Classe responsabile dell'esportazione di dati da db a file CSV
 *
 * Questa classe permette di esportare tutte le pizze dal database ion un file CSV che può essere utilizzato per backup, analisi o importazione in altri sistemi.
 *
 */
public class CsvExporter {

    private static final String CSV_SEPARATOR = ",";
    private static final String DEFAULT_HEADER = "nome,ingredienti,prezzo";

    /**
     * Esporta tutte le pizze del db in un file CSV
     *
     * @param filePath il percorso del file CSV da creare
     * @return il numero di record esportati
     * @throws IOException se ci sono problemi nella scrittura del file
     * @throws SQLException se ci sono problemi con il database
     * @throws ClassNotFoundException se il driver jcbc non viene trovato
     */
    public static int exportPizzasToCsv(String filePath) throws SQLException, IOException, ClassNotFoundException{
        return exportPizzasToCsv(filePath, true);
    }

    /**
     * Esporta tutte le pizze del db in un file CSV
     *
     * @param filePath il percorso del file CSV da creare
     * @param includeHeader indica se includere la riga di intestazione
     * @return il numero di record esportati
     * @throws IOException se ci sono problemi nella scrittura del file
     * @throws SQLException se ci sono problemi con il database
     * @throws ClassNotFoundException se il driver jcbc non viene trovato
     */
    public static int exportPizzasToCsv(String filePath, boolean includeHeader) throws SQLException, IOException, ClassNotFoundException{
        int recordsExported = 0;

        System.out.println("\n=== ESPORTAZIONE CSV ===");
        System.out.println("File: " + filePath);
        System.out.println("==========================\n");

        try(
                Connection conn = DbConnection.open();
                PrintWriter writer = new PrintWriter(new FileWriter(filePath))
            ){

            PizzaDao pizzaDao = new PizzaDao(conn);
            List<Pizza> pizzas = pizzaDao.getAll();

            if(pizzas.isEmpty()){
                System.out.println("Nessuna pizza trovata nel db, Il file CSV sarà vuoto o conterrà solo l'intestazione. \n");
            }

            if(includeHeader){
                writer.println(DEFAULT_HEADER);
                System.out.println("Intestazione Scritta: "
                + DEFAULT_HEADER);
            }

            for(Pizza pizza : pizzas){
                String csvLine = buildCSVLine(pizza);
                writer.println(csvLine);
                recordsExported++;
                System.out.println("Esportata pizza #" + pizza.getId() + ": " + pizza.getNome() + " (€ " + pizza.getPrezzo() + ")");
            }

        }catch(IOException e){
            System.err.println("Errore nella scrittura del file: " + e.getMessage());
            throw e;
        }catch(SQLException e){
            System.err.println("Errore nella connessione al db: " + e.getMessage());
            throw e;
        }

        System.out.println("\n=== RISULTATI ESPORTAZIONE ===");
        System.out.println("Record esportati con successo: " + recordsExported);
        System.out.println("File: " + filePath);
        System.out.println("=================================\n");

        return recordsExported;

    }

    /**
     * Esporta pizze specifiche in un file CSV
     *
     * @param filePath il percorso del file CSV da creare
     * @param pizzas la lista di pizze da esportare
     * @param includeHeader indica se includere la riga di intestazione
     * @return il numero di record esportati
     * @throws IOException se ci sono problemi nella scrittura del file
     */
    public static int exportPizzaListToCsv(String filePath, List<Pizza> pizzas, boolean includeHeader) throws IOException {
        int recordsExported = 0;

        System.out.println("\n=== ESPORTAZIONE CSV ===");
        System.out.println("File: " + filePath);
        System.out.println("==========================\n");

        try(
                PrintWriter writer = new PrintWriter(new FileWriter(filePath))
        ){

            if(includeHeader){
                writer.println(DEFAULT_HEADER);
                System.out.println("Intestazione Scritta: " + DEFAULT_HEADER);
            }

            for(Pizza pizza : pizzas){
                String csvLine = buildCSVLine(pizza);
                writer.println(csvLine);
                recordsExported++;
                System.out.println("Esportata pizza #" + pizza.getId() + ": " + pizza.getNome() + " (€ " + pizza.getPrezzo() + ")");
            }

        }catch(IOException e){
            System.err.println("Errore nella scrittura del file: " + e.getMessage());
            throw e;
        }

        System.out.println("\n=== RISULTATI ESPORTAZIONE ===");
        System.out.println("Record esportati con successo: " + recordsExported);
        System.out.println("File: " + filePath);
        System.out.println("=================================\n");

        return recordsExported;
    }

    /**
     * Esporta una singola pizza in formato CSV (aggiunge al file esistente)
     *
     * @param filePath il percorso del file CSV
     * @param pizza la pizza da esportare
     * @param append se true aggiunge al file esistente, altrimenti lo sovrascrive
     * @throws IOException se ci sono problemi nella scrittura del file
     */
    public static void appendPizzaToCsv(String filePath, Pizza pizza, boolean append) throws IOException {

        try(PrintWriter writer = new PrintWriter(new FileWriter(filePath, append))){
            String csvLine = buildCSVLine(pizza);
            writer.println(csvLine);
            System.out.println("Pizza aggiunta al file");
        }

    }

    /**
     * Costruisce una rig csv da un oggetto Pizza
     *
     * @param pizza la pizza da convertire in CSV
     * @return la stringa CSV rappresentante la pizza
     */
    private static String buildCSVLine(Pizza pizza){
        StringBuilder sb = new StringBuilder();

        sb.append(escapeCsvField(pizza.getNome()));
        sb.append(CSV_SEPARATOR);

        sb.append(escapeCsvField(pizza.getIngredienti()));
        sb.append(CSV_SEPARATOR);

        //TODO: conversione punti e virgole decimali
        sb.append(pizza.getPrezzo());

        return sb.toString();
    }

    /**
     * Gestisce l'escape dei campi CSV
     * Se il campo contiene virgole, virgolette o a capo, lo racchiude tra virgolette
     *
     * @param field il campo da processare
     * @return il campo processato per CSV
     */
    private static String escapeCsvField(String field){
        if(field == null) return "";

        boolean needsQuotes = field.contains(CSV_SEPARATOR) ||  field.contains("\"") ||  field.contains("\n") ||  field.contains("\r");

        if(needsQuotes){
            //raddoppia le virgolette interne
            //raddoppiando le virgolette interne, si evita che vengano interpretate come delimitatori di campo
            String escaped = field.replace("\"","\"\"");
            return "\"" + escaped + "\"";
        }

        return field;
    }




}
