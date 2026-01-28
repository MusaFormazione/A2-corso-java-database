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

            if(rowsAffected > 0){//se l'inserimento è riuscito

                try(ResultSet generatedKeys = stmt.getGeneratedKeys()){
                    //generatedkeys è un nome convenzionale, indica che contiene le chiavi generate
                    //restituisce un ResultSet (contenente le chiavi)

                    if(generatedKeys.next()){
                        int id = generatedKeys.getInt(1);//prendiamo l'id della prima colonna
                        pizza.setId(id);//aggiorniamo l'oggetto pizza con l'id
                        return id;
                    }

                }

            }


        }

    throw new SQLException("Creazione pizza fallita, nessuna riga inserita.");
    }

    /**
     * Questo metodo cerca una pizza nel db usando il suo id
     * @params id
     * @returns L'oggetto pizza trovato
     * */
    public Pizza findById(int id) throws SQLException{

        String sql = "SELECT * from pizzas WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1,id);

            //eseguiamo la query
            try(ResultSet rs = stmt.executeQuery()){

                //se troviamo il risultato
                if(rs.next()){
                    return new Pizza(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("ingredienti"),
                            rs.getDouble("prezzo")
                    );
                }


            }

        }
        // se non troviamo nulla, restituiamo null
        return null;
    }

    /**
     * Questo metodo crecupera tutte le pizze nel db
     * @returns una lista di pizze
     * */
    public List<Pizza> getAll() throws SQLException{

        List<Pizza> pizzas = new ArrayList<>();

        String sql = "SELECT * from pizzas";

        try(
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ){

            while(rs.next()){

                Pizza pizza = new Pizza(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("ingredienti"),
                        rs.getDouble("prezzo")
                );
                pizzas.add(pizza);

            }

            return pizzas;

        }
    }


    /**
     * Questo metodo aggiorna una pizza esistente nel db
     * @params pizza - l'oggetto Pizza con i nuovi dati (Deve avere un id valido)
     * @returns true se l'aggiornamento è riuscito, false altrimenti
     * */
    public boolean update(Pizza pizza) throws SQLException{

        String sql = "UPDATE pizzas SET nome = ?, ingredienti = ?, prezzo = ? WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, pizza.getNome());
            stmt.setString(2, pizza.getIngredienti());
            stmt.setDouble(3, pizza.getPrezzo());
            stmt.setInt(4, pizza.getId());

            int affectedRows = stmt.executeUpdate();//eseguo l'aggiornamento

            return affectedRows > 0;//Se almeno una riga è stata modificata, l'operazione è riuscita
        }

    }

    /**
     * Questo metodo elimina una pizza esistente nel db
     * @params id - l'id della pizza da eliminare
     * @returns true se l'eliminazione è riuscita, false altrimenti
     * */
    public boolean delete(int id) throws SQLException {

        String sql = "DELETE FROM pizzas WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);


            int affectedRows = stmt.executeUpdate();//eseguo l'eliminazione

            return affectedRows > 0;//Se almeno una riga è stata modificata, l'operazione è riuscita

        }

    }

}
