package gui;

import javax.swing.JLabel;

public class ShipLabel extends JLabel {

    private int numOfShipsRemaining;
    private final String name;

    public ShipLabel(int numOfShipsRemaining, String name) {
        super(numOfShipsRemaining + name);
        this.name = name;
        this.numOfShipsRemaining = numOfShipsRemaining;
    }

    public void shipLost() {
        setText(--numOfShipsRemaining + name);
        revalidate();
        repaint();
    }

    public int getNumOfShipsRemaining() {
        return numOfShipsRemaining;
    }

    public void setNumOfShipsRemaining(int numOfShipsRemaining) {
        this.numOfShipsRemaining = numOfShipsRemaining;
        setText(numOfShipsRemaining + name);
    }

}
