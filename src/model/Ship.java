package model;

import java.io.Serializable;
import java.util.List;

public class Ship implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Integer> coordinates;
    private boolean alive; // are all the field of the ship unhit
    private final String name;

    public Ship(List<Integer> coordinates, boolean alive, String name) {
        this.coordinates = coordinates;
        this.alive = alive;
        this.name = name;
    }

    public List<Integer> getCoordinates() {
        return coordinates;
    }

    public boolean isAlive() {
        return alive;
    }

    public String getName() {
        return name;
    }

    public void setCoordinates(int row, int column) {
        coordinates.add(row);
        coordinates.add(column);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

}
