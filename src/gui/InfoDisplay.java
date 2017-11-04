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
import model.BattlefieldMap;
import model.StatInfo;

public class InfoDisplay extends JPanel {

    private final JLabel infoLabel;
    private final JLabel carrierLabel;
    private final JLabel battleshipLabel;
    private final JLabel cruiserLabel;
    private final JLabel submarineLabel;
    private final JLabel destroyerLabel;
    private final JLabel nameLabel;
    private final JLabel hitMissLabel;
    private final JLabel accuracyLabel;

    private StatInfo statInfo;
    private BattlefieldMap accompanyingMap;

    public InfoDisplay() {
        statInfo = new StatInfo(new int[]{1, 2, 3, 4, 5});

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

        carrierLabel = new JLabel();
        battleshipLabel = new JLabel();
        cruiserLabel = new JLabel();
        submarineLabel = new JLabel();
        destroyerLabel = new JLabel();
        setShipLabels(null);

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
        this.add(textAndStatsPanel, BorderLayout.CENTER);
    }

    /**
     * Sets the ship labels to the state before load, or to a default state.
     *
     * @param numOfShips the previous state to reset to, or null to reset to default values
     */
    public void setShipLabels(int[] numOfShips) {
        if (numOfShips == null) {
            numOfShips = new int[]{1, 2, 3, 4, 5}; // default values
            statInfo.setNumOfShipsRemaining(numOfShips);
        }
        carrierLabel.setText(numOfShips[0] + " x Carrier");
        battleshipLabel.setText(numOfShips[1] + " x Battleship");
        cruiserLabel.setText(numOfShips[2] + " x Cruiser");
        submarineLabel.setText(numOfShips[3] + " x Submarine");
        destroyerLabel.setText(numOfShips[4] + " x Destroyer");
    }

    /**
     * Updates the given ship's label and the new number of ships of that type.
     *
     * @param shipName ship to be updated
     */
    public void updateShipLabel(String shipName) {
        int index = 0, numOfShips = 0;
        JLabel shipLabeltoUpdate = null;
        switch (shipName) {
            case "Carrier":
                index = 0;
                shipLabeltoUpdate = carrierLabel;
                break;
            case "Battleship":
                index = 1;
                shipLabeltoUpdate = battleshipLabel;
                break;
            case "Cruiser":
                index = 2;
                shipLabeltoUpdate = cruiserLabel;
                break;
            case "Submarine":
                index = 3;
                shipLabeltoUpdate = submarineLabel;
                break;
            case "Destroyer":
                index = 4;
                shipLabeltoUpdate = destroyerLabel;
                break;
        }
        numOfShips = statInfo.updateNumberOfShips(index);
        shipLabeltoUpdate.setText(numOfShips + " x " + shipName);
    }

    public void markHit() {
        statInfo.markHit();
        updateStatLabels();
    }

    public void markMiss() {
        statInfo.markMiss();
        updateStatLabels();
    }

    public void updateStatLabels() {
        int hits = statInfo.getHits(), misses = statInfo.getMisses();
        hitMissLabel.setText("Hits/Misses: " + hits + "/" + misses);
        int total = hits + misses;
        int accuracy = (int) ((double) hits / total * 100.0);
        accuracyLabel.setText("Accuracy: " + accuracy + "%");
    }

    public void saveInfo(ObjectOutputStream os) throws IOException {
        os.writeObject(statInfo);
    }

    public void loadInfo(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        statInfo = (StatInfo) ois.readObject();
        updateStatLabels();
        setShipLabels(statInfo.getNumOfShipsRemaining());
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
