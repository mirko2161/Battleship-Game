package gui;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Statistics extends JPanel {

    private final JLabel statLabel;
    private final JLabel carrierLabel;
    private final JLabel battleshipLabel;
    private final JLabel cruiserLabel;
    private final JLabel submarineLabel;
    private final JLabel destroyerLabel;

    private BattlefieldMap accompanyingMap;

    public Statistics() { // TODO: add better alignment, better look and position
        GridLayout experimentLayout = new GridLayout(7, 1);
        setLayout(experimentLayout);
        // TODO: add behaviour for num of ships remaining to place, then swich to num of alive ships

        statLabel = new JLabel("Number of ships remaining:");
        carrierLabel = new ShipLabel(1, " x Carrier");
        battleshipLabel = new ShipLabel(2, " x Battleship");
        cruiserLabel = new ShipLabel(3, " x Cruiser");
        submarineLabel = new ShipLabel(4, " x Submarine");
        destroyerLabel = new ShipLabel(5, " x Destroyer");

        statLabel.setHorizontalAlignment(JLabel.CENTER);
        carrierLabel.setHorizontalAlignment(JLabel.CENTER);
        battleshipLabel.setHorizontalAlignment(JLabel.CENTER);
        cruiserLabel.setHorizontalAlignment(JLabel.CENTER);
        submarineLabel.setHorizontalAlignment(JLabel.CENTER);
        destroyerLabel.setHorizontalAlignment(JLabel.CENTER);

        Font font = statLabel.getFont().deriveFont(14f);
        statLabel.setFont(font);
        carrierLabel.setFont(font);
        battleshipLabel.setFont(font);
        cruiserLabel.setFont(font);
        submarineLabel.setFont(font);
        destroyerLabel.setFont(font);

        add(statLabel);
        add(carrierLabel);
        add(battleshipLabel);
        add(cruiserLabel);
        add(submarineLabel);
        add(destroyerLabel);
    }

    public void updateShipLabels(String shipName) {
        switch (shipName) {
            case "Carrier":
                ((ShipLabel) carrierLabel).shipLost();
                break;
            case "Battleship":
                ((ShipLabel) battleshipLabel).shipLost();
                break;
            case "Cruiser":
                ((ShipLabel) cruiserLabel).shipLost();
                break;
            case "Submarine":
                ((ShipLabel) submarineLabel).shipLost();
                break;
            case "Destroyer":
                ((ShipLabel) destroyerLabel).shipLost();
                break;
        }
    }

    public void setAccompanyingMap(BattlefieldMap accompanyingMap) {
        this.accompanyingMap = accompanyingMap;
    }

    public BattlefieldMap getAccompanyingMap() {
        return accompanyingMap;
    }

}
