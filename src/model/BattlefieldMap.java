package model;

import gui.BattlefieldMapGUI;
import gui.FieldGUI;
import gui.InfoDisplay;
import gui.MainFrame;
import gui.UserInfoDisplay;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.Timer;
import utils.MapSaverAndLoader;

public class BattlefieldMap {

    private static final int ROW = 10;
    private static final int COLUMN = 10;

    private final String nameOfMap;
    private boolean[][] savedStateOfMap; // each field, does it contain a ship or not
    private boolean[][] potencialStateOfMap;
    private int currentShip;
    private boolean shipsPlaced; // can the game begin
    private List<Ship> listOfShips;

    private JFrame mainFrame;
    private InfoDisplay accompanyingInfo;
    private final MapSaverAndLoader saveOrLoad;
    private final BattlefieldMapGUI mapGUI;

    public BattlefieldMap(String name, BattlefieldMapGUI mapGUI) {

        this.nameOfMap = name;
        this.savedStateOfMap = new boolean[ROW][COLUMN];
        this.potencialStateOfMap = savedStateOfMap;
        this.listOfShips = new ArrayList<>();

        this.mapGUI = mapGUI;
        this.saveOrLoad = new MapSaverAndLoader(this);

        String[] listOfShipNames = new String[]{"Carrier", "Battleship", "Battleship", "Cruiser",
            "Cruiser", "Cruiser", "Submarine", "Submarine", "Submarine", "Submarine",
            "Destroyer", "Destroyer", "Destroyer", "Destroyer", "Destroyer"};
        int[] lengthOfShips = new int[]{5, 4, 4, 3, 3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2};

        for (int i = 0; i < listOfShipNames.length; i++) {
            Ship ship = new Ship(listOfShipNames[i], lengthOfShips[i]);
            listOfShips.add(ship);
        }
    }

    public void placeShips(int row, int column, boolean isHorizontal) {
        resetMapToPreviousState(); // prevents same ship being placed multiple times
        boolean placed;
        if (isHorizontal) {
            placed = putShipOnMapHorizontally(listOfShips.get(currentShip).getLength(), row, column);
        } else {
            placed = putShipOnMapVertically(listOfShips.get(currentShip).getLength(), row, column);
        }

        String newLabel;
        if (placed) {
            newLabel = listOfShips.get(currentShip).getName() + " placed.";
        } else {
            newLabel = "Cannot place ship there.";
            resetMapToPreviousState();
        }
        ((MainFrame) mainFrame).updateNotificationLabel(newLabel);
    }

    private void resetMapToPreviousState() {
        for (int row = 0; row < ROW; row++) {
            for (int column = 0; column < COLUMN; column++) {
                if (!savedStateOfMap[row][column]) { // unchanged until confir, so can be used for reset
                    mapGUI.getGridFields()[row][column].setBackground(Color.BLUE);
                    mapGUI.getGridFields()[row][column].getField().setContainsShip(false);
                }
            }
        }
        potencialStateOfMap = savedStateOfMap; // so it can't pass the nextShip condition
    }

    private boolean putShipOnMapHorizontally(int lengthOfShip, int row, int column) {
        // to be a diff object for nextShip check AND be usefull for saveShipPosition
        potencialStateOfMap = new boolean[savedStateOfMap.length][];
        for (int i = 0; i < savedStateOfMap.length; i++) {
            potencialStateOfMap[i] = savedStateOfMap[i].clone();
        }
        if (lengthOfShip > COLUMN - column) { // can it fit
            return false;
        }
        for (int i = 0; i < lengthOfShip; i++) { // if any of the fields already contains a ship, abort
            if (mapGUI.getGridFields()[row][column + i].getField().isWithShip()) {
                return false;
            }
        }
        for (int i = 0; i < lengthOfShip; i++) { // else put ship there
            FieldGUI fieldGUI = mapGUI.getGridFields()[row][column + i];

            potencialStateOfMap[row][column + i] = true;
            fieldGUI.getField().setContainsShip(true);
            if (nameOfMap.equals("user")) { // comment out if for test purposes
                fieldGUI.setBackground(Color.CYAN);
            }
        }
        return true;
    }

    private boolean putShipOnMapVertically(int lengthOfShip, int row, int column) {
        potencialStateOfMap = new boolean[savedStateOfMap.length][];
        for (int i = 0; i < savedStateOfMap.length; i++) {
            potencialStateOfMap[i] = savedStateOfMap[i].clone();
        }
        if (lengthOfShip > ROW - row) {
            return false;
        }
        for (int i = 0; i < lengthOfShip; i++) {
            if (mapGUI.getGridFields()[row + i][column].getField().isWithShip()) {
                return false;
            }
        }
        for (int i = 0; i < lengthOfShip; i++) {
            FieldGUI fieldGUI = mapGUI.getGridFields()[row + i][column];

            potencialStateOfMap[row + i][column] = true;
            fieldGUI.getField().setContainsShip(true);
            if (nameOfMap.equals("user")) { // comment out if for test purposes
                fieldGUI.setBackground(Color.CYAN);
            }
        }
        return true;
    }

    public void nextShip() {
        String nameOfCurrentShip = listOfShips.get(currentShip).getName();

        if (savedStateOfMap != potencialStateOfMap) { // prevents advancing without placing a ship
            ((MainFrame) mainFrame).updateNotificationLabel(nameOfCurrentShip + " deployed.");
            listOfShips.get(currentShip).saveShipPosition(mapGUI.getMap());
            accompanyingInfo.updateShipLabel(nameOfCurrentShip); // decrease num of ships to place

            if (currentShip == listOfShips.size() - 1) { // last ship placement confirmed
                ((MainFrame) mainFrame).beginGame();
            } else if (currentShip == listOfShips.size() - 2) { // next to last ship
                ((UserInfoDisplay) accompanyingInfo).renameButton("Deploy ships");
            }

            currentShip++;
            savedStateOfMap = potencialStateOfMap; // potential map becomes saved
        } else {
            ((MainFrame) mainFrame).updateNotificationLabel("Must place " + nameOfCurrentShip
                    + " to continue!");
        }
    }

    public void randomPlacementOfShips() {
        for (int i = 0; i < listOfShips.size(); i++) {
            int row = (int) (Math.random() * ROW);
            int column = (int) (Math.random() * COLUMN);
            boolean isHorizontal = Math.random() < 0.5;

            boolean placed;
            Ship ship = listOfShips.get(i);
            if (isHorizontal) {
                placed = putShipOnMapHorizontally(ship.getLength(), row, column);
            } else {
                placed = putShipOnMapVertically(ship.getLength(), row, column);
            }
            if (placed) {
                ship.saveShipPosition(mapGUI.getMap());
                savedStateOfMap = potencialStateOfMap;
            } else { // if ship placement failed - try again
                i--;
            }
        }
    }

    public void fire(int row, int column) {
        FieldGUI unluckyBastard = mapGUI.getGridFields()[row][column];
        unluckyBastard.getField().setIsHit(true);
        MainFrame main = ((MainFrame) mainFrame);

        if (unluckyBastard.getField().isWithShip()) {
            unluckyBastard.setBackground(Color.RED);
            accompanyingInfo.markHit();

            if (checkIfAllShipsAreDead()) {
                main.endTheGame(this);
                return; // prevents the dead fleet from firing back
            } else {
                String newLabel = nameOfMap.equals("enemy") ? "We hit the enemy! They are "
                        + "returning fire..." : "Our ship got hit!";
                main.updateNotificationLabel(newLabel);
            }
        } else {
            String newLabel = nameOfMap.equals("enemy") ? "We missed! Enemy firing..."
                    : "Enemy missed us!";
            main.updateNotificationLabel(newLabel);
            unluckyBastard.setBackground(Color.GRAY);
            accompanyingInfo.markMiss();
        }
        if (nameOfMap.equals("enemy")) { // if player fired, return fire, but after delay
            int delayInMilliseconds = 1500;
            Timer timer = new Timer(delayInMilliseconds, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    main.returnFire();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void randomFire() {
        int row, column;
        boolean alreadyHit;
        do { // choose random coordinates, but only if they weren't previously hit
            row = (int) (Math.random() * ROW);
            column = (int) (Math.random() * COLUMN);
            alreadyHit = mapGUI.getGridFields()[row][column].getField().isHit();
        } while (alreadyHit);

        fire(row, column);
    }

    private boolean checkIfAllShipsAreDead() {
        boolean allShipsDead = true;
        for (Ship ship : listOfShips) {
            if (ship.isAlive()) { // skip dead ships
                if (ship.checkIfShipIsDestroyed()) {
                    ((MainFrame) mainFrame).updateNotificationLabel(ship.getName() + " destroyed!");
                    accompanyingInfo.updateShipLabel(ship.getName());
                } else {
                    allShipsDead = false; // first alive ship; cont checking to update labels
                }
            }
        }
        return allShipsDead;
    }

    public String getNameOfMap() {
        return nameOfMap;
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

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void setAccompanyingInfo(InfoDisplay userStat) {
        this.accompanyingInfo = userStat;
    }

    public boolean isShipsPlaced() {
        return shipsPlaced;
    }

    public void setShipsPlaced(boolean shipsPlaced) {
        this.shipsPlaced = shipsPlaced;
    }

    public List<Ship> getListOfShips() {
        return listOfShips;
    }

    public void setListOfShips(List<Ship> listOfShips) {
        this.listOfShips = listOfShips;
    }

    public MapSaverAndLoader getSaveOrLoad() {
        return saveOrLoad;
    }

    public BattlefieldMapGUI getMapGUI() {
        return mapGUI;
    }

}
