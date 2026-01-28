package it.corsojavadb.app;

import it.corsojavadb.app.db.DbConnection;
import java.sql.Connection;//serve a stabilire la connessione
import java.sql.ResultSet;//serve a gestire i risultati delle query
import java.sql.Statement;//serve a eseguire le query


public class Main {
    public static void main(String[] args) {

        try(Connection conn = DbConnection.open()){

            System.out.println("====== CONNESSIONE AL DATABASE RIUSCITA ===");

            //Creiamo un oggetto PizzaDao per gestire le operazioni sulle pizze


            //facciamo le operazioni CRUD

        }catch(Exception e){
            System.err.println("Errore: " + e.getMessage());
        }

    }
}