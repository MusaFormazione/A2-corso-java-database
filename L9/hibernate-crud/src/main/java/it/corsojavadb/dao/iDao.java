package it.corsojavadb.dao;

import it.corsojavadb.model.Pizza;

import java.util.List;
import java.util.Optional;

public interface iDao {

    List<Pizza> findAll();
    Optional<Pizza> findById(Long id);
    Pizza save(Pizza pizza);
    Pizza update(Pizza pizza);
    void delete(Long id);

}
