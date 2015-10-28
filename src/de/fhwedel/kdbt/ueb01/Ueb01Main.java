/**
 * 
 */
package de.fhwedel.kdbt.ueb01;

import java.util.List;

import de.fhwedel.kdbt.Pilot;
import de.fhwedel.kdbt.PilotMain;

/**
 * Übung 1 Konzepte der Datenbanktechnologie.
 * 
 * @author Ellen
 * 
 */
public class Ueb01Main extends PilotMain {

    /** Name der Datenbank */
    private static final String DB_NAME = "de.fhwedel.kdbt.ueb01.ueb01-db";

    /**
     * Führt die Datenbankoperationen der ersten Aufgabe aus.
     * 
     * @param args
     */
    public static void main(String[] args) {
        refreshDb(DB_NAME);
        try {
            createPilots();
            readPilots();
            update(PILOTS.get(0));
            delete();
        } finally {
            db.close();
        }
    }

    /**
     * Liest die gespeicherten Objekte aus der Datenbank.
     */
    private static void readPilots() {
        System.out.println("Gefundene Piloten:");
        List<Object> pilots = db.queryByExample(new Pilot(null, 0));
        pilots.forEach(System.out::println);
        System.out.println("-------------");
    }

    /**
     * Aktualisiert Objekte in der Datenbank.
     */
    private static void update(Object refPilot) {
        System.out.println("Aktualisiere Pilot: " + refPilot.toString());
        Pilot pilot = (Pilot) db.queryByExample(refPilot).get(0);
        pilot.addPoints(5);
        System.out.println("Manipulierter Pilot: " + pilot);
        db.store(pilot);
        // Ergebnis prüfen:
        System.out.println("Ergebnis prüfen:");
        readPilots();
    }

    /**
     * Löscht Objekte aus der Datenbank.
     */
    private static void delete() {
        System.out.println("Alle Piloten aus der DB löschen.");
        PILOTS.forEach(db::delete);
        readPilots();
    }
}
