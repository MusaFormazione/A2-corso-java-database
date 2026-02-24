package it.corsojavadb.dao;

import it.corsojavadb.db.DbConnection;
import it.corsojavadb.model.Pizza;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PizzaDAO implements iDao{


    private final SessionFactory sessionFactory;

    public PizzaDAO() {
        //Otteniamo la connessione al db configurata in DbConnection
        this.sessionFactory = DbConnection.getSessionFactory();
    }


    /**
     * Recupera tutte le pizze dal db
     *
     * Useremo HQL(Hibernate Query Language) per selezionare tutte le righe della tabella pizzas
     *
     * @return List<Pizza>
     */
    @Override
    public List<Pizza> findAll() {
        //Apriamo la session
        try(Session session = sessionFactory.openSession()){
            //creiamo una query per ottenere le pizza
            return session.createQuery("from Pizza", Pizza.class).list();
        }

    }

    /**
     * Cerca una pizza specifica usando il suo ID
     *
     * Se la pizza non esiste, find() restituisce null
     * A questo punto useremo Optional.ofNullable() per gestire il modo sicuro il caso null
     *
     * @param id - id della pizza da cercare
     * @return Optional<Pizza> - Contiene la pizza se trovata
     */
    @Override
    public Optional<Pizza> findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            //find() cerca nel db un oggetto pizza con questo id
            Pizza p = session.find(Pizza.class, id);

            return Optional.ofNullable(p);
        }
    }

    /**
     * Salva una nuova pizza nel db (INSERT)
     *
     * @param pizza - l'oggetto pizza da salvare (senza id)
     * @return Pizza - l'oggetto salvato con id assegnato dal db
     */
    @Override
    public Pizza save(Pizza pizza) {
        //variabile per gestire la transazione
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            //inizio transazione
            tx = session.beginTransaction();

            session.persist(pizza);

            tx.commit();

            return pizza;
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    /**
     * Aggiorna una pizza esistente nel db
     *
     * @param pizza - la pizza con i dati aggiornati (deve avere id valido)
     * @return Pizza - l'oggetto pizza aggiornato e gestito da Hibernate
     */
    @Override
    public Pizza update(Pizza pizza) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            //inizio transazione
            tx = session.beginTransaction();

            //merge() aggiorna la pizza nel db e restituisce un oggetto pizza (merged)
            //Hibernate genera l'SQL necessario per UPDATE con i campi modificati
            Pizza merged = session.merge(pizza);

            tx.commit();
            return merged;
        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }

    /**
     * Elimina una pizza dal db usando il suo id
     *
     * @param id - id della pizza da eliminare
     */
    @Override
    public void delete(Long id) {
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()){
            //inizio transazione
            tx = session.beginTransaction();

            //cerco la pizza nel db
            Pizza p = session.find(Pizza.class, id);

            //controllo che la pizza esista, se esiste la eliminamo
            if(p != null){
                //remove() elimina l'oggetto dal db
                session.remove(p);
                tx.commit();
            }

        }catch(Exception e){
            if(tx != null) tx.rollback();
            throw e;
        }
    }
}
