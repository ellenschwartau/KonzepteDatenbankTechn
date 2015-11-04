package de.fhwedel.kdbt;

import java.util.Arrays;
import java.util.List;

import com.db4o.query.Predicate;
import com.db4o.query.Query;

public class PilotMain extends DBUtils {

    protected static final Pilot SCHUHMACHER = new Pilot("Michael Schuhmacher", 0);
    protected static final Pilot BARRICELLO = new Pilot("Rubens Barricello", 0);
    protected static final Pilot BUTTON = new Pilot("Jenson Button", 0);
    protected static final Pilot HIEDFELD = new Pilot("Nick Hiedfeld", 0);
    protected static final Pilot EINS = new Pilot("Super Eins", 1);
    protected static final Pilot NEUNZIG = new Pilot("Super 90", 90);
    protected static final Pilot HUNDERT = new Pilot("Super 100", 100);

    /**
     * Liste der default Piloten
     */
    protected static List<Object> PILOTS = Arrays.asList(
            SCHUHMACHER,
            BARRICELLO,
            BUTTON,
            HIEDFELD,
            EINS,
            NEUNZIG,
            HUNDERT
            );

    /**
     * Erstellt die Piloten und speichert diese in der Datenbank.
     */
    protected static void createPilots() {
        System.out.println("Speichere die gewünschten Piloten...");
        create(PILOTS);
        System.out.println("-------------");
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
