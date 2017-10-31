package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Statistics extends JPanel {

    private JLabel statLabel;
    private final JLabel carrierLabel;
    private final JLabel battleshipLabel;
    private final JLabel cruiserLabel;
    private final JLabel submarineLabel;
    private final JLabel destroyerLabel;

    private BattlefieldMap accompanyingMap;

    public Statistics() {
        // TODO: add behaviour for num of ships remaining to place, then swich to num of alive ships
        this.setLayout(new BorderLayout()); // contains textAndStatsPanel and user buttons/notifications

        JPanel textAndStatsPanel = new JPanel(); // contains titlePanel, shipsPanel, and statsPanel
        textAndStatsPanel.setLayout(new BoxLayout(textAndStatsPanel, BoxLayout.PAGE_AXIS));

        // 1. panel
        JPanel titlePanel = new JPanel(); // if just label not centered
        statLabel = new JLabel("Number of ships remaining to place:");
        Font font = statLabel.getFont().deriveFont(26f);
        statLabel.setFont(statLabel.getFont().deriveFont(21f));
        titlePanel.add(statLabel);
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
        JLabel hitMissLabel = new JLabel("Hits/Misses: " + "0/0"); // TODO: implement
        JLabel accuracyLabel = new JLabel("Accuracy: " + "0%");

        Font newFont = hitMissLabel.getFont().deriveFont(14f);
        hitMissLabel.setFont(newFont);
        accuracyLabel.setFont(newFont);
        hitMissLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        accuracyLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

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

    public void setAccompanyingMap(BattlefieldMap accompanyingMap) {
        this.accompanyingMap = accompanyingMap;
    }

    public BattlefieldMap getAccompanyingMap() {
        return accompanyingMap;
    }

    public void setStatLabelText(String newText) {
        this.statLabel.setText(newText);
    }

}
