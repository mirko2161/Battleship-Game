package model;

import gui.BattlefieldMap;
import gui.Field;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ship implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private boolean alive; // are all the fields of the ship unhit
    private final int length;
    private final List<Field> coordinates;

    public Ship(String name, int length) {
        this.name = name;
        this.alive = true; // always alive at construction
        this.length = length;
        this.coordinates = new ArrayList<>();
    }

    public void saveShipPosition(BattlefieldMap map) {
        for (int row = 0; row < map.getSavedStateOfMap().length; row++) {
            for (int column = 0; column < map.getSavedStateOfMap()[row].length; column++) {
                // diff between two states is new ship position
                if (map.getSavedStateOfMap()[row][column] != map.getPotencialStateOfMap()[row][column]) {
                    coordinates.add(map.getMapGUI().getGridFields()[row][column].getField());
                }
            }
        }
    }

    public boolean checkIfShipIsDestroyed() {
        for (Field field : coordinates) {
            if (!field.isHit()) {
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

    public List<Field> getCoordinates() {
        return coordinates;
    }

}
