package com.tiy.webapp;

/**
 * Created by Paul Dennis on 1/24/2017.
 */
public class SettingsContainer {

    private int numGuesses;
    private int numColors;
    private int boardWidth;
    private boolean duplesAllowed;

    public static final int DEFAULT_BOARD_WIDTH = 4;
    public static final int DEFAULT_NUM_GUESSES = 10;
    public static final int DEFAULT_NUM_COLORS = 6;
    public static final boolean DEFAULT_DUPLES_ALLOWED = false;

    public SettingsContainer () {

    }

    public SettingsContainer(int numGuesses, int numColors, int boardWidth, boolean duplesAllowed) {
        this.numGuesses = numGuesses;
        this.numColors = numColors;
        this.boardWidth = boardWidth;
        this.duplesAllowed = duplesAllowed;
    }

    public int getNumGuesses() {
        return numGuesses;
    }

    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }

    public int getNumColors() {
        return numColors;
    }

    public void setNumColors(int numColors) {
        this.numColors = numColors;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public boolean isDuplesAllowed() {
        return duplesAllowed;
    }

    public void setDuplesAllowed(boolean duplesAllowed) {
        this.duplesAllowed = duplesAllowed;
    }

    public static SettingsContainer getDefaultSettings () {
        return new SettingsContainer(DEFAULT_NUM_GUESSES, DEFAULT_NUM_COLORS, DEFAULT_BOARD_WIDTH, DEFAULT_DUPLES_ALLOWED);
    }
}
