package gui;

import java.awt.event.MouseListener;
import javax.swing.JButton;

public class Field extends JButton {

    private boolean containsShip;
    private boolean isHit;
    private int row;
    private int column;

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

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
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
