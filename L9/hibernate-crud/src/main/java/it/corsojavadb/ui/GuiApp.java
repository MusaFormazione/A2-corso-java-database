package it.corsojavadb.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * Ogni applicazione javaFX deve estendere la classe Aplication.
 * Application fornisce un ciclo di vita dell'applicazione
 *
 * - main() avvia l'applicazione
 * - launch() inizializzazione di JavaFX
 * - start() creazione della finestra principale
 *
 *
 *
 */
public class GuiApp extends Application {

    public static void main(String[] args){
        launch();
    }


    /**
     * lo stage è la finestra dell'applicazione. è il contenitore principale in cui mettiamo tutto(bottoni, tabelle ecc...)
     *
     * Scene è il contenuto della finestra. è come una pagina, quindi puoi acere più scene che inserirai in uno stage
     *
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/it/corsojavadb/ui/pizza_main.fxml"));

        primaryStage.setTitle("Gestione Pizze - Hibernate + JavaFX");

        //Creo una nuova scene con il contenuto del file FXML
        primaryStage.setScene(new Scene(root));

        //imposto altezza e larghezza finestra
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);

        //apro la finestra
        primaryStage.show();

    }
}
