package gui;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import model.BattlefieldMap;
import utils.FieldAdapter;

public class BattlefieldMapGUI extends JPanel {

    private static final int ROW = 10;
    private static final int COLUMN = 10;

    private final FieldGUI[][] gridFields;

    private BattlefieldMap map;

    public BattlefieldMapGUI(String name) {

        GridLayout layout = new GridLayout(ROW, COLUMN);
        setLayout(layout);

        map = new BattlefieldMap(name, this);

        this.gridFields = new FieldGUI[ROW][COLUMN];
        FieldAdapter fieldAdapter = new FieldAdapter(map);

        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN; column++) {
                gridFields[row][column] = new FieldGUI(row, column);
                FieldGUI fieldGUI = gridFields[row][column];
                fieldGUI.addMouseListener(fieldAdapter);
                fieldGUI.setBackground(Color.BLUE);
                this.add(fieldGUI);
            }
        }
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void removeButtonActions() {
        for (FieldGUI[] fieldsGUI : gridFields) {
            for (FieldGUI fieldGUI : fieldsGUI) {
                fieldGUI.removeAllMouseListeners();
            }
        }
    }

    public FieldGUI[][] getGridFields() {
        return gridFields;
    }

    public BattlefieldMap getMap() {
        return map;
    }

}
