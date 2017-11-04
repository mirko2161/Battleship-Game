package utils;

import gui.FieldGUI;
import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import model.BattlefieldMap;
import model.Field;
import model.Ship;

public class MapSaverAndLoader {

    private final BattlefieldMap map;

    public MapSaverAndLoader(BattlefieldMap map) {
        this.map = map;
    }

    public void saveMap(ObjectOutputStream os) throws IOException {
        os.writeObject(map.getSavedStateOfMap());
        os.writeObject(map.getListOfShips());

        for (FieldGUI[] gridField : map.getMapGUI().getGridFields()) {
            for (FieldGUI fieldGUI : gridField) {
                os.writeObject(fieldGUI.getField());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void loadMap(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        map.setSavedStateOfMap((boolean[][]) ois.readObject());
        map.setListOfShips((List<Ship>) ois.readObject());

        repaintMap();

        for (FieldGUI[] gridField : map.getMapGUI().getGridFields()) {
            for (FieldGUI fieldGUI : gridField) {
                Field field = ((Field) ois.readObject());
                fieldGUI.setField(field);

                if (field.isHit()) {
                    if (field.isWithShip()) {
                        fieldGUI.setBackground(Color.RED);
                    } else {
                        fieldGUI.setBackground(Color.GRAY);
                    }
                }
            }
        }
    }

    private void repaintMap() {
        FieldGUI[][] fieldsGUI = map.getMapGUI().getGridFields();
        for (int row = 0; row < fieldsGUI.length; row++) {
            for (int column = 0; column < fieldsGUI[row].length; column++) {
                if (map.getSavedStateOfMap()[row][column] && map.getNameOfMap().equals("user")) {
                    fieldsGUI[row][column].setBackground(Color.CYAN);
                } else {
                    fieldsGUI[row][column].setBackground(Color.BLUE);
                }
            }
        }
        map.setPotencialStateOfMap(map.getSavedStateOfMap());
    }

}
