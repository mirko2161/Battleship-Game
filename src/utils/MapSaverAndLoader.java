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
        map.setListOfShips(list);

        repaintMap();

        for (Field[] gridField : map.getGridFields()) {
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
            }
        }
    }

    public void repaintMap() {
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                if (map.getSavedStateOfMap()[row][column] && map.getNameOfMap().equals("user")) {
                    map.getGridFields()[row][column].setBackground(Color.CYAN);
                } else {
                    map.getGridFields()[row][column].setBackground(Color.BLUE);
                }
            }
        }
        map.setPotencialStateOfMap(map.getSavedStateOfMap());
    }

}
