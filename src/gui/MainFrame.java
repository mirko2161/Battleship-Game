package gui;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.KeyStroke;
import utils.FileSaverAndLoader;

public class MainFrame extends JFrame {

    private JMenuBar menuBar;
    private BattlefieldMap enemyMap;
    private Statistics enemyStat;
    private Statistics userStat;
    private BattlefieldMap userMap;
    private final FileSaverAndLoader fileSaver;

    public MainFrame(String title) throws HeadlessException {
        super(title);

        GridLayout layout = new GridLayout(2, 2);
        setLayout(layout);

        setJMenuBar(createMenuBar());
        // TODO: have something on the screen before new game is clicked, like the title
        fileSaver = new FileSaverAndLoader();
        fileSaver.setMainFrame(this);

        setSize(850, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        setVisible(true);

    }

    private JMenuBar createMenuBar() {
        menuBar = new JMenuBar();
        setUpFileMenu();
        setUpUsersMenu();

        return menuBar;
    }

    private void setUpFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem saveGame = new JMenuItem("Save...");
        JMenuItem loadGame = new JMenuItem("Load...");
        JMenuItem forfitGame = new JMenuItem("Forfit Game");
        JMenuItem exitGame = new JMenuItem("Exit");

        fileMenu.add(newGame);
        fileMenu.add(saveGame);
        fileMenu.add(loadGame);
        fileMenu.add(forfitGame); // TODO: add f-ty for forfiting games
        fileMenu.add(exitGame);

        fileMenu.setMnemonic(KeyEvent.VK_F);
        newGame.setMnemonic(KeyEvent.VK_N);
        saveGame.setMnemonic(KeyEvent.VK_S);
        loadGame.setMnemonic(KeyEvent.VK_L);
        forfitGame.setMnemonic(KeyEvent.VK_F);
        exitGame.setMnemonic(KeyEvent.VK_E);

        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        saveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        loadGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        exitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                addPanels();
                showMessageDialog(MainFrame.this, "Click on a field to place a "
                        + "ship horizontaly, right click to place verticaly",
                        "Place your fleet commander", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // TODO: ask to save the game if exiting or attempting a load
        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userMap != null && userMap.isShipsPlaced()) {
                    try {
                        fileSaver.saveToFile();
                    } catch (IOException ex) {
                        showMessageDialog(MainFrame.this, "Could not save files!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    showMessageDialog(MainFrame.this,
                            "Cannot save files - game hasn't started!", "Game not saved",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    fileSaver.loadFromFile();
                } catch (IOException ex) {
                    showMessageDialog(MainFrame.this, "Could not load files!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int clicked = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want "
                        + "to quit?", "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
                if (clicked == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        menuBar.add(fileMenu);
    }

    private void setUpUsersMenu() { // TODO: implement
        JMenu usersMenu = new JMenu("Users");

        JMenuItem newUser = new JMenuItem("New User...");
        JMenuItem loadUser = new JMenuItem("Load User...");
        JMenuItem deleteUser = new JMenuItem("Delete User...");

        usersMenu.setMnemonic(KeyEvent.VK_U);
        newUser.setMnemonic(KeyEvent.VK_N);
        loadUser.setMnemonic(KeyEvent.VK_L);
        deleteUser.setMnemonic(KeyEvent.VK_D);

        usersMenu.add(newUser);
        usersMenu.add(loadUser);
        usersMenu.add(deleteUser);

        menuBar.add(usersMenu);
    }

    public void addPanels() {
        enemyMap = new BattlefieldMap("enemy");
        enemyStat = new EnemyStat();
        userStat = new UserStat();
        userMap = new BattlefieldMap("user");

        userMap.setMainFrame(this);
        enemyMap.setMainFrame(this);

        userStat.setAccompanyingMap(userMap);
        userMap.setAccompanyingStat(userStat);
        enemyStat.setAccompanyingMap(enemyMap);
        enemyMap.setAccompanyingStat(enemyStat);

        ((UserStat) userStat).addNextShipButton();
        ((EnemyStat) enemyStat).addNotificationLabel();

        ((UserStat) userStat).addStatisticsListener(new StatisticsListener() {
            @Override
            public void startGame() {
                enemyMap.randomPlacementOfShips();
                userMap.setShipsPlaced(true);
                enemyMap.setShipsPlaced(true);
                updateNotificationLabel("Pick a target to fire upon.");
                showMessageDialog(MainFrame.this, "Choose an enemy field to fire upon!",
                        "Battle is joined!", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        add(enemyMap);
        add(enemyStat);
        add(userStat);
        add(userMap);

        setVisible(true);
    }

    public void updateNotificationLabel(String newLabel) {
        ((EnemyStat) enemyStat).updateNotificationLabel(newLabel);
    }

    // TODO: a random fire button for player; hover text - "random fire - give a chance
    // to fire to one of your crew"; replaces the random placement button
    public void returnFire() {
        int row, column;
        boolean alreadyHit;
        // TODO: add some logic to fire patterns, like if hit, next time target adjacent fields
        do { // if AI firing, use random coordinates
            row = (int) (Math.random() * 10);
            column = (int) (Math.random() * 10);
            alreadyHit = userMap.getGridFields()[row][column].isHit();
        } while (alreadyHit);

        userMap.fire(row, column);
    }

    public void endTheGame(BattlefieldMap map) {
        if (map.getNameOfMap().equals("enemy")) {
            showMessageDialog(this, "Their ships lie on the bottom of the sea. We are victorious!",
                    "We have beaten the enemy", JOptionPane.INFORMATION_MESSAGE);
            updateNotificationLabel("Victory!");
        } else {
            showMessageDialog(null, "Our fleet is gone! We have been defeated!",
                    "We lost!", JOptionPane.INFORMATION_MESSAGE);
            updateNotificationLabel("Defeat!");
        }
        removeButtonActions(); // removes clickability
    }

    public void removeButtonActions() {
        userMap.removeButtonActions();
        enemyMap.removeButtonActions();
    }

    public BattlefieldMap getEnemyMap() {
        return enemyMap;
    }

    public Statistics getEnemyStat() {
        return enemyStat;
    }

    public Statistics getUserStat() {
        return userStat;
    }

    public BattlefieldMap getUserMap() {
        return userMap;
    }

    public void setEnemyMap(BattlefieldMap enemyMap) {
        this.enemyMap = enemyMap;
    }

    public void setEnemyStat(Statistics enemyStat) {
        this.enemyStat = enemyStat;
    }

    public void setUserStat(Statistics userStat) {
        this.userStat = userStat;
    }

    public void setUserMap(BattlefieldMap userMap) {
        this.userMap = userMap;
    }

}
