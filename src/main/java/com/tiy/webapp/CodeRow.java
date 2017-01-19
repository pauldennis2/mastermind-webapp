package com.tiy.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
public class CodeRow {

    private static boolean duplicateColorsAllowed = false;
    private Color[] colors;

    public CodeRow (Random random, int size) {
        colors = new Color[size];
        int numColors = Color.values().length;
        if (duplicateColorsAllowed) {
            for (int index = 0; index < size; index++) {
                int rand = random.nextInt(numColors);
                colors[index] = Color.values()[rand];
            }
        } else {
            boolean[] colorUsed = new boolean[Color.values().length];
            Arrays.fill(colorUsed, false);
            for (int index = 0; index < size; index++) {
                int rand = random.nextInt(numColors);
                while (colorUsed[rand]) {
                    rand = random.nextInt(numColors);
                }
                colors[index] = Color.values()[rand];
                colorUsed[rand] = true;
            }
        }
    }

    public CodeRow (Color[] colors) {
        this.colors = colors;
    }

    public Color getColor (int index) {
        return colors[index];
    }

    public Color[] getColors () {
        return colors;
    }

    public static CompareResult compareTwoRows (CodeRow secret, CodeRow guess) {
        int colorsRight = 0;
        int spotsRight = 0;
        if (secret.getColors().length != guess.getColors().length) {
            throw new AssertionError("Cannot compare rows if they don't match in size");
        }
        Color[] secretColors = secret.getColors();
        Color[] guessColors = guess.getColors();

        List<Color> noSpotMatchSecretColors = new ArrayList<>();
        List<Color> noSpotMatchGuessColors = new ArrayList<>();
        for (int index = 0; index < secretColors.length; index++) {
            if (secretColors[index] == guessColors[index]) {
                spotsRight++;
            } else {
                noSpotMatchGuessColors.add(guessColors[index]);
                noSpotMatchSecretColors.add(secretColors[index]);
            }
        }
        /*int loopTimes = 0;
        for (Color color : noSpotMatchGuessColors) {
            loopTimes++;
            System.out.println("looptimes = " + loopTimes);
            if (noSpotMatchSecretColors.contains(color)) {
                colorsRight++;
                noSpotMatchGuessColors.remove(color);
                noSpotMatchSecretColors.remove(color);
            }
        }*/

        for (int index = 0; index < noSpotMatchGuessColors.size(); index++) {
            Color color = noSpotMatchGuessColors.get(index);
            if (noSpotMatchSecretColors.contains(color)) {
                colorsRight++;
                noSpotMatchGuessColors.remove(color);
                noSpotMatchSecretColors.remove(color);
                index--;
            }
        }
        return new CompareResult(colorsRight, spotsRight);
    }

    public static boolean duplicateColorsAllowed() {
        return duplicateColorsAllowed;
    }

    public static void setDuplicateColorsAllowed(boolean duplicateColorsAllowed) {
        CodeRow.duplicateColorsAllowed = duplicateColorsAllowed;
    }

    public int getSize () {
        return colors.length;
    }

    @Override
    public String toString () {
        String response = "";
        for (Color color : colors) {
            response += " " + color;
        }
        return response;
    }
}