package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class UserStat extends Statistics {

    private JButton randomPlacement;
    private JButton nextShip;
    private JButton deployFleet;
    private StatisticsListener statisticsListener;

    public void addRandomPlacementButton() {
        randomPlacement = new JButton("Random placement");
        randomPlacement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accompanyingMap.randomPlacementOfShips();
                statisticsListener.startGame();
            }
        });
        add(randomPlacement);
    }

    public void addNextShipButton() {
        nextShip = new JButton("Next ship");
        nextShip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accompanyingMap.nextShip();
            }
        });
        add(nextShip);
    }

    public void removeNextShipButton() {
        remove(nextShip);
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
        add(deployFleet);
        revalidate();
        repaint();
    }

    public void addStatisticsListener(StatisticsListener listener) {
        this.statisticsListener = listener;
    }

    public void removeButtons() {
        if (randomPlacement != null) {
            remove(randomPlacement);
        }
        if (nextShip != null) {
            remove(nextShip);
        }
        if (deployFleet != null) {
            remove(deployFleet);
        }
    }

}
