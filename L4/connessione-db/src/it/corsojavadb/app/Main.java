package it.corsojavadb.app;

import it.corsojavadb.app.dao.PizzaDao;
import it.corsojavadb.app.model.Pizza;
import it.corsojavadb.app.db.DbConnection;
import java.sql.Connection;//serve a stabilire la connessione
import java.sql.ResultSet;//serve a gestire i risultati delle query
import java.sql.Statement;//serve a eseguire le query
import java.util.List;


public class Main {
    public static void main(String[] args) {

        try(Connection conn = DbConnection.open()){

            System.out.println("====== CONNESSIONE AL DATABASE RIUSCITA ===");

            //Creiamo un oggetto PizzaDao per gestire le operazioni sulle pizze
            PizzaDao pizzaDao = new PizzaDao(conn);

            //facciamo le operazioni CRUD

            //======CREATE=========
            System.out.println("====== CREAZIONE NUOVA PIZZA ===");
            Pizza margherita = new Pizza("Margherita","pomodoro, mozzarella, basilico",5.50);
            int id1 = pizzaDao.create(margherita);
            System.out.println("Pizza inserita con ID " + id1 + " -> " + margherita);

            //======READ All=========
            System.out.println("====== READ ALL: Elenco di tutte le pizze===");
            List<Pizza> tutteLePizze = pizzaDao.getAll();//prendendo tutte le pizze dal db

            for(Pizza pizza : tutteLePizze){
                System.out.println(pizza);
            }

            //======READ BY ID=========
            System.out.println("====== READ BY ID: Cerco una pizza per ID===");
            Pizza pizzaTrovata = pizzaDao.findById(id1);//prendendo la pizza numero 17

            if(pizzaTrovata != null){
                System.out.println(pizzaTrovata);
            }else{
                System.out.println("Pizza non trovata");
            }

            //======UPDATE=========
            System.out.println("====== UPDATE =====");

            margherita.setPrezzo(6.00);
            boolean aggiornato = pizzaDao.update(margherita);

            if(aggiornato){
                System.out.println("Pizza aggiornata con successo!");
                Pizza pizzaAggiornata = pizzaDao.findById(id1);//prendendo la pizza numero 17
                System.out.println("Pizza dopo l'aggiornamento: " + pizzaAggiornata);
            } else {
                System.out.println("Aggiornamento fallito");
            }


            //======DELETE=========
            System.out.println("====== DELETE =====");
            boolean eliminato = pizzaDao.delete(id1);

            if(eliminato){
                System.out.println("Pizza eliminata con successo!");
            } else {
                System.out.println("Eliminazione fallita");
            }



        }catch(Exception e){
            System.err.println("Errore: " + e.getMessage());
        }

    }
}