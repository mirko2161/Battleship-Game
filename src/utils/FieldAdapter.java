package utils;

import gui.BattlefieldMap;
import gui.Field;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.SwingUtilities;

public class FieldAdapter extends MouseAdapter {

    BattlefieldMap map;

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
        int row = ((Field) event.getComponent()).getRow();
        int column = ((Field) event.getComponent()).getColumn();
        if (map.isShipsPlaced()) { // if game can begin, change behaviour of fields
            showMessageDialog(map, "You can't fire upon your own fleet!", "Not allowed",
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
        Field field = (Field) event.getComponent();
        if (map.isShipsPlaced()) {
            if (field.isHit()) {
                showMessageDialog(map, "You can fire in the same place, your crew will laught "
                        + "at you...", "Not allowed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                map.fire(field.getRow(), field.getColumn());
            }
        } else {
            showMessageDialog(map, "You can't place the enemy ships, that would be cheating!",
                    "Not allowed", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void setMap(BattlefieldMap map) {
        this.map = map;
    }

}
