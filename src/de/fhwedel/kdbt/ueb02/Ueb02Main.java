package de.fhwedel.kdbt.ueb02;

import de.fhwedel.kdbt.PilotMain;

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

        } finally {
            db.close();
        }
    }

}
