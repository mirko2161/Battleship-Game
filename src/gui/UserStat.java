package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class UserStat extends Statistics {

    private JButton randomPlacement;
    private JButton nextShip;
    private JButton deployFleet;
    private StatisticsListener statisticsListener;
    private JPanel panel;

    public UserStat() {
        panel = new JPanel(new BorderLayout());
        add(panel);
    }

    public void addRandomPlacementButton() {
        randomPlacement = new JButton("Random placement");
        randomPlacement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accompanyingMap.randomPlacementOfShips();
                statisticsListener.startGame();
            }
        });
        panel.add(randomPlacement, BorderLayout.WEST);
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
        panel.remove(randomPlacement);
        panel.remove(nextShip);
        panel.add(deployFleet, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void addStatisticsListener(StatisticsListener listener) {
        this.statisticsListener = listener;
    }

    public void removeButtons() {
        remove(panel);
        revalidate();
        repaint();
    }

}
