package model;

import gui.BattlefieldMap;
import java.io.Serializable;
import java.util.ArrayList;
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

    public boolean checkIfShipIsDestroyed(BattlefieldMap map) {
        ArrayList<Integer> shipCoordinates = (ArrayList<Integer>) this.getCoordinates();
        for (int i = 0; i < shipCoordinates.size(); i += 2) {
            int row = shipCoordinates.get(i);
            int column = shipCoordinates.get(i + 1);
            if (!map.getGridFields()[row][column].isHit()) {
                return false; // as soon as there's an unhit field
            }
        }
        this.setAlive(false); // RIP
        return true;
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
