package gui;

import java.awt.event.MouseListener;
import javax.swing.JButton;

public class Field extends JButton {

    private boolean containsShip;
    private boolean isHit;
    private final int row;
    private final int column;

    public Field(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public boolean isWithShip() {
        return containsShip;
    }

    public void setContainsShip(boolean containsShip) {
        this.containsShip = containsShip;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public void removeAllMouseListeners() {
        for (MouseListener listener : getMouseListeners()) {
            removeMouseListener(listener);
        }
    }
}
