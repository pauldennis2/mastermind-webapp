package com.tiy.webapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
public class BoardTest {

    Board board;
    CodeRow answer;
    @Before
    public void setUp() throws Exception {
        answer = new CodeRow(new Color[]{Color.GREEN, Color.GREEN, Color.BLUE, Color.RED});
        board = new Board(answer);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBoard () {
        board.addGuess(answer);
        assertTrue(board.isWon());
    }
}