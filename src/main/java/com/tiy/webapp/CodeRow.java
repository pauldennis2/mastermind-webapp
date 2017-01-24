package com.tiy.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
public class CodeRow {

    private Color[] colors;

    public CodeRow () {

    }

    public CodeRow (Random random, int size, boolean duplicateColorsAllowed, int numColors) {
        colors = new Color[size];
        //int numColors = Color.values().length;
        if (duplicateColorsAllowed) {
            for (int index = 0; index < size; index++) {
                int rand = random.nextInt(numColors);
                colors[index] = Color.values()[rand];
            }
        } else {
            if (size > numColors) {
                throw new AssertionError("Cannot produce a unique combination of size *"
                        + size + "* with only *" + numColors + "* colors to work with.");
            }
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
        System.out.println("Guess = " + guess);
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

    public void setColors(Color[] colors) {
        this.colors = colors;
    }
}
