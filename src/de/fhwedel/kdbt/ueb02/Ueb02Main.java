package de.fhwedel.kdbt.ueb02;

import java.util.Arrays;
import java.util.List;

import com.db4o.query.Predicate;

import de.fhwedel.kdbt.PilotMain;
import de.fhwedel.kdbt.ueb01.Pilot;

public class Ueb02Main extends PilotMain {

    /** Name der Datenbank */
    private static final String DB_NAME = "de.fhwedel.kdbt.ueb02.ueb02-db";

    /**
     * Main der zweiten Übung Konzepte der Datenbanktechnologie.
     * 
     * @param args
     */
    public static void main(String[] args) {
        PILOTS = Arrays.asList(
                new Pilot("Michael Schuhmacher", 1),
                new Pilot("Rubens Barricello", 0),
                new Pilot("Jenson Button", 90),
                new Pilot("Nick Hiedfeld", 100)
                );
        refreshDb(DB_NAME);
        try {
            createPilots();
            readPilots();
            getAllPilotsWithMinPoints100();
            getAllPilotsWith99To199PointsOrRubensBarrichello();
            getAllPilotsWithPoints_1_90_100();
        } finally {
            db.close();
        }
    }

    /**
     * Liest die gespeicherten Objekte aus der Datenbank.
     */
    private static void readPilots() {
        System.out.println("Gefundene Piloten:");
        List<Object> pilots = db.queryByExample(Pilot.class);
        pilots.forEach(System.out::println);
        System.out.println(SEPERATOR);
    }

    /**
     * Liefert alle Fahrer, deren Punktzahl 100 beträgt.
     * 
     * @return Liste der Piloten
     */
    private static List<Pilot> getAllPilotsWithMinPoints100() {
        System.out.println("Fahrer, deren Punktzahl 100 beträgt:");
        List<Pilot> pilots = db.query(new Predicate<Pilot>() {
            @Override
            public boolean match(Pilot pilot) {
                return pilot.getPoints() >= 100;
            }
        });
        pilots.forEach(System.out::println);
        System.out.println(SEPERATOR);
        return pilots;
    }

    /**
     * Liefert alle Fahrer, deren Punktzahl zwischen 99 und 199 Punkten liegt oder die Rubens
     * Barrichello heißen.
     * 
     * @return Liste der Piloten
     */
    private static List<Pilot> getAllPilotsWith99To199PointsOrRubensBarrichello() {
        System.out
                .println("Fahrer, deren Punktzahl zwischen 99 und 199 Punkten liegt oder die Rubens:");
        List<Pilot> pilots = db.query(new Predicate<Pilot>() {
            @Override
            public boolean match(Pilot pilot) {
                return pilot.getName().equals("Rubens Barricello")
                        || (pilot.getPoints() >= 99 && pilot.getPoints() <= 199);
            }

        });
        pilots.forEach(System.out::println);
        System.out.println(SEPERATOR);
        return pilots;
    }

    /**
     * Liefert alle Fahrer, deren Punktzahl einer der Punktzahlliste entspricht.
     * 
     * @return Liste der Piloten
     */
    private static List<Pilot> getAllPilotsWithPoints(List<Integer> points) {
        System.out
                .println("Fahrer, deren Punktzahl in: " + points.toString());
        List<Pilot> pilots = db.query(new Predicate<Pilot>() {
            @Override
            public boolean match(Pilot pilot) {
                return points.contains(pilot.getPoints());
            }

        });
        pilots.forEach(System.out::println);
        System.out.println(SEPERATOR);
        return pilots;
    }

    /**
     * Liefert alle Piloten mit der Punktzahl 1, 90 oder 100.
     * 
     * @return Liste der Piloten
     */
    private static List<Pilot> getAllPilotsWithPoints_1_90_100() {
        return getAllPilotsWithPoints(Arrays.asList(1, 90, 100));
    }

}
