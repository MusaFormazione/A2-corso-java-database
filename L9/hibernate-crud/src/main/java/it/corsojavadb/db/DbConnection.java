package it.corsojavadb.db;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import it.corsojavadb.model.Pizza;
import java.util.Properties;


/**
 * Classe per gestire la connessione al database usando Hibernate.
 * La configurazione è fatta in modo programmatico
 */

public class DbConnection {

    //Configurazione Database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hibernate_pizza";
    private static final String DB_USERNAME = "user";
    private static final String DB_PASSWORD = "password";

    //L'oggetto SessionFactory gestisce tutte le connessioni al database
    private static SessionFactory sessionFactory;

    /**
     * Metodo che crea la SessionFactory, viene chiamato la prima volta che serve la connessione.
     * Tutta la configurazione è fatta qui
     */
    private static void buildSessionFactory(){

        try{

            System.out.println("Configurazione Hibernate avviata...");

            //1. Crea l'oggetto Configuration
            Configuration configuration = new Configuration();

            //2. Crea un oggetto Properties per le impostazioni
            Properties settings = new Properties();

            //CONFIGURAZIONE CONNESSIONE AL DATABASE

            //Driver JDBC per MySQL
            settings.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");

            settings.put("hibernate.connection.url", DB_URL);
            settings.put("hibernate.connection.username", DB_USERNAME);
            settings.put("hibernate.connection.password", DB_PASSWORD);


            //Gestione automatica dello schema del db
            //update = aggiorna/crea le tabelle senza cancellarle
            settings.put("hibernate.hbm2ddl.auto", "update");

            // Mostra le query SQL nella console (utile per imparare!)
            settings.put("hibernate.show_sql", "true");

            // Formatta le query SQL in modo leggibile
            settings.put("hibernate.format_sql", "true");

            // Aggiunge commenti alle query SQL
            settings.put("hibernate.use_sql_comments", "true");


            // Pool di connessioni (quante connessioni tenere pronte)
            settings.put("hibernate.connection.pool_size", "10");
            // Usa il timezone UTC per evitare problemi con le date
            settings.put("hibernate.jdbc.time_zone", "UTC");


            // 3. Applica le impostazioni alla configurazione
            configuration.setProperties(settings);

            // 4. Registra le classi Entity (tabelle del database)
            // Ogni Entity deve essere aggiunta qui per essere gestita da Hibernate
            configuration.addAnnotatedClass(Pizza.class);

            // 5. Crea la SessionFactory con tutte le impostazioni
            sessionFactory = configuration.buildSessionFactory();

            System.out.println("✓ Connessione al database stabilita con successo!");


        }catch (Exception e) {
            System.err.println("✗ Errore nella creazione della connessione al database!");
            System.err.println("Dettagli errore: " + e.getMessage());
        }

    }

    /**
     * Metodo pubblico per ottenere la SessionFactory.
     * Se non esiste ancora, la crea automaticamente.
     *
     * @return SessionFactory per gestire le operazioni sul database
     */
    public static SessionFactory getSessionFactory() {
        // Se la connessione non è ancora stata creata (prima chiamata)
        if (sessionFactory == null) {
            buildSessionFactory(); // la crea
        }
        return sessionFactory;
    }

    /**
     * Metodo per chiudere la connessione.
     */
    public static void closeConnection(){
        if(sessionFactory != null && !sessionFactory.isClosed()){
            sessionFactory.close();//chiude tutte le connessioni
            System.out.println("Connessione al database chiusa.");
        }
    }


}
