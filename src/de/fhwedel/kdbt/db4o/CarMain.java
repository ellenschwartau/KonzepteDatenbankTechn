package de.fhwedel.kdbt.db4o;

import java.util.Arrays;
import java.util.List;

public class CarMain extends PilotMain {

    protected static final Car FERRARI = new Car("Ferrari", SCHUHMACHER);
    protected static final Car LAMBURGINI = new Car("Lamburgini", SCHUHMACHER);

    /**
     * Liste der default Piloten
     */
    protected static List<Object> CARS = Arrays.asList(
            FERRARI,
            LAMBURGINI
            );

    /**
     * Erstellt die Piloten und speichert diese in der Datenbank.
     */
    protected static void createCars() {
        System.out.println("Speichere die gewünschten Cars...");
        create(CARS);
    }

}
