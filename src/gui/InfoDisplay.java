package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoDisplay extends JPanel {

    private final JLabel infoLabel;
    private JLabel carrierLabel;
    private JLabel battleshipLabel;
    private JLabel cruiserLabel;
    private JLabel submarineLabel;
    private JLabel destroyerLabel;
    private final JLabel nameLabel;
    private final JLabel hitMissLabel;
    private final JLabel accuracyLabel;
    private int hits;
    private int misses;

    private BattlefieldMap accompanyingMap;

    public InfoDisplay() {
        this.setLayout(new BorderLayout()); // contains textAndStatsPanel and user buttons/notifications

        JPanel textAndStatsPanel = new JPanel(); // contains titlePanel, shipsPanel, and statsPanel
        textAndStatsPanel.setLayout(new BoxLayout(textAndStatsPanel, BoxLayout.PAGE_AXIS));

        // 1. panel
        JPanel titlePanel = new JPanel(); // if just label not centered
        infoLabel = new JLabel("Number of ships remaining to deploy:");
        Font font = infoLabel.getFont().deriveFont(26f);
        infoLabel.setFont(infoLabel.getFont().deriveFont(21f));
        titlePanel.add(infoLabel);
        titlePanel.setMaximumSize(titlePanel.getPreferredSize()); // after adding components
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // 2. panel
        JPanel shipsPanel = new JPanel();
        shipsPanel.setLayout(new BoxLayout(shipsPanel, BoxLayout.LINE_AXIS));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));

        carrierLabel = new ShipLabel(1, " x Carrier");
        battleshipLabel = new ShipLabel(2, " x Battleship");
        cruiserLabel = new ShipLabel(3, " x Cruiser");
        submarineLabel = new ShipLabel(4, " x Submarine");
        destroyerLabel = new ShipLabel(5, " x Destroyer");

        carrierLabel.setFont(font);
        battleshipLabel.setFont(font);
        cruiserLabel.setFont(font);
        submarineLabel.setFont(font);
        destroyerLabel.setFont(font);

        textPanel.add(carrierLabel);
        textPanel.add(battleshipLabel);
        textPanel.add(cruiserLabel);
        textPanel.add(submarineLabel);
        textPanel.add(destroyerLabel);

        shipsPanel.add(Box.createHorizontalGlue());
        shipsPanel.add(textPanel);
        shipsPanel.add(Box.createHorizontalGlue());

        // 3. panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.LINE_AXIS));

        nameLabel = new JLabel("");
        hitMissLabel = new JLabel("Hits/Misses: " + "0/0");
        accuracyLabel = new JLabel("Accuracy: " + "0%");

        Font newFont = hitMissLabel.getFont().deriveFont(14f);
        nameLabel.setFont(newFont);
        hitMissLabel.setFont(newFont);
        accuracyLabel.setFont(newFont);
        hitMissLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        accuracyLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        statsPanel.add(nameLabel);
        statsPanel.add(hitMissLabel);
        statsPanel.add(accuracyLabel);

        // adding
        textAndStatsPanel.add(titlePanel);
        textAndStatsPanel.add(shipsPanel);
        textAndStatsPanel.add(Box.createVerticalGlue());
        textAndStatsPanel.add(statsPanel);

        textAndStatsPanel.setPreferredSize(textAndStatsPanel.getMaximumSize());
        add(textAndStatsPanel, BorderLayout.CENTER);
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

    public void markHit() {
        hits++;
        updateStatLabels();
    }

    public void markMiss() {
        misses++;
        updateStatLabels();
    }

    private void updateStatLabels() {
        hitMissLabel.setText("Hits/Misses: " + hits + "/" + misses);
        int total = hits + misses;
        int accuracy = (int) ((double) hits / total * 100.0);
        accuracyLabel.setText("Accuracy: " + accuracy + "%");
    }

    public void saveInfo(ObjectOutputStream os) throws IOException {
        os.writeObject(hits);
        os.writeObject(misses);
        os.writeObject(((ShipLabel) carrierLabel).getNumOfShipsRemaining());
        os.writeObject(((ShipLabel) battleshipLabel).getNumOfShipsRemaining());
        os.writeObject(((ShipLabel) cruiserLabel).getNumOfShipsRemaining());
        os.writeObject(((ShipLabel) submarineLabel).getNumOfShipsRemaining());
        os.writeObject(((ShipLabel) destroyerLabel).getNumOfShipsRemaining());
    }

    public void loadInfo(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        hits = (int) ois.readObject();
        misses = (int) ois.readObject();
        updateStatLabels();
        ((ShipLabel) carrierLabel).setNumOfShipsRemaining((int) ois.readObject());
        ((ShipLabel) battleshipLabel).setNumOfShipsRemaining((int) ois.readObject());
        ((ShipLabel) cruiserLabel).setNumOfShipsRemaining((int) ois.readObject());
        ((ShipLabel) submarineLabel).setNumOfShipsRemaining((int) ois.readObject());
        ((ShipLabel) destroyerLabel).setNumOfShipsRemaining((int) ois.readObject());
    }

    public void setInfoLabelText(String newText) {
        this.infoLabel.setText(newText);
    }

    public JLabel getCarrierLabel() {
        return carrierLabel;
    }

    public JLabel getBattleshipLabel() {
        return battleshipLabel;
    }

    public JLabel getCruiserLabel() {
        return cruiserLabel;
    }

    public JLabel getSubmarineLabel() {
        return submarineLabel;
    }

    public JLabel getDestroyerLabel() {
        return destroyerLabel;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public BattlefieldMap getAccompanyingMap() {
        return accompanyingMap;
    }

    public void setAccompanyingMap(BattlefieldMap accompanyingMap) {
        this.accompanyingMap = accompanyingMap;
    }

}
