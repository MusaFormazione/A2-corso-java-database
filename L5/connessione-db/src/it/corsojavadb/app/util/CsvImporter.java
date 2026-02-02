package it.corsojavadb.app.util;

import it.corsojavadb.app.dao.PizzaDao;
import it.corsojavadb.app.db.DbConnection;
import it.corsojavadb.app.model.Pizza;

import java.sql.Connection;
import java.sql.SQLException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
     */
    public static int importPizzasFromCsv(String filePath){
        return importPizzasFromCsv(filePath, true);
        // Questo metodo è un esempio di OVERLOADING(sovraccarico) dei metodi.
    }

    /**
     * importa le pizze da un file csv nel db
     *
     * @param filePath il percorso del file csv da importare
     * @param hasHeader indica se il file CSV ha una riga di intestazione da saltare
     * @return il numero di record importati
     */
    public static int importPizzasFromCsv(String filePath, boolean hasHeader){

    }

}
