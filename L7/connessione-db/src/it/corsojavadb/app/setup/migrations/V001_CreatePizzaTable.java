package it.corsojavadb.app.setup.migrations;

import it.corsojavadb.app.setup.Migration;
import it.corsojavadb.app.db.DbConnection;

import java.sql.Connection;
import java.sql.Statement;

public class V001_CreatePizzaTable implements Migration {

    private final String description = "Create pizzas table";

    @Override
    public String getVersion() {
        return "V001";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void up() throws Exception {
        try(
                Connection conn = DbConnection.open();
                Statement stmt = conn.createStatement()
        ){

            String sql = "CREATE TABLE pizzas ( id INT AUTO_INCREMENT PRIMARY KEY,nome VARCHAR(100) NOT NULL,ingredienti TEXT NOT NULL,prezzo DECIMAL(5,2) NOT NULL )";

            stmt.executeUpdate(sql);
            System.out.println("Tabella 'pizzas' creata con successo.");
        }
    }

    @Override
    public void down()  throws Exception {
        try(
                Connection conn = DbConnection.open();
                Statement stmt = conn.createStatement()
        ){

            String sql = "DROP TABLE IF EXISTS pizzas";

            stmt.executeUpdate(sql);
            System.out.println("Tabella 'pizzas' eliminata con successo.");
        }
    }
}
