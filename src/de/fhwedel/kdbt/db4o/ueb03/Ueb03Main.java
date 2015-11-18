package de.fhwedel.kdbt.db4o.ueb03;

import com.db4o.Db4oEmbedded;

import de.fhwedel.kdbt.db4o.Car;
import de.fhwedel.kdbt.db4o.CarMain;

/**
 * Main Klasse der dritten Datenbanken Übung.
 * 
 * @author Ellen
 *
 */
public class Ueb03Main extends CarMain {

    /** Name der Datenbank */
    private static final String DB_NAME = "de.fhwedel.kdbt.ueb02.ueb03-db";

    public static void main(String[] args) {
        refreshDb(DB_NAME);
        try {
            createCars();
            readObjects(Car.class, "Gespeicherte Autos: ");
            System.out
                    .println("\nÄndern Sie ein Pilot Objekt im Speicher und schreiben \n"
                            + "Sie dann das zugehörige Car-Objekt. Beobachten Sie, dass\n"
                            + "das Pilot Objekt zwar im Speicher, aber nicht in der Datenbank\n"
                            + "geändert ist.\n");
            manipulatePilot();
            System.out.println("\nWiederholen mit cascadeOnUpdate\n");
            manipulatePilotWithCascadeOnUpdate();
            System.out
                    .println("\nLöschen Sie bitte ein Car-Objekt und beobachten Sie, dasss das \n"
                            + "zugehörige Pilot Objekt im Speicher und in der Datenbank erhalten bleibt.\n");
            deleteCar();
            System.out.println("\nWiederholen mit cascadeOnDelete\n");
            deleteCarWithOnDeleteCascade();
        } finally {
            db.close();
        }
    }

    private static void manipulatePilot() {
        System.out.println("Manipulierte Schuhmacher... ");
        Car car = (Car) db.query(Car.class).get(0);
        car.getPilot().addPoints(100);
        db.store(car);
        db.close();
        db = Db4oEmbedded.openFile(DB_NAME);
        System.out.println("Schuhmacher im Speicher: " + SCHUHMACHER);
        readObjects(Car.class, "Autos aus der Datenbank: ");
    }

    private static void manipulatePilotWithCascadeOnUpdate() {
        System.out.println("Erstelle neue DB mit cascadeOnUpdate für Cars...");
        createDbCascadeOnUpdate(DB_NAME, Car.class);
        createCars();
        manipulatePilot();
    }

    private static void deleteCar() {
        System.out.println("Lösche den Ferrari... ");
        db.delete(db.queryByExample(FERRARI).get(0));
        readObjects(Car.class, "Gespeicherte Autos: ");
        System.out.println("Pilot im Speicher: " + SCHUHMACHER);
        System.out.println("Passende Piloten aus DB: ");
        db.queryByExample(SCHUHMACHER).forEach(System.out::println);
        System.out.println(SEPERATOR);
    }

    private static void deleteCarWithOnDeleteCascade() {
        createDbCascadeOnDelete(DB_NAME, Car.class);
        createCars();
        deleteCar();
    }

}
