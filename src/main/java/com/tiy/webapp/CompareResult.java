package com.tiy.webapp;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
public class CompareResult {

    private int colorsRight;
    private int spotsRight;

    public CompareResult (int colorsRight, int spotsRight) {
        this.colorsRight = colorsRight;
        this.spotsRight = spotsRight;
    }

    public int getColorsRight() {
        return colorsRight;
    }

    public int getSpotsRight() {
        return spotsRight;
    }
}
