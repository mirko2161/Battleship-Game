package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import model.Ship;
import utils.MapSaverAndLoader;

public class BattlefieldMap extends JPanel {

    private JFrame mainFrame;
    private final Field[][] gridFields;
    private boolean[][] savedStateOfMap; // each field, does it contain a ship or not
    private boolean[][] potencialStateOfMap;
    private int currentShip;
    private final int[] lengthOfShips;
    private boolean shipsPlaced;
    private final String nameOfMap;
    private List<Ship> listOfShips;
    private final String[] listOfShipNames;

    private Statistics accompanyingStat;
    private final MapSaverAndLoader saveOrLoad;

    public BattlefieldMap(String name) {
        GridLayout layout = new GridLayout(10, 10);
        setLayout(layout);

        this.nameOfMap = name;
        this.gridFields = new Field[10][10];
        this.lengthOfShips = new int[]{5, 4, 4, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2};
//        this.lengthOfShips = new int[]{5, 4, 3, 3}; // for quicker test
        this.savedStateOfMap = new boolean[10][10];
        this.potencialStateOfMap = savedStateOfMap;
        this.listOfShips = new ArrayList<>(lengthOfShips.length);
        this.listOfShipNames = new String[]{"Carrier", "Battleship", "Battleship", "Cruiser",
            "Cruiser", "Cruiser", "Submarine", "Submarine", "Submarine", "Submarine",
            "Destroyer", "Destroyer", "Destroyer", "Destroyer", "Destroyer"};
        this.saveOrLoad = new MapSaverAndLoader(this);

        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                gridFields[row][column] = new Field(row, column);
                Field field = gridFields[row][column];
                addListener(field);
                field.setBackground(Color.BLUE);
                add(field);
            }
        }
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    private void addListener(JButton field) { // TODO: make smaller, segment(second ML when game begins?)
        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = ((Field) e.getComponent()).getRow();
                int column = ((Field) e.getComponent()).getColumn();

                if (getNameOfMap().equals("user")) { // behaviour for player buttons
                    if (isShipsPlaced()) { // if game can begin, change behavour of fields
                        showMessageDialog(BattlefieldMap.this, "You can't fire upon your own fleet!",
                                "Not allowed", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            placeShips(row, column, true); // horizontal placement
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            placeShips(row, column, false); // vertical placement
                        }
                    }
                } else if (getNameOfMap().equals("enemy")) { // behaviour for """AI""" buttons
                    if (isShipsPlaced()) {
                        if (((Field) e.getComponent()).isHit()) {
                            showMessageDialog(BattlefieldMap.this,
                                    "You can fire in the same place, your crew will laught at you...",
                                    "Not allowed", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            fire(row, column);
                        }
                    } else {
                        showMessageDialog(BattlefieldMap.this, "You can't place the enemy ships, "
                                + "that would be cheating!", "Not allowed",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    private void placeShips(int row, int column, boolean isHorizontal) {
        resetMapToPreviousState(); // prevents same ship being placed multiple times
        boolean placed;
        if (isHorizontal) {
            placed = putShipOnMapHorizontally(lengthOfShips[currentShip], row, column);
        } else {
            placed = putShipOnMapVertically(lengthOfShips[currentShip], row, column);
        }

        String label;
        if (placed) {
            label = listOfShipNames[currentShip] + " placed.";
        } else {
            label = "Cannot place ship there.";
            resetMapToPreviousState();
        }
        ((MainFrame) mainFrame).updateNotificationLabel(label);
    }

    private void resetMapToPreviousState() {
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                if (!savedStateOfMap[row][column]) {
                    gridFields[row][column].setBackground(Color.BLUE);
                    gridFields[row][column].setContainsShip(false);
                }
            }
        }
        potencialStateOfMap = savedStateOfMap;
    }

    private boolean putShipOnMapHorizontally(int lengthOfShip, int row, int column) {
        // update potencial to be like saved so it can be changed
        potencialStateOfMap = new boolean[savedStateOfMap.length][];
        for (int i = 0; i < savedStateOfMap.length; i++) {
            potencialStateOfMap[i] = savedStateOfMap[i].clone();
        }
        if (lengthOfShip > 10 - column) { // can it fit
            return false;
        }
        for (int i = 0; i < lengthOfShip; i++) { // if any of the fields already contains a ship, abort
            if (gridFields[row][column + i].isWithShip()) {
                return false;
            }
        }
        for (int i = 0; i < lengthOfShip; i++) { // else put ship there
            Field field = gridFields[row][column + i];

            potencialStateOfMap[row][column + i] = true;
            field.setContainsShip(true);
            if (nameOfMap.equals("user")) { // comment out if for test purposes
                field.setBackground(Color.CYAN);
            }
        }
        return true;
    }

    private boolean putShipOnMapVertically(int lengthOfShip, int row, int column) {
        potencialStateOfMap = new boolean[savedStateOfMap.length][];
        for (int i = 0; i < savedStateOfMap.length; i++) {
            potencialStateOfMap[i] = savedStateOfMap[i].clone();
        }
        if (lengthOfShip > 10 - row) {
            return false;
        }
        for (int i = 0; i < lengthOfShip; i++) {
            if (gridFields[row + i][column].isWithShip()) {
                return false;
            }
        }
        for (int i = 0; i < lengthOfShip; i++) {
            Field field = gridFields[row + i][column];

            potencialStateOfMap[row + i][column] = true;
            field.setContainsShip(true);
            if (nameOfMap.equals("user")) { // comment out if for test purposes
                field.setBackground(Color.CYAN);
            }
        }
        return true;
    }

    public void nextShip() {
        if (savedStateOfMap != potencialStateOfMap) { // prevents advancing without placing a ship
            ((MainFrame) mainFrame).updateNotificationLabel(listOfShipNames[currentShip]
                    + " deployed.");
            saveShipPosition();

            if (currentShip == lengthOfShips.length - 1) { // TODO: prevent player from moving and removing last ship
                ((UserStat) accompanyingStat).removeNextShipButton();
                ((UserStat) accompanyingStat).addDeployFleetButton();
            } else {
                currentShip++;
                // potential map becomes saved
                savedStateOfMap = potencialStateOfMap;
            }
        } else {
            ((MainFrame) mainFrame).updateNotificationLabel("Must place "
                    + listOfShipNames[currentShip] + " to continue!");
        }
    }

    private void saveShipPosition() { // TODO: move to Ship..?
        Ship ship = new Ship(new ArrayList<>(10), true, listOfShipNames[currentShip]);
        listOfShips.add(ship);

        for (int row = 0; row < savedStateOfMap.length; row++) {
            for (int column = 0; column < savedStateOfMap[row].length; column++) {
                // diff between two states is new ship position
                if (savedStateOfMap[row][column] != potencialStateOfMap[row][column]) {
                    ship.setCoordinates(row, column);
                }
            }
        }
    }

    public void randomPlacementOfShips() {
        for (currentShip = 0; currentShip < lengthOfShips.length; currentShip++) {
            int row = (int) (Math.random() * 10);
            int column = (int) (Math.random() * 10);
            boolean isHorizontal = Math.random() < 0.5;

            boolean placed;
            if (isHorizontal) {
                placed = putShipOnMapHorizontally(lengthOfShips[currentShip], row, column);
            } else {
                placed = putShipOnMapVertically(lengthOfShips[currentShip], row, column);
            }
            if (placed) {
                saveShipPosition();
                savedStateOfMap = potencialStateOfMap;
            } else { // if ship placement failed, try again
                currentShip--;
            }
        }
    }

    public void fire(int row, int column) {
        Field unluckyBastard = gridFields[row][column];
        unluckyBastard.setIsHit(true);

        if (unluckyBastard.isWithShip()) {
            unluckyBastard.setBackground(Color.RED);

            if (checkIfAllShipsAreDead()) {
                ((MainFrame) mainFrame).endTheGame(this);
                return; // prevents the dead fleet from firing back
            } else {
                String newLabel = nameOfMap.equals("enemy") ? "We hit the enemy! They are "
                        + "returning fire..." : "Our ship got hit!";
                ((MainFrame) mainFrame).updateNotificationLabel(newLabel);
            }
        } else {
            String newLabel = nameOfMap.equals("enemy") ? "We missed! Enemy firing..."
                    : "Enemy missed us!";
            ((MainFrame) mainFrame).updateNotificationLabel(newLabel);
            unluckyBastard.setBackground(Color.GRAY);
        }
        if (nameOfMap.equals("enemy")) { // if player fired, return fire, but after delay
            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ((MainFrame) getTopLevelAncestor()).returnFire();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public boolean checkIfAllShipsAreDead() {
        boolean allShipsDead = true;
        for (Ship ship : listOfShips) {
            if (ship.isAlive()) { // skip dead ships
                if (ship.checkIfShipIsDestroyed(this)) {
                    ((MainFrame) mainFrame).updateNotificationLabel(ship.getName() + " destroyed!");
                    accompanyingStat.updateShipLabels(ship.getName());
                } else {
                    allShipsDead = false; // first alive ship; cont checking to update labels
                }
            }
        }
        return allShipsDead;
    }

    public void removeButtonActions() {
        for (int row = 0; row < 10; row++) {
            for (int column = 0; column < 10; column++) {
                Field field = gridFields[row][column];
                field.removeAllMouseListeners();
            }
        }
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setAccompanyingStat(Statistics userStat) {
        this.accompanyingStat = userStat;
    }

    public boolean isShipsPlaced() {
        return shipsPlaced;
    }

    public String getNameOfMap() {
        return nameOfMap;
    }

    public void setShipsPlaced(boolean shipsPlaced) {
        this.shipsPlaced = shipsPlaced;
    }

    public Field[][] getGridFields() {
        return gridFields;
    }

    public boolean[][] getSavedStateOfMap() {
        return savedStateOfMap;
    }

    public void setSavedStateOfMap(boolean[][] savedStateOfMap) {
        this.savedStateOfMap = savedStateOfMap;
    }

    public boolean[][] getPotencialStateOfMap() {
        return potencialStateOfMap;
    }

    public void setPotencialStateOfMap(boolean[][] potencialStateOfMap) {
        this.potencialStateOfMap = potencialStateOfMap;
    }

    public MapSaverAndLoader getSaveOrLoad() {
        return saveOrLoad;
    }

    public List<Ship> getListOfShips() {
        return listOfShips;
    }

    public void setListOfShips(List<Ship> listOfShips) {
        this.listOfShips = listOfShips;
    }

}
