package it.corsojavadb.app.db;

import java.sql.Connection;//per stabilire la connessione
import java.sql.DriverManager;//helper per restituire la connessione
import java.sql.SQLException;

public class DbConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public static Connection open() throws SQLException, ClassNotFoundException{
        //forName è un metodo statico della classe Class che carica la classe specificata
        Class.forName("com.mysql.cj.jdbc.Driver");//carica il driver mysql
        return DriverManager.getConnection(URL, USER, PASSWORD);//avvia e restituisce la connessione oppure lancia un'eccezione
    }

    public static void main(String[] args){

        try(Connection conn = open()){
            System.out.println("Connessione avvenuta con successo!" + conn.getMetaData().getURL());
        }catch(Exception e){
            System.err.println("Connessione Fallita" + e.getMessage());
        }

    }

}
