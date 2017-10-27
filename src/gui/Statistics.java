package gui;

import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Statistics extends JPanel {

    protected final JLabel statLabel;
    protected final JLabel carrierLabel;
    protected final JLabel battleshipLabel;
    protected final JLabel cruiserLabel;
    protected final JLabel submarineLabel;
    protected final JLabel destroyerLabel;
    protected JLabel notificationLabel;

    protected BattlefieldMap accompanyingMap;

    public Statistics() { // TODO: add better alignment, better look and position
        GridLayout experimentLayout = new GridLayout(7, 1);
        setLayout(experimentLayout);
        // TODO: myb add behaviour for num of ships remaining to place, then swich to num of alive ships

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

    public void addNotificationLabel() {
        notificationLabel = new JLabel("Waiting for ship deployment...");
        notificationLabel.setHorizontalAlignment(JLabel.CENTER);
        notificationLabel.setFont(notificationLabel.getFont().deriveFont(14f));
        notificationLabel.setBorder(BorderFactory.createEtchedBorder());
        add(notificationLabel);
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

    public void updateNotificationLabel(String newLabel) {
        notificationLabel.setText(newLabel);
    }

    public void setAccompanyingMap(BattlefieldMap accompanyingMap) {
        this.accompanyingMap = accompanyingMap;
    }

}
