package it.corsojavadb.app.model;

/**
 * Questa classe rappresenza una pizza.
 * è un modello che contiene i dati di una pizza nel nostro database.
* */
public class Pizza {

    //Attributi della pizza(le colonne della tabella nel database)
    private int id; //chiave primaria
    private String nome;//es Margherita
    private String ingredienti;//es pomodoro, mozzarella
    private double prezzo;


    public Pizza(String nome, String ingredienti, double prezzo){
        //id viene generato automaticamente dal database
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.prezzo = prezzo;
    }

    public Pizza(int id, String nome, String ingredienti, double prezzo) {
        this.id = id;
        this.nome = nome;
        this.ingredienti = ingredienti;
        this.prezzo = prezzo;
    }

    //METODI GETTER E SETTER

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }


    /**
     * Restituisce una rappresentazione testuale della pizza.
     *
     * @return una stringa che rappresenta la pizza
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
