package com.tiy.webapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/23/2017.
 */
public class SimpleBoard {

    private boolean isWon;

    private List<SimpleCodeRow> guesses;

    private SimpleCodeRow secret;

    public SimpleBoard (SimpleCodeRow secret) {
        this.secret = secret;
        guesses = new ArrayList<>();
    }

    public void addGuess (SimpleCodeRow guess) {
        guesses.add(guess);
        /*CompareResult result = CodeRow.compareTwoRows(secret, guess);
        results.add(result);
        if (result.getSpotsRight() == secret.getSize()) {
            System.out.println("Game is over");
            isWon = true;
        }*/
    }
}
