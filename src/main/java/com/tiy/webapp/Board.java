package com.tiy.webapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
public class Board {


    private CodeRow secret;

    private List<CodeRow> guesses;
    private List<CompareResult> results;



    public Board (CodeRow secret) {
        this.secret = secret;
        guesses = new ArrayList<>();
        results = new ArrayList<>();
    }

    public void addGuess (CodeRow guess) {
        guesses.add(guess);
        CompareResult result = CodeRow.compareTwoRows(secret, guess);
        results.add(result);
        if (result.getSpotsRight() == secret.getSize()) {
            System.out.println("Game is over");
        }
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
}
