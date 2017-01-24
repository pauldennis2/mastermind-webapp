package com.tiy.webapp;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
@RestController
public class MastermindRestController {


    //Board board;

    public static final int ROW_SIZE = 4;

    @RequestMapping(path="/board.json", method = RequestMethod.GET)
    public List<CodeRow> getBoardJson (HttpSession session) {
        Board board = (Board) session.getAttribute("board");
        if (board == null) {
            SettingsContainer settings = (SettingsContainer)session.getAttribute("settings");
            int boardWidth = settings.getBoardWidth();
            boolean duplesAllowed = settings.isDuplesAllowed();
            int numColors = settings.getNumColors();
            board = new Board(new CodeRow(new Random(), boardWidth, duplesAllowed, numColors));
            session.setAttribute("board", board);
        }
        return board.getGuesses();
    }

    @RequestMapping(path="/submit-move.json", method = RequestMethod.POST)
    public CompareResult submitMove (HttpSession session, @RequestBody String[] simpleRow) {
        Board board = (Board) session.getAttribute("board");
        if (board == null) {
            SettingsContainer settings = (SettingsContainer)session.getAttribute("settings");
            int boardWidth = settings.getBoardWidth();
            boolean duplesAllowed = settings.isDuplesAllowed();
            int numColors = settings.getNumColors();
            board = new Board(new CodeRow(new Random(), boardWidth, duplesAllowed, numColors));
            session.setAttribute("board", board);
        }

        Color[] colors = new Color[simpleRow.length];
        for (int index = 0; index < simpleRow.length; index++) {
            colors[index] = Color.valueOf(simpleRow[index].toUpperCase());
        }

        CodeRow codeRow = new CodeRow(colors);

        board.addGuess(codeRow);
        System.out.println("Secret = " + board.getSecret());
        return CodeRow.compareTwoRows(board.getSecret(), codeRow);
    }

    @RequestMapping(path="/save-settings.json", method = RequestMethod.POST)
    public SettingsContainer saveSettings (HttpSession session, @RequestBody SettingsContainer settings) {
        session.setAttribute("settings", settings);
        return (SettingsContainer)session.getAttribute("settings");
    }

    @RequestMapping(path="/get-settings.json", method = RequestMethod.GET)
    public SettingsContainer getSettings (HttpSession session) {
        SettingsContainer settings = (SettingsContainer) session.getAttribute("settings");
        if (settings == null) {
            settings = SettingsContainer.getDefaultSettings();
            session.setAttribute("settings", settings);
        }
        return settings;
    }
}
