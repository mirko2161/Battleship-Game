package gui;

import javax.swing.JLabel;

public class ShipLabel extends JLabel {

    private int numOfShipsRemaining;
    private String name;

    public ShipLabel(int numOfShipsRemaining, String name) {
        super(numOfShipsRemaining + name);
        this.name = name;
        this.numOfShipsRemaining = numOfShipsRemaining;
    }

    public void shipLost() {
        if (numOfShipsRemaining > 0) {
            setText(--numOfShipsRemaining + name);
            revalidate();
            repaint();
        }
    }

}
