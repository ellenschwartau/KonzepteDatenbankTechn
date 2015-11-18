package de.fhwedel.kdbt.db4o;

public class Car {

    private String model;
    private Pilot pilot;

    public Car(String model, Pilot pilot) {
        this.model = model;
        this.pilot = pilot;
    }

    public Car(String model) {
        this(model, null);
    }

    public String getModel() {
        return model;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public String toString() {
        return model + "/" + pilot;
    }

}
