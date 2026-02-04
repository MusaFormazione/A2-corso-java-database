package it.corsojavadb.app.util;

import it.corsojavadb.app.dao.PizzaDao;
import it.corsojavadb.app.db.DbConnection;
import it.corsojavadb.app.model.Pizza;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.List;


/**
 * Classe responsabile dell'importazione di dati dal database a file csv
 *
 * Permette di leggere un file CSV contenente dati di pizze e inserirli automaticamente nel db.
 *
 * Formato CSV atteso:
 * nome,ingredienti,prezzo
 * Margherita, pomodoro+mozzarella+basilico,5.50
 * Diavola,pomodoro+mozzarella+salame piccante,1.00
 */
public class CsvImporter {

    private static final String CSV_SEPARATOR = ",";
    private static final String CSV_SEPARATOR_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    //Questa regex permette di gestire virgole all'interno di campi racchiusi tra virgolette

    /**
     * importa le pizze da un file csv nel db
     *
     * @param filePath il percorso del file csv da importare
     * @return il numero di record importati
     * @throws IOException se ci sono problemi nella lettura del file
     * @throws SQLException se ci sono problemi con il database
     * @throws ClassNotFoundException se il driver jcbc non viene trovato
     */
    public static int importPizzasFromCsv(String filePath) throws IOException, SQLException, ClassNotFoundException{
        return importPizzasFromCsv(filePath, true);
        // Questo metodo è un esempio di OVERLOADING(sovraccarico) dei metodi.
    }

    /**
     * importa le pizze da un file csv nel db
     *
     * @param filePath il percorso del file csv da importare
     * @param hasHeader indica se il file CSV ha una riga di intestazione da saltare
     * @return il numero di record importati
     * @throws IOException se ci sono problemi nella lettura del file
     * @throws SQLException se ci sono problemi con il database
     * @throws ClassNotFoundException se il driver jdbc non viene trovato
     */
    public static int importPizzasFromCsv(String filePath, boolean hasHeader) throws IOException, SQLException, ClassNotFoundException{
        int recordImported = 0;
        int lineNumber = 0;

        System.out.println("\n=== IMPORTAZIONE CSV ===");
        System.out.println("File: " + filePath);
        System.out.println("==========================\n");

        try(Connection conn = DbConnection.open();BufferedReader reader = new BufferedReader(new FileReader(filePath))){



            PizzaDao pizzaDao = new PizzaDao(conn);;
            String line;

            //Leggo il file riga per riga
            while((line = reader.readLine()) != null){
                lineNumber++;

                //salto la riga di intestazione
                if(hasHeader && lineNumber == 1){
                    System.out.println("Saltata riga di intestazione: " + line);
                    continue;
                }

                //salto le righe vuote
                if(line.trim().isEmpty()){
                    System.out.println("Saltata riga vuota alla linea : " + lineNumber);
                    continue;
                }

                try{

                    //parsing della riga csv
                    Pizza pizza = parseCsvLine(line, lineNumber);

                    int id = pizzaDao.create(pizza);
                    recordImported++;
                    System.out.println("Importata pizza #" + id + ": " + pizza.getNome() + " (€" + pizza.getPrezzo() + ")");



                }catch(Exception e){
                    System.err.println("Errore alla linea " + lineNumber + ": " + e.getMessage());
                    System.err.println(" Riga: " + line);
                }

            }

        }catch(IOException e){
            System.err.println("Errore nella lettura del file: " + e.getMessage());
            throw e;
        }catch(SQLException e){
            System.err.println("Errore di connessione al db: " + e.getMessage());
            throw e;
        }

        System.out.println("\n=== RISULTATI IMPORTAZIONE ===");
        System.out.println("Record importati con successo: " + recordImported );
        System.out.println("Righe totali processate: " + (lineNumber - (hasHeader ? 1 : 0)));
        System.out.println("================================\n");

        return recordImported;

    }

    /**
     * Effettua il parsing di una riga CSV e crea un oggetto Pizza
     * @param line la riga csv da parsare
     * @param lineNumber il numero della riga (per messaggi di errore)
     * @return un oggetto Pizza
     * @throws IllegalArgumentException se i dati non sono nel formato corretto
     */
    private static Pizza parseCsvLine(String line, int lineNumber) throws IllegalArgumentException{
        //rimuovo spazi bianchi all'inizio e alla fine
        line = line.trim();

        //divido la riga usando il separatore
        String[] fields = line.split(CSV_SEPARATOR);

        //Verifico che ci siano esattamente 3 campi
        if(fields.length != 3){
            throw new IllegalArgumentException(
                    "Formato non valido. Attesi 3 campi (nome, ingredienti, prezzo), trovati " + fields.length
            );
        }

        try {
            //estraggo e pulisco i campi
            String nome = cleanField(fields[0]);
            String ingredienti = cleanField(fields[1]);
            String prezzoStr = cleanField(fields[2]);

            //validazione dei campi
            if(nome.isEmpty()){
                throw new IllegalArgumentException("Il nome della pizza non può essere vuoto");
            }

            if(ingredienti.isEmpty()){
                throw new IllegalArgumentException("Gli ingredienti della pizza non possono essere vuoti");
            }

            //Parsing del prezzo
            double prezzo;
            try{
                prezzo = Double.parseDouble(prezzoStr);
                //se la conversione da stringa a numero non va, significa che il numero non è nel formato valido, bisogna lanciare l'eccezione
            }catch(NumberFormatException e){
                throw new IllegalArgumentException("Il prezzo '" + prezzoStr + "' non è un numero valido");
            }

            //validazione del prezzo
            if(prezzo <= 0){
                throw new IllegalArgumentException("Il prezzo della pizza deve superiore a zero");
            }


            //Creo e restituisco l'oggetto Pizza
            return new Pizza(nome, ingredienti, prezzo);

        }catch(Exception e){
            throw new IllegalArgumentException("Errore nel parsing della riga: " + e.getMessage());
        }


    }


    /**
     * Pulisce un campo CSV rimuovendo spazi bianchi e virgolette
     *
     * @param field il campo da pulire
     * @return il campo pulito
     */
    private static String cleanField(String field) {
        if (field == null) {
            return "";
        }

        // Rimuove spazi all'inizio e alla fine
        field = field.trim();

        // Rimuove virgolette se presenti all'inizio e alla fine
        if (field.startsWith("\"") && field.endsWith("\"") && field.length() > 1) {
            field = field.substring(1, field.length() - 1);
        }

        return field.trim();
    }

    /**
     * Metodo di utilità per creare un file CSV di esempio
     *
     * @param filePath il percorso dove creare il file di esempio
     * @throws IOException se ci sono problemi nella scrittura del file
     */
    public static void createSampleCsvFile(String filePath) throws IOException {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(filePath))) {
            writer.println("nome,ingredienti,prezzo");
            writer.println("Margherita,pomodoro mozzarella basilico,5.50");
            writer.println("Diavola,pomodoro mozzarella salame piccante,7.00");
            writer.println("Quattro Stagioni,pomodoro mozzarella funghi carciofi prosciutto olive,8.50");
            writer.println("Capricciosa,pomodoro mozzarella funghi carciofi prosciutto,8.00");
            writer.println("Quattro Formaggi,mozzarella gorgonzola fontina parmigiano,9.00");
            writer.println("Marinara,pomodoro aglio origano,4.50");
            writer.println("Vegetariana,pomodoro mozzarella melanzane zucchine peperoni,7.50");

            System.out.println("✔ File CSV di esempio creato: " + filePath);
        }
    }
}
