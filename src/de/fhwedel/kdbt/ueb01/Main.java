/**
 * 
 */
package de.fhwedel.kdbt.ueb01;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

/**
 * Übung 1 Konzepte der Datenbanktechnologie.
 * 
 * @author Ellen
 * 
 */
public class Main {

    /**
     * Datenbank Container
     */
    private static ObjectContainer db;

    /**
     * Liste der Piloten
     */
    private static final List<Object> PILOTS = Arrays.asList(
            new Pilot("Michael Schuhmacher", 0),
            new Pilot("Rubens Barricello", 0),
            new Pilot("Jenson Button", 0),
            new Pilot("Nick Hiedfeld", 0)
            );

    /**
     * Führt die Datenbankoperationen der ersten Aufgabe aus.
     * 
     * @param args
     */
    public static void main(String[] args) {
        new File("de.fhwedel.kdbt.ueb01-db").delete();
        db = Db4oEmbedded.openFile("de.fhwedel.kdbt.ueb01-db");
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
     * Erstellt die Piloten und speichert diese in der Datenbank.
     */
    private static void createPilots() {
        create(PILOTS);
        System.out.println("-------------");
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
     * Speichert eine Liste an Objekten in die Datenbank.
     * 
     * @param objects
     */
    private static void create(List<Object> objects) {
        objects.forEach(db::store);
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
