package it.corsojavadb.app.setup;

import it.corsojavadb.app.db.DbConnection;

import java.sql.Connection;
import java.sql.Statement;

public class TestResetDatabase {
    public static void main(String[] args){
        try(Connection conn = DbConnection.open();
            Statement stmt = conn.createStatement()){

            stmt.executeUpdate("DROP TABLE IF EXISTS pizzas");
            System.out.println("Dropped table pizzas if existed");

            // remove migration record if migrations table exists
            try{
                stmt.executeUpdate("DELETE FROM migrations WHERE version = 'V001'");
                System.out.println("Removed migration V001 record if existed");
            }catch(Exception e){
                System.out.println("Could not remove migration record (table may not exist): " + e.getMessage());
            }

        }catch(Exception e){
            System.err.println("Error during reset: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
