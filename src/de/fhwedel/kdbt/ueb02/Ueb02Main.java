package de.fhwedel.kdbt.ueb02;

import static de.fhwedel.kdbt.Pilot.PROPERTY_NAME;
import static de.fhwedel.kdbt.Pilot.PROPERTY_POINTS;
import static de.fhwedel.kdbt.QueryBuilder.createQuery;

import java.util.Arrays;
import java.util.List;

import com.db4o.query.Constraint;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import de.fhwedel.kdbt.Pilot;
import de.fhwedel.kdbt.PilotMain;
import de.fhwedel.kdbt.QueryBuilder.GivenConstraint;

public class Ueb02Main extends PilotMain {

    /** Name der Datenbank */
    private static final String DB_NAME = "de.fhwedel.kdbt.ueb02.ueb02-db";

    /**
     * Main der zweiten Übung Konzepte der Datenbanktechnologie.
     * 
     * @param args
     */
    public static void main(String[] args) {
        refreshDb(DB_NAME);
        try {
            createPilots();
            readPilots();
            // QBE
            getAllPilotsWithMinPoints100();
            getAllPilotsWith99To199PointsOrRubensBarrichello();
            getAllPilotsWithPoints_1_90_100();
            // SODA
            getAllPilotsWithMinPoints100_SODA();
            getAllPilotsWith99To199PointsOrRubensBarrichello_SODA();
            getAllPilotsWithPoints_1_90_100_SODA();
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
    private static void getAllPilotsWithMinPoints100() {
        getAllPilotsWithPredicate(new Predicate<Pilot>() {
            @Override
            public boolean match(Pilot pilot) {
                return pilot.getPoints() >= 100;
            }
        }, "Fahrer, deren Punktzahl 100 beträgt:");
    }

    /**
     * Liefert alle Fahrer, deren Punktzahl zwischen 99 und 199 Punkten liegt oder die Rubens
     * Barrichello heißen.
     * 
     * @return Liste der Piloten
     */
    private static void getAllPilotsWith99To199PointsOrRubensBarrichello() {
        getAllPilotsWithPredicate(new Predicate<Pilot>() {
            @Override
            public boolean match(Pilot pilot) {
                return pilot.getName().equals("Rubens Barricello")
                        || (pilot.getPoints() >= 99 && pilot.getPoints() <= 199);
            }
        }, "Fahrer, deren Punktzahl zwischen 99 und 199 Punkten liegt oder die Rubens:");
    }

    /**
     * Liefert alle Fahrer, deren Punktzahl einer der Punktzahlliste entspricht.
     * 
     * @return Liste der Piloten
     */
    private static void getAllPilotsWithPoints(List<Integer> points) {
        getAllPilotsWithPredicate(new Predicate<Pilot>() {
            @Override
            public boolean match(Pilot pilot) {
                return points.contains(pilot.getPoints());
            }
        }, "Fahrer, deren Punktzahl in: " + points.toString());
    }

    /**
     * Liefert alle Piloten mit der Punktzahl 1, 90 oder 100.
     * 
     * @return Liste der Piloten
     */
    private static void getAllPilotsWithPoints_1_90_100() {
        getAllPilotsWithPoints(Arrays.asList(1, 90, 100));
    }

    /**
     * Formulieren Sie bitte die Bedingungen (a) und (b) aus Aufgabe 2 in Form von SODA–Anfragen.
     * (b) F¨ur Bedingung 2(c) generieren Sie bitte an Hand der Liste points eine passende Anfrage
     * (mit enstprechend vielen or–Teilen). (c) Experimentieren Sie bitte auch mit eigenen
     * SODA–Anfragen, machen Sie sich bitte mit den Constraint-Methoden vertraut
     */

    /**
     * SODA-Anfrage Liefert alle Fahrer, deren Punktzahl 100 beträgt.
     * 
     * @return Liste der Piloten
     */
    private static void getAllPilotsWithMinPoints100_SODA() {
        getAllPilotsWithSodaQuery(
                createQuery(db, Pilot.class, new GivenConstraint(PROPERTY_POINTS, 100)),
                "SODA: Fahrer, deren Punktzahl 100 beträgt:");
    }

    /**
     * SODA-Anfrage Liefert alle Fahrer, deren Punktzahl zwischen 99 und 199 Punkten liegt oder die
     * Rubens Barrichello heißen.
     * 
     * @return Liste der Piloten
     */
    private static void getAllPilotsWith99To199PointsOrRubensBarrichello_SODA() {
        Query query = createQuery(db, Pilot.class);
        query.descend(PROPERTY_POINTS).constrain(98).greater();
        Constraint constraint = query.descend(PROPERTY_POINTS).constrain(200).smaller();
        constraint = query.descend(PROPERTY_NAME).constrain("Rubens Barricello").or(constraint);
        getAllPilotsWithSodaQuery(query,
                "SODA: Fahrer, deren Punktzahl zwischen 99 und 199 Punkten liegt oder die Rubens:");
    }

    /**
     * SODA-Anfrage Liefert alle Fahrer, deren Punktzahl einer der Punktzahlliste entspricht.
     * 
     * @return Liste der Piloten
     */
    private static void getAllPilotsWithPoints_SODA(List<Integer> points) {
        getAllPilotsWithSodaQuery(createPilotQueryForPoints(points),
                "SODA: Fahrer, deren Punktzahl in: " + points.toString());
    }

    /**
     * Erstellt die DB-Query zum Auslesen aller Piloten mit einer Punktzahl aus der übergebenen
     * Liste.
     * 
     * @param points gesuchte Punktzahlen
     * @return Query
     */
    private static Query createPilotQueryForPoints(List<Integer> points) {
        Query query = createQuery(db, Pilot.class);
        if (!points.isEmpty()) {
            Constraint constraint = query.descend(PROPERTY_POINTS).constrain(points.get(0));
            for (Integer point : points.subList(1, points.size())) {
                constraint = query.descend(PROPERTY_POINTS).constrain(point).or(constraint);
            }
        }
        return query;
    }

    /**
     * SODA-Anfrage Liefert alle Piloten mit der Punktzahl 1, 90 oder 100.
     * 
     * @return Liste der Piloten
     */
    private static void getAllPilotsWithPoints_1_90_100_SODA() {
        getAllPilotsWithPoints_SODA(Arrays.asList(1, 90, 100));
    }
}
