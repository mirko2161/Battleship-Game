package gui;

import java.io.Serializable;

public class Field implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
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

}
