package de.fhwedel.kdbt.db4o;

public class Pilot {
    private String m_name;
    private int m_points;

    /** Name des Attributs der Punkte */
    public static final String PROPERTY_POINTS = "m_points";

    /** Name des Attributs Name */
    public static final String PROPERTY_NAME = "m_name";

    public Pilot(final String name, final int points) {
        m_name = name;
        m_points = points;
    }

    public int getPoints() {
        return m_points;
    }

    public void addPoints(final int points) {
        m_points += points;
    }

    public String getName() {
        return m_name;
    }

    public String toString() {
        return m_name + "/" + m_points;
    }
}
