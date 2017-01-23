package com.tiy.webapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
public class Board {

    public static final int DEFAULT_NUM_GUESSES = 10;
    public static final int DEFAULT_SIZE = 4;

    private CodeRow secret;

    private List<CodeRow> guesses;
    private List<CompareResult> results;

    private boolean isWon;

    int maxGuesses;
    int size;

    public Board (CodeRow secret) {
        this.secret = secret;
        guesses = new ArrayList<>();
        results = new ArrayList<>();
        maxGuesses = DEFAULT_NUM_GUESSES;
        size = DEFAULT_SIZE;
    }

    public void addGuess (CodeRow guess) {
        guesses.add(guess);
        if (guess.getSize() != size) {
            throw new AssertionError("Size mismatch. Trying to add guess with size *" + guess.getSize() +
                    "*, but expected size was *" + size + "*.");
        }
        CompareResult result = CodeRow.compareTwoRows(secret, guess);
        results.add(result);
        if (result.getSpotsRight() == secret.getSize()) {
            System.out.println("Game is over");
            isWon = true;
        }
    }

    public boolean isWon() {
        return isWon;
    }

    public CodeRow getSecret() {
        return secret;
    }

    public List<CodeRow> getGuesses() {
        return guesses;
    }

    public List<CompareResult> getResults() {
        return results;
    }

    public int getMaxGuesses() {
        return maxGuesses;
    }
}
