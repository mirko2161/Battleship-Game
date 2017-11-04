package model;

import java.io.Serializable;

public class StatInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int hits;
    private int misses;
    private int[] numOfShipsRemaining; // for each ship type, how many are left

    /**
     *
     * @param numOfShips for each ship type, total number of ships
     */
    public StatInfo(int[] numOfShips) {
        this.numOfShipsRemaining = numOfShips;
    }

    /**
     * Updates the number of ships for the given index.
     *
     * @param index the index of the ship to update
     * @return updated number of ships for the given index
     */
    public int updateNumberOfShips(int index) {
        return --numOfShipsRemaining[index];
    }

    /**
     * Records the hits and updates the InfoDisplay.
     */
    public void markHit() {
        hits++;
    }

    /**
     * Records the misses and updates the InfoDisplay.
     */
    public void markMiss() {
        misses++;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }

    public int[] getNumOfShipsRemaining() {
        return numOfShipsRemaining;
    }

    public void setNumOfShipsRemaining(int[] numOfShipsRemaining) {
        this.numOfShipsRemaining = numOfShipsRemaining;
    }

}
