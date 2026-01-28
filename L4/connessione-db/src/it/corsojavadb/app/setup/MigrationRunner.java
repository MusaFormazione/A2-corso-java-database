package it.corsojavadb.app.setup;

import it.corsojavadb.app.db.DbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MigrationRunner {

    private static final String MIGRATION_TABLE = "migrations";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //Step1: creazione tabella delle migrazioni

    /**
     * Crea la tabella "migrations" che traccia le migrazioni eseguite
     * Se esiste già, non fa null
     */
    private static void createMigrationTable(Connection conn) throws SQLException {
        //verificare se la tabella esiste
        DatabaseMetaData meta = conn.getMetaData();
        //ottiene i metadati del database, ossia le informazioni sulla struttura stessa del db

        //ora che abbiamo i metadati, possiamo usarli per controllare se la tabella delle migrazioni esiste
        ResultSet tables = meta.getTables(null, null, MIGRATION_TABLE, new String[]{"table"});
        //spiegazione parametri:
        //1° parametro (null): catalogo del database, null siccome usiamo mySql che non supporta i cataloghi
        //2° parametro (null): schema del database, null siccome usiamo mySql che non supporta gli schemi
        //3° parametro (MIGRATION_TABLE): nome della tabella che vogliamo controllare
        //4° parametro (new String[]{"table"}): tipo di oggetto che vogliamo controllare, in questo caso una tabella


        if(tables.next()){
            //se esiste già
            return;
        }

        String sql = "CREATE TABLE " + MIGRATION_TABLE + " (" +
                "id INT AUTO_INCREMENT PRIMARY KEY,"+
                "version VARCHAR(50) NOT NULL UNIQUE,"+
                "description VARCHAR(255),"+
                "executed_at DATETIME DEFAULT CURRENT_TIMESTAMP,"+
                "success BOOLEAN DEFAULT 1,"+
                ")";

        try(Statement stmt = conn.createStatement()){
            stmt.execute(sql);
        }


    }

    //Step2: creazione metodi per leggere, inserire, eliminare migrazioni

    /**
     * Recupera l'elenco delle migrazioni già eseguite con successo
     */
    private static List<String> getExecutedMigration(Connection conn) throws SQLException{

        List<String> executed = new ArrayList<>();

        String sql = "SELECT version FROM " + MIGRATION_TABLE + " WHERE success = 1 ORDER BY version ASC";

        try(
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){

            while (rs.next()){
                executed.add(rs.getString("version"));
            }

        }

        return executed;
    }

    /**
     * registra una migrazione nella tabella di tracciamento
     */
    private static  void recordMigration(Connection conn, String version, String description, boolean success) throws SQLException{

        String sql = "INSERT INTO " + MIGRATION_TABLE + " (version, description, executed_at, success) VALUES (?,?,?,?)";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, version);
            stmt.setString(2, description);
            stmt.setString(3, LocalDateTime.now().format(DATE_FORMATTER));
            stmt.setBoolean(4, success);
            stmt.executeUpdate();
        }

    }

    /**
     * Rimuove il record di una migrazione dal db
     */
    private static void removeMigrationRecord(Connection conn, String version) throws SQLException{
        String sql = "DELETE FROM " + MIGRATION_TABLE + " WHERE version = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, version);
            stmt.executeUpdate();
        }
    }

    private static boolean isMigrationExecuted(Connection conn, String version) throws SQLException {
        String sql = "SELECT 1 FROM " + MIGRATION_TABLE + " WHERE version = ? AND success = 1";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, version);
            try(ResultSet rs = stmt.executeQuery()){
                return rs.next();
            }
        }
    }

    //Step 3: metodi per lancio migrazioni e rollback

    /**
     * Esegue tuttew le migrazioni non ancora applicate
     *
     * @param migrations lista di migrazioni da eseguire
     * @throws SQLException se qualcosa va male
     */
    private static void runMigrations(List<Migration> migrations) throws SQLException{

        try(Connection conn = DbConnection.open()){

            //step1: crea la tabella di tracciamento se non esiste
            createMigrationTable(conn);

            //Step 2: Recupera le migrazioni già eseguite
            List<String> executedMigrations = getExecutedMigration(conn);

            System.out.println("=========ESECUZIONE MIGRAZIONI===========");
            System.out.println("Numero migrazioni già eseguite: " + executedMigrations.size());

            int executed = 0;
            //ci siamo interrotti qui

        }catch(Exception e){

        }

    }


}
