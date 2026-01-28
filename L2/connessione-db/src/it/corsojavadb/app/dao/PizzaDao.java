package it.corsojavadb.app.dao;

import it.corsojavadb.app.model.Pizza;
import java.sql.*;//importa tutte le classi del package. meglio importarle tutte perchè ne usiamo tante
import java.util.ArrayList;
import java.util.List;



public class PizzaDao {

    private Connection connection;

    public PizzaDao(Connection connection){
        this.connection = connection;
    }

    //==========CREATE=========

    /**
     * Questo metodo inserisce una nuova pizza nel database
     * @param pizza - l'oggetto pizza da inserire
     * @return l'id generato dal database per la nuova pizza
     * @throws SQLException
     */
    public int create(Pizza pizza) throws SQLException{

        String sql = "INSERT INTO pizzas (nome, ingredienti, prezzo) VALUES (?,?,?)";

        //prepareStatement serve per eseguire query con parametri in sicurezza
        //RETURN_GENERATED_KEYS serve per ottenere l'id generato automaticamente dal db
        try(PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            //sostituisco i "?" con i valori reali della pizza
            //questo è fattibile in modo semplice poichè abbiamo già preparato tutti i getter nell'oggetto pizza
            stmt.setString(1, pizza.getNome()); //il primo "?" diventa il nome della pizza
            stmt.setString(2, pizza.getIngredienti());
            stmt.setDouble(3, pizza.getPrezzo());

            //Eseguiamo la query (executeUpdate si usa per INSERT, UPDATE, DELETE)
            int rowsAffected = stmt.executeUpdate();//restituisce il numero di righe interessate
            //in questo caso dovrebbe essere 1 se l'inserimento è andato a buon fine

            if(rowsAffected > 0){

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    //generatedkeys è un nome convenzionale, indica che contiene le chiavi generate
                    //restituisce un ResultSet (contenente le chiavi)

                    if(generatedKeys.next()){
                        int id = generatedKeys.getInt(1);//prendiamo l'id della prima colonna
                        pizza.setId(id);
                        return id;
                    }

                }

            }


        }

    }


}
