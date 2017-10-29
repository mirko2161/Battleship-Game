package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JPanel;

public class UserStat extends Statistics {

    private final JPanel panel;
    private JButton nextShip;
    private StatisticsListener statisticsListener;
    private FireListener fireListener;

    public UserStat() {
        panel = new JPanel(new BorderLayout());
        add(panel);
    }

    public void addButtons() {
        addRandomPlacementButton();
        addManualPlacementButton();
    }

    private void addRandomPlacementButton() {
        JButton randomPlacement = new JButton("Random placement");
        randomPlacement.setToolTipText("Take a nap and let your ships drift towards the enemy...");
        randomPlacement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getAccompanyingMap().randomPlacementOfShips();
                statisticsListener.startGame();
            }
        });
        panel.add(randomPlacement, BorderLayout.WEST);
    }

    private void addManualPlacementButton() {
        JButton manualPlacement = new JButton("Expert placement");
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
                getAccompanyingMap().nextShip();
            }
        });
        nextShip.setMnemonic(KeyEvent.VK_N);
        panel.add(nextShip, BorderLayout.CENTER);
    }

    public void renameButton(String newText) {
        nextShip.setText(newText);
    }

    public void addRandomFireButton() {
        JButton randomFire = new JButton("Random Fire");
        randomFire.setToolTipText("Give a chance to fire to one of your crew...");
        randomFire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireListener.randomFire("user");
            }
        });
        randomFire.setMnemonic(KeyEvent.VK_R);
        panel.add(randomFire, BorderLayout.WEST);
    }

    public void addStatisticsListener(StatisticsListener listener) {
        this.statisticsListener = listener;
    }

    public void addFireListener(FireListener listener) {
        this.fireListener = listener;
    }

    public void removeButtons() {
        panel.removeAll();
        revalidate();
        repaint();
    }

}
