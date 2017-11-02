package utils;

import gui.BattlefieldMap;
import gui.Field;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import model.Ship;

public class MapSaverAndLoader {

    private final BattlefieldMap map;

    public MapSaverAndLoader(BattlefieldMap map) {
        this.map = map;
    }

    public void saveMap(ObjectOutputStream os) throws IOException {
        os.writeObject(map.getSavedStateOfMap());
        os.writeObject(map.getListOfShips());

        for (Field[] gridField : map.getGridFields()) {
            for (Field field : gridField) {
                os.writeObject(field.isWithShip());
                os.writeObject(field.isHit());
            }
        }
    }

    public void loadMap(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        map.setSavedStateOfMap((boolean[][]) ois.readObject());
        @SuppressWarnings("unchecked")
        List<Ship> list = (List<Ship>) ois.readObject();

        repaintMap();

        for (Field[] gridField : map.getGridFields()) {
            outer:
            for (Field field : gridField) {
                field.setContainsShip((Boolean) ois.readObject());
                field.setIsHit((Boolean) ois.readObject());

                if (field.isHit()) {
                    if (field.isWithShip()) {
                        field.setBackground(Color.RED);
                    } else {
                        field.setBackground(Color.GRAY);
                    }
                }

                // TODO: remove terible hack, rework design
                // because Ship fields are diff objects after load and won't be hit when fire is called
                if (field.isWithShip()) {
                    for (Ship ship : list) {
                        // can't use for-each because can't asign to for parameter
                        for (int i = 0; i < ship.getCoordinates().size(); i++) {
                            if (field.getRow() == ship.getCoordinates().get(i).getRow()
                                    && field.getColumn() == ship.getCoordinates().get(i).getColumn()) { // ughh
                                ship.getCoordinates().set(i, field);
                                continue outer;
                            }
                        }
                    }
                }
            }
        }
        map.setListOfShips(list);
    }

    private void repaintMap() {
        Field[][] fields = map.getGridFields();
        for (int row = 0; row < fields.length; row++) {
            for (int column = 0; column < fields[row].length; column++) {
                if (map.getSavedStateOfMap()[row][column] && map.getNameOfMap().equals("user")) {
                    fields[row][column].setBackground(Color.CYAN);
                } else {
                    fields[row][column].setBackground(Color.BLUE);
                }
            }
        }
        map.setPotencialStateOfMap(map.getSavedStateOfMap());
    }

}
