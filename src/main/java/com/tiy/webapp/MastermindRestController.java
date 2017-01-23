package com.tiy.webapp;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
@RestController
public class MastermindRestController {

    boolean initialized = false;
    Board board;

    public static final int ROW_SIZE = 4;

    @RequestMapping(path="/board.json", method = RequestMethod.GET)
    public List<CodeRow> getBoardJson () {
        if (!initialized) {
            board = new Board(new CodeRow(new Random(), ROW_SIZE));
            initialized = true;
        }
        return board.getGuesses();
    }

    @RequestMapping(path="/submit-move.json", method = RequestMethod.POST)
    //public List<CodeRow> submitMove (@RequestBody CodeRow codeRow) {
    public Integer submitMove (@RequestBody CodeRow codeRow) {
        System.out.println(codeRow);
        board.addGuess(codeRow);
        //return board.getGuesses();
        return 5;
    }

    @RequestMapping(path="/simple-submit.json", method = RequestMethod.POST)
    //public List<CodeRow> simpleSubmit (@RequestBody SimpleRow simpleRow) {
    public CompareResult simpleSubmit (@RequestBody SimpleRow simpleRow) {
        Color color1 = Color.valueOf(simpleRow.first.toUpperCase());
        Color color2 = Color.valueOf(simpleRow.second.toUpperCase());
        Color color3 = Color.valueOf(simpleRow.third.toUpperCase());
        Color color4 = Color.valueOf(simpleRow.fourth.toUpperCase());
        Color[] colors = {color1, color2, color3, color4};
        CodeRow codeRow = new CodeRow(colors);
        board.addGuess(codeRow);
        //return board.getGuesses();
        System.out.println("Secret = " + board.getSecret());
        return CodeRow.compareTwoRows(board.getSecret(), codeRow);
    }
}
