package it.corsojavadb;

import it.corsojavadb.db.DbConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import it.corsojavadb.model.Pizza;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args){

        System.out.println("====ESEMPIO DI UTILIZZO DI HIBERNATE SU ENTITY PIZZA====");


        //Variabile per la transazione
        Transaction transaction = null;

        try{

            //1. ottengo la connessione al database tramite DbConnection (Classe utility per Hibernate)
            System.out.println("1. Ottengo la connessione al database...");
            SessionFactory sessionFactory = DbConnection.getSessionFactory();

            if(sessionFactory != null){

                System.out.println("2. SessionFactory creata correttamente!");
                System.out.println("3. Hibernate ha creato/aggiornato la tabella pizzas nel db");

                //Apro la sessione (connessione attiva al db)
                System.out.println("4. Apro una session per fare operazioni sul database....");
                Session session = sessionFactory.openSession();

                //inizio una transazione
                transaction = session.beginTransaction();
                System.out.println("Transazione iniziata!");

                System.out.println("Creo una nuova pizza...");

                Pizza margherita = new Pizza(
                        "Margherita",
                        "Pomodoro, Mozzarella, Basilico",
                        new BigDecimal("5.50")
                );
                System.out.println(" Pizza Creata: " + margherita);

                //salvo la pizza nel db
                System.out.println("Salvo la pizza nel db");
                session.persist(margherita);//persist dice ad hibernate di salvare l'oggetto

                //confermo la transazione
                transaction.commit();
                System.out.println("Transazione confermata! Pizza salvata con ID: " + margherita.getId());

                session.close();
                System.out.println("Session chiusa!");

                System.out.println("OPERAZIONE CONCLUSA CON SUCCESSO");


            }else{
                System.err.println("Errore: SessionFactory è null");
            }


        }catch(Exception e){
            if(transaction != null){
                transaction.rollback();
                System.out.println("Transazione annullata a causa di un errore: " + e.getMessage());
            }
        }finally {
            //Chiudo sempre la connessione al db quando finisce il programma
            System.out.println("Chiudo la connessione al DB...");
            DbConnection.closeConnection();;
        }

    }


}
