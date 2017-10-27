package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class UserStat extends Statistics {

    private JButton nextShip;
    private JButton deployFleet;
    private StatisticsListener statisticsListener;

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
                remove(deployFleet);
                revalidate();
                repaint();
            }
        });
        add(deployFleet);
        revalidate();
        repaint();
    }

    public void addStatisticsListener(StatisticsListener listener) {
        this.statisticsListener = listener;
    }

}
