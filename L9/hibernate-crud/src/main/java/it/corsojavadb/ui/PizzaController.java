package it.corsojavadb.ui;


import it.corsojavadb.model.Pizza;
import it.corsojavadb.service.PizzaService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * Controller JavaFX per la gestione dell'interfaccia grafica delle pizze.
 *
 * Cos'è un controller in JavaFX?
 * Il controller è la classe Java che gestisce l'interfaccia grafica(un file fxml).
 * è il cervello della finestra: risponde ai click dei pulsanti, aggiorna la tabella, legge i dati dei campi di testo.
 *
 * PATTERN MVC (MODEL-VIEW-CONTROLLER):
 * Model: La classe Pizza
 * View: il file fxml pizza_main.fxml
 * Controller: Questa classe (Collega model e view)
 *
 * Annotazione @FXML:
 * Questa annotazione collega i componenti grafici del file fxml con le variabili Java.
 * Il nome della variabile DEVE corrispondere all'fx:id nel file fxml
 *
 * ESEMPIO: nel file fxml abbiamo <TableView fx:id="tablePizze" ...>
 *      Qui abbiamo @FXML private TableView<Pizza> tablePizze;
 *      JavaFX li collega automaticamente.
 */
public class PizzaController {

    //====================== COMPONENTI DELLA TABELLA ====================


    /**
     * La tabella che mostra tutte le pizze
     * TableView<Pizza> significa che ogni riga della tabella contiene un oggetto pizza
     */
    @FXML
    private TableView<Pizza> tablePizze;

    @FXML
    private TableColumn<Pizza, Long> idCol;

    @FXML
    private TableColumn<Pizza, String> nomeCol;

    @FXML
    private TableColumn<Pizza, String> ingredientiCol;

    @FXML
    private TableColumn<Pizza, String> prezzoCol;


    // ============= CAMPI DI INPUT ==========
    @FXML
    private TextField nomeField;

    @FXML
    private TextField ingredientiField;

    @FXML
    private TextField prezzoField;


    //============== PULSANTI ==============

    @FXML
    private Button createBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    //============== INTERAZIONE CON IL SERVICE ==============

    private final PizzaService pizzaService = new PizzaService();


    /**
     * Metodo di inizializzazione del controller.
     *
     * Quando viene chiamato?
     * Viene chiamato automaticamente da JavaFX subito dopo che  tutti i componenti @FXML sono stati collegati.
     * è come il costruttore del controller, ma viene eseguito dopo che l'interfaccia è pronta
     *
     *
     * Cosa fa?
     * - Configura le colonne della tabella
     * - Carica la lista iniziale delle pizze dal db
     * - aggiunge in listener per riempire i campi quando si seleziona una riga
     *
     */
    @FXML
    public void initialize(){
        //configurazione della tabella

        //Colonna id: mostra pizza.getId()
        idCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));

        nomeCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNome()));

        ingredientiCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIngredienti()));

        prezzoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPrezzo().toString()));

        //caricamento pizze
        refreshList();

        //listener per la selezione della riga
        //Quando l'utente clicca su una riga della tabella, riempiamo i campi di testo

        tablePizze.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            //newSel è la pizza appena selezionata
            if(newSel != null){
                //riempiamo i campi con i dati della pizza selezionata
                nomeField.setText(newSel.getNome());
                ingredientiField.setText(newSel.getIngredienti());
                prezzoField.setText(newSel.getPrezzo().toString());
            }

        });


    }

    /**
     * Gestisce il click sul pulsante "create"
     *
     */
    @FXML
    public void onCreate(){

            try{
                //leggiamo i valori dai campi di testo
                String nome = nomeField.getText();
                String ingredienti = ingredientiField.getText();
                BigDecimal prezzo = new BigDecimal(prezzoField.getText());

                //creiamo una pizza
                Pizza p = new Pizza(nome, ingredienti, prezzo);

                //salviamo

                pizzaService.save(p);

                clearForm();

                refreshList();

            }catch(Exception e){
                showAlert("Errore","Impossibile creare la pizza: " + e.getMessage());
            }


    }

    @FXML
    public void onUpdate(){

        //otteniamo la pizza selezionata nella tabella
        Pizza selected = tablePizze.getSelectionModel().getSelectedItem();

        if(selected == null){
            showAlert("Attenzione","Seleziona prima una pizza da aggiornare");
            return;
        }

        try{
            //leggiamo i valori dai campi di testo
            selected.setNome(nomeField.getText());
            selected.setIngredienti(ingredientiField.getText());
            selected.setPrezzo(new BigDecimal(prezzoField.getText()));

            pizzaService.update(selected);

            clearForm();

            refreshList();

        }catch(Exception e){
            showAlert("Errore","Impossibile aggiornare la pizza: " + e.getMessage());
        }

    }

    @FXML
    public void onDelete(){

        //otteniamo la pizza selezionata nella tabella
        Pizza selected = tablePizze.getSelectionModel().getSelectedItem();

        if(selected == null){
            showAlert("Attenzione","Seleziona prima una pizza da eliminare");
            return;
        }

        try{

            pizzaService.delete(selected.getId());

            clearForm();

            refreshList();

        }catch(Exception e){
            showAlert("Errore","Impossibile eliminare la pizza: " + e.getMessage());
        }

    }

    /**
     * Aggiorna la lista delle pizze visualizzate nella tabella.
     *
     * Una observableList è una lista speciale di JavaFX che "ossserva" i cambiamenti.
     * Quando la lista cambia, la tabella si aggiorna automaticamente.
     * FXCollections.observableArrayList() converte la lista in una ObservableList
     *
     */
    private void refreshList(){
        //otteniamo le pizze
        List<Pizza> list = pizzaService.findAll();
        tablePizze.setItems(FXCollections.observableArrayList(list));
    }

    /**
     * Pulisce tutti i campi di testo del form.
     *
     */
    private void clearForm(){
        //.clear() è un metodo di TextField che svuota il campo dal suo contenuto
        nomeField.clear();
        ingredientiField.clear();
        prezzoField.clear();
    }

    /**
     * Mostra un messaggio di avviso/errore all'utente.
     *
     * COSA FA?
     * Crea e visualizza una finestra di dialogo con un messaggio.
     * La finestra rimane aperta finché l'utente non clicca "OK".
     *
     * COS'È UN Alert?
     * È una finestra popup di JavaFX per mostrare messaggi all'utente.
     * AlertType.INFORMATION crea un alert informativo (icona "i" blu).
     *
     * ALTRI TIPI DI ALERT:
     * - AlertType.ERROR: per errori (icona X rossa)
     * - AlertType.WARNING: per avvisi (icona ! gialla)
     * - AlertType.CONFIRMATION: per chiedere conferma (icona ?)
     *
     * @param title - il titolo della finestra di dialogo
     * @param message - il messaggio da mostrare all'utente
     */
    private void showAlert(String title, String message){

        Alert a = new Alert(Alert.AlertType.INFORMATION);

        //imposto il titolo
        a.setTitle(title);
        //imposto il messaggio da mostrare
        a.setContentText(message);
        //mostriamo l'alert e aspettiamo che l'utente clicchi ok
        a.showAndWait();

    }

}
