package it.corsojavadb.service;


import it.corsojavadb.dao.PizzaDAO;
import it.corsojavadb.model.Pizza;

import java.util.List;
import java.util.Optional;

/**
 * Service Layer per la gestione della logica di business delle pizze.
 *
 * Cos'è un service?
 * è uno strato intermedio tra il controller e il DAO.Si occupa della logica di business, cioè le regole dell'applicazione.
 *
 * Perchè usarlo?
 * - separa la logica di business dall'accesso ai dati
 * - Il Controller non deve conoscere il DAO direttamente
 * - Possiamo aggiungere validazioni, calcoli, regole complesse
 * - Se cambiamo il DAO, il controller non deve cambiare
 *
 * Architettura a strati (LAYERED ARCHITECTURE):
 * Controller(UI) -> Service(Business Logic) -< DAO(Data Access) -> Database
 *
 * Esempio di logica di business_
 * - Verificare che il prezzo sia > 0 prima di salvare
 * - calcolo sconti e promozioni
 * - inviare notifiche quando una pizza viene creata
 * - Verificare che il nome non sia vuoto
 *
 * NOTA:
 * In questa applicazione semplice, usiamo il service solo come passaggio dati.
 * In un'app reale qui aggiungeremmo validazioni, calcoli ecc...
 *
 */
public class PizzaService {

    private final PizzaDAO dao;

    public PizzaService(){
        this.dao = new PizzaDAO();
    }

    public List<Pizza> findAll(){
        return dao.findAll();
    }

    public Optional<Pizza> findById(Long id){
        return dao.findById(id);
    }

    public Pizza save(Pizza p){
        return dao.save(p);
    }

    public Pizza update(Pizza p){
        return dao.update(p);
    }

    public void delete(Long id){
        dao.delete(id);
    }

}
