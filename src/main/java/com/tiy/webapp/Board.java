package com.tiy.webapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
public class Board {

    public static final int DEFAULT_NUM_GUESSES = 10;

    private CodeRow secret;

    private List<CodeRow> guesses;
    private List<CompareResult> results;

    private boolean isWon;

    int maxGuesses;

    public Board (CodeRow secret) {
        this.secret = secret;
        guesses = new ArrayList<>();
        results = new ArrayList<>();
        maxGuesses = DEFAULT_NUM_GUESSES;
    }

    public void addGuess (CodeRow guess) {
        guesses.add(guess);
        if (secret == null) {
            System.out.println("Legit wtf");
        }
        CompareResult result = CodeRow.compareTwoRows(secret, guess);

        if (result.getSpotsRight() == secret.getSize()) {
            System.out.println("Game is over");
            isWon = true;
        }
    }

    public boolean isWon() {
        return isWon;
    }
}
