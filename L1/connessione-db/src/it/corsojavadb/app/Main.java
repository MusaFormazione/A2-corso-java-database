package it.corsojavadb.app;

import it.corsojavadb.app.db.DbConnection;
import java.sql.Connection;//serve a stabilire la connessione
import java.sql.ResultSet;//serve a gestire i risultati delle query
import java.sql.Statement;//serve a eseguire le query


public class Main {
    public static void main(String[] args) {

            String sql = "SELECT 1";//query di test per verificare la connessione al db. Significa "restituisci il valore 1"

            try(
                    Connection conn = DbConnection.open();//apriamo la connessione
                    Statement stmt = conn.createStatement();//creiamo lo statement per eseguire la query
                    ResultSet rs = stmt.executeQuery(sql);//eseguiamo la query e otteniamo i risultati
                    ){

                //analizziamo i risultati
                if(rs.next()){
                    System.out.println("Connessione avvenuta con successo! Valore restituito:" + rs.getInt(1));
                    //rs.getInt(1) restituisce il valore della prima colonna del risultato, columnindex serve a indicare la colonna
                    //se non indichi il columnindex, java restituisce un errore perchè non sa quale colonna restituire
                }else{
                    System.out.println("Nessun dato restituito");
                }

            }catch(Exception e){
                System.err.println("Connessione fallita " + e.getMessage());
            }

    }
}