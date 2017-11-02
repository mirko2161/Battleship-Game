package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    private BattlefieldMap userMap;
    private InfoDisplay enemyInfo;
    private InfoDisplay userInfo;
    private final JLabel startText;

    public MainFrame(String title) throws HeadlessException {
        super(title);

        setJMenuBar(createMenuBar());

        startText = new JLabel("  Battleship  ");
        add(startText, BorderLayout.CENTER);

        int width = 850, height = 740;
        setSize(width, height);
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
        JMenuItem forfeitGame = new JMenuItem("Forfeit Game");
        JMenuItem exitGame = new JMenuItem("Exit");

        fileMenu.add(newGame);
        fileMenu.add(saveGame);
        fileMenu.add(loadGame);
        fileMenu.add(forfeitGame);
        fileMenu.add(exitGame);

        fileMenu.setMnemonic(KeyEvent.VK_F);
        newGame.setMnemonic(KeyEvent.VK_N);
        saveGame.setMnemonic(KeyEvent.VK_S);
        loadGame.setMnemonic(KeyEvent.VK_L);
        forfeitGame.setMnemonic(KeyEvent.VK_F);
        exitGame.setMnemonic(KeyEvent.VK_E);

        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        saveGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        loadGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        exitGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userMap != null && userMap.isShipsPlaced()) {
                    saveBeforeExit();
                }
                addPanels();
                showMessageDialog(MainFrame.this, "Click on a field to place a "
                        + "ship horizontaly, right click to place verticaly",
                        "Place your fleet commander", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        saveGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });

        loadGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBeforeExit();
                try {
                    new FileSaverAndLoader(MainFrame.this).loadFromFile();
                } catch (IOException | ClassNotFoundException ex) {
                    showMessageDialog(MainFrame.this, "Could not load files!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        forfeitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userMap != null && userMap.isShipsPlaced()) {
                    showMessageDialog(null, "Our fleet is falling back! We have been bested!",
                            "We surrendered!", JOptionPane.INFORMATION_MESSAGE);
                    updateNotificationLabel("Defeat!");
                    removeButtonActions(); // removes clickability
                } else {
                    showMessageDialog(MainFrame.this, "We haven't even met the enemy!",
                            "Shameful display", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBeforeExit();
                int clicked = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want "
                        + "to quit?", "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
                if (clicked == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        menuBar.add(fileMenu);
    }

    private void saveGame() {
        if (userMap != null && userMap.isShipsPlaced()) { // checks that it has something to save
            try {
                new FileSaverAndLoader(this).saveToFile();
            } catch (IOException ex) {
                showMessageDialog(this, "Could not save game!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            showMessageDialog(this, "Cannot save file - game hasn't started!", "Game not saved",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveBeforeExit() {
        if (userMap != null && userMap.isShipsPlaced()) {
            int clicked = JOptionPane.showConfirmDialog(MainFrame.this, "Do you want to save the "
                    + "game first?", "Confirm exit from current game", JOptionPane.YES_NO_OPTION);
            if (clicked == JOptionPane.OK_OPTION) {
                saveGame();
            }
        }
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
        getContentPane().removeAll(); // in case game was already in progress
        GridLayout layout = new GridLayout(2, 2);
        setLayout(layout);

        enemyMap = new BattlefieldMap("enemy");
        userMap = new BattlefieldMap("user");
        enemyInfo = new EnemyInfoDisplay();
        userInfo = new UserInfoDisplay();

        enemyMap.setMainFrame(this);
        userMap.setMainFrame(this);

        enemyMap.setAccompanyingInfo(enemyInfo);
        userMap.setAccompanyingInfo(userInfo);
        enemyInfo.setAccompanyingMap(enemyMap);
        userInfo.setAccompanyingMap(userMap);

        ((UserInfoDisplay) userInfo).addInfoDisplayListener(new InfoDisplayListener() {
            @Override
            public void startGame() {
                beginGame();
            }
        });
        ((UserInfoDisplay) userInfo).addFireListener(new FireListener() {
            @Override
            public void randomFire() {
                enemyMap.randomFire();
            }
        });
        add(enemyMap); // must be added in this order
        add(enemyInfo);
        add(userInfo);
        add(userMap);

        setVisible(true);
    }

    public void beginGame() {
        enemyMap.randomPlacementOfShips();
        ((EnemyInfoDisplay) enemyInfo).showEnemyInfo();
        ((UserInfoDisplay) userInfo).removeButtons();
        ((UserInfoDisplay) userInfo).addRandomFireButton();

        enemyInfo.setInfoLabelText("Number of enemy ships remaining:");
        userInfo.setInfoLabelText("Number of your ships remaining:");
        ((UserInfoDisplay) userInfo).resetNumOfShips();

        enemyMap.setShipsPlaced(true);
        userMap.setShipsPlaced(true);

        updateNotificationLabel("Pick a target to fire upon.");
    }

    public void updateNotificationLabel(String newLabel) {
        ((EnemyInfoDisplay) enemyInfo).updateNotificationLabel(newLabel);
    }

    public void returnFire() {
        // TODO: add some logic to fire patterns, like if hit, next time target adjacent fields
        userMap.randomFire();
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

    @Override
    public void validate() {
        super.validate();
        setTextToFit();
    }

    private void setTextToFit() { // resizable "title" text
        Font startTextFont = startText.getFont();
        String startTextText = startText.getText();

        int stringWidth = startText.getFontMetrics(startTextFont).stringWidth(startTextText);
        int componentWidth = startText.getWidth();

        // how much the font can grow in width
        double widthRatio = componentWidth / (double) stringWidth;

        int newFontSize = (int) (startTextFont.getSize() * widthRatio);
        int componentHeight = startText.getHeight();

        // make sure the new font size will not be larger than the height of startText
        int fontSizeToUse = Math.min(newFontSize, componentHeight);
        startText.setFont(new Font(startTextFont.getName(), Font.PLAIN, fontSizeToUse));
    }

    private void removeButtonActions() {
        enemyMap.removeButtonActions();
        userMap.removeButtonActions();
        ((UserInfoDisplay) userInfo).removeButtons();
    }

    public BattlefieldMap getEnemyMap() {
        return enemyMap;
    }

    public BattlefieldMap getUserMap() {
        return userMap;
    }

    public InfoDisplay getEnemyInfo() {
        return enemyInfo;
    }

    public InfoDisplay getUserInfo() {
        return userInfo;
    }

}
