package it.corsojavadb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity//Indica che la classe è una tabella nel db
@Table(name = "pizzas")//Nome della tabella (opzionale, di default usa il nome della classe)
public class Pizza {

    @Id//indica che questo campo è la chiave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto increment
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "Ingredienti", nullable = false, length = 500)
    private String ingredienti;

    @Column(name = "prezzo", nullable = false, precision = 10, scale = 2)
    //precision = 10 -> numero totale di cifre
    //scale = 2 -> cifre dopo la virgola
    private BigDecimal prezzo;



    //=============================================
    //COSTRUTTORI
    //=============================================

    /**
     * Costruttore vuoto (OBBLIGATORIO per Hibernate)
     * Hibernate usa questo costruttore per creare gli oggetti quando legge dal database
     */
    public Pizza(){

    }

    /**
     * Costruttore con parametri (per creare nuove pizze)
     *
     * @param nome il nome della pizza
     * @param ingredienti Lista ingredienti
     * @param prezzo Il prezzo
     */
    public Pizza(String nome, String ingredienti, BigDecimal prezzo){
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.prezzo = prezzo;
        //l'id non va importato perchè generato dal db
    }

    //=============================================
    //GETTER E SETTER
    //=============================================


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(String ingredienti) {
        this.ingredienti = ingredienti;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }


    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", ingredienti='" + ingredienti + '\'' +
                ", prezzo=" + prezzo +
                '}';
    }
}
