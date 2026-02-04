package it.corsojavadb.app.setup;

/**
 * Interfaccia base per le migrazioni del database.
 *
 * La useremo per definire il contratto pubblico sulle classi migrazione(implements)
 * La useremo anche per descrivere il tipo di dato a java in fase di gestione delle migrazioni
 * */
public interface Migration {

    /**
     * Restituisce la versione di questa migrazione
     * Formato V001, V002, ecc...
     * @return versione della migrazione
     */
    String getVersion();

    /**
     * Restituisce la descrizione di questa migrazione
     * @return descrizione del cambiamento
     */
    String getDescription();

    /**
     * Esegue la migrazione
     * Viene chiamato quando una migrazione non è ancora stata eseguita
     * @throws Exception se l'esecuzione fallisce
     */
    void up() throws Exception;

    /**
     * Annulla la migrazione
     * Viene usato rararmente, principalmente per testing o rollback
     * @throws Exception se l'annullamento fallisce
     */
    void down() throws Exception;

}
