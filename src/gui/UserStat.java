package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class UserStat extends Statistics {

    private final JPanel panel;
    private JButton randomPlacement;
    private JButton manualPlacement;
    private JButton nextShip;
    private JButton deployFleet;
    private StatisticsListener statisticsListener;

    public UserStat() {
        panel = new JPanel(new BorderLayout());
        add(panel);
    }

    public void addButtons() {
        addRandomPlacementButton();
        addManualPlacementButton();
    }

    public void addRandomPlacementButton() {
        randomPlacement = new JButton("Random placement");
        randomPlacement.setToolTipText("Take a nap and let your ships drift towards the enemy...");
        randomPlacement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accompanyingMap.randomPlacementOfShips();
                statisticsListener.startGame();
            }
        });
        panel.add(randomPlacement, BorderLayout.WEST);
    }

    private void addManualPlacementButton() {
        manualPlacement = new JButton("Expert placement");
        manualPlacement.setToolTipText("Strategically position your fleet to best the enemy");
        manualPlacement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeButtons();
                addNextShipButton();
            }
        });
        panel.add(manualPlacement, BorderLayout.CENTER);
    }

    public void addNextShipButton() {
        nextShip = new JButton("Next ship");
        nextShip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accompanyingMap.nextShip();
            }
        });
        panel.add(nextShip, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void renameButton(String newText) {
        nextShip.setText(newText);
    }

    public void addDeployFleetButton() {
        this.deployFleet = new JButton("Deploy Fleet");
        this.deployFleet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statisticsListener.startGame();
            }
        });
        removeButtons();
        panel.add(deployFleet, BorderLayout.CENTER);
    }

    public void addStatisticsListener(StatisticsListener listener) {
        this.statisticsListener = listener;
    }

    public void removeButtons() {
        panel.removeAll();
        revalidate();
        repaint();
    }

}
