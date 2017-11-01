package model;

import gui.BattlefieldMap;
import java.io.Serializable;
import java.util.List;

public class Ship implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private boolean alive; // are all the field of the ship unhit
    private final int length;
    private final List<Integer> coordinates;

    public Ship(List<Integer> coordinates, String name, int length) {
        this.name = name;
        this.alive = true; // always alive at construction
        this.length = length;
        this.coordinates = coordinates;
    }

    public boolean checkIfShipIsDestroyed(BattlefieldMap map) {
        for (int i = 0; i < coordinates.size(); i += 2) {
            int row = coordinates.get(i);
            int column = coordinates.get(i + 1);
            if (!map.getGridFields()[row][column].isHit()) {
                return false; // as soon as there's an unhit field
            }
        }
        this.setAlive(false); // RIP
        return true;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getLength() {
        return length;
    }

    public void setCoordinates(int row, int column) {
        coordinates.add(row);
        coordinates.add(column);
    }

}
