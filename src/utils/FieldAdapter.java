package utils;

import gui.BattlefieldMap;
import gui.Field;
import gui.FieldGUI;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.SwingUtilities;

public class FieldAdapter extends MouseAdapter {

    private final BattlefieldMap map;

    public FieldAdapter(BattlefieldMap map) {
        this.map = map;
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        if (map.getNameOfMap().equals("user")) {
            playerButtonsBehaviour(event);
        } else if (map.getNameOfMap().equals("enemy")) {
            enemyButtonsBehaviour(event);
        }
    }

    private void playerButtonsBehaviour(MouseEvent event) {
        int row = ((FieldGUI) event.getComponent()).getField().getRow();
        int column = ((FieldGUI) event.getComponent()).getField().getColumn();
        if (map.isShipsPlaced()) { // if game can begin, change behaviour of fields
            showMessageDialog(map.getMapGUI(), "You can't fire upon your own fleet!", "That would be treason",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (SwingUtilities.isLeftMouseButton(event)) {
                map.placeShips(row, column, true); // horizontal placement
            } else if (SwingUtilities.isRightMouseButton(event)) {
                map.placeShips(row, column, false); // vertical placement
            }
        }
    }

    private void enemyButtonsBehaviour(MouseEvent event) {
        Field field = ((FieldGUI) event.getComponent()).getField();
        if (map.isShipsPlaced()) {
            if (field.isHit()) {
                showMessageDialog(map.getMapGUI(), "You can fire in the same place, your crew will laught "
                        + "at you...", "Not allowed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                map.fire(field.getRow(), field.getColumn());
            }
        } else {
            showMessageDialog(map.getMapGUI(), "You can't place the enemy ships, that would be cheating!",
                    "Not allowed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}
