package de.fhwedel.kdbt;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

public class PilotMain {

    /**
     * Datenbank Container
     */
    protected static ObjectContainer db;

    /** Trennzeile für die Konsolenausgabe */
    protected static final String SEPERATOR = "-------------";

    /**
     * Liste der default Piloten
     */
    protected static List<Object> PILOTS = Arrays.asList(
            new Pilot("Michael Schuhmacher", 0),
            new Pilot("Rubens Barricello", 0),
            new Pilot("Jenson Button", 0),
            new Pilot("Nick Hiedfeld", 0)
            );

    /**
     * Löscht die alte Datenbank und erstellt eine neue.
     * 
     * @param dbName
     */
    protected static void refreshDb(String dbName) {
        new File(dbName).delete();
        db = Db4oEmbedded.openFile(dbName);
    }

    /**
     * Erstellt die Piloten und speichert diese in der Datenbank.
     */
    protected static void createPilots() {
        System.out.println("Speichere die gewünschten Piloten...");
        create(PILOTS);
        System.out.println("-------------");
    }

    /**
     * Speichert eine Liste an Objekten in die Datenbank.
     * 
     * @param objects
     */
    private static void create(List<Object> objects) {
        objects.forEach(db::store);
    }

    /**
     * Liefert alle Piloten, die ein bestimmtes Constraint erfüllen.
     * 
     * @param predicate
     * @param msg
     */
    protected static void getAllPilotsWithPredicate(Predicate<Pilot> predicate, String msg) {
        System.out.println(msg);
        List<Pilot> pilots = db.query(predicate);
        pilots.forEach(System.out::println);
        System.out.println(SEPERATOR);
    }

    /**
     * Führt eine query aus und printed die ermittelten Piloten.
     * 
     * @param query Abfrage
     * @param msg Beschreibung
     */
    protected static void getAllPilotsWithSodaQuery(Query query, String msg) {
        System.out.println(msg);
        List<Pilot> pilots = query.execute();
        pilots.forEach(System.out::println);
        System.out.println(SEPERATOR);
    }

}
