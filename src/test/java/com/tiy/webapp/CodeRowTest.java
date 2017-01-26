package com.tiy.webapp;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Paul Dennis on 1/19/2017.
 */
public class CodeRowTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testCompareTwoRows() throws Exception {
        //Color[] secret = {Color.BLUE, Color.GREEN, Color.GREEN, Color.RED};
        CodeRow secret = new CodeRow(new Color[]{Color.BLUE, Color.GREEN, Color.GREEN, Color.RED});
        CodeRow guess = new CodeRow(new Color[]{Color.BLUE, Color.ORANGE, Color.RED, Color.GREEN});
        CompareResult result = CodeRow.compareTwoRows(secret, guess);
        assertEquals(1, result.getSpotsRight());
        assertEquals(2, result.getColorsRight());
        guess = new CodeRow(new Color[]{Color.BLUE, Color.GREEN, Color.GREEN, Color.RED});
        result = CodeRow.compareTwoRows(secret, guess);
        assertEquals(4, result.getSpotsRight());
        assertEquals(0, result.getColorsRight());
        guess = new CodeRow(new Color[]{Color.GREEN, Color.BLUE, Color.RED, Color.GREEN});
        result = CodeRow.compareTwoRows(secret, guess);
        assertEquals(0, result.getSpotsRight());
        assertEquals(4, result.getColorsRight());
    }

    @Test
    public void testDuplesInGuessNoDuplesInSecret() {
        CodeRow secret = new CodeRow(new Color[]{Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW});
        CodeRow guess = new CodeRow(new Color[]{Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW});
        CompareResult result = CodeRow.compareTwoRows(secret, guess);
        assertEquals(1, result.getSpotsRight());
        assertEquals(0, result.getColorsRight());
        guess = new CodeRow(new Color[]{Color.RED, Color.RED, Color.BLUE, Color.BLUE});
        result = CodeRow.compareTwoRows(secret, guess);
        assertEquals(0, result.getSpotsRight());
        assertEquals(2, result.getColorsRight());
        //make sure order doesn't matter
        result = CodeRow.compareTwoRows(guess, secret);
        assertEquals(0, result.getSpotsRight());
        assertEquals(2, result.getColorsRight());
        guess = new CodeRow(new Color[]{Color.BLUE, Color.BLUE, Color.RED, Color.YELLOW});
        result = CodeRow.compareTwoRows(secret, guess);
        assertEquals(3, result.getSpotsRight());
        assertEquals(0, result.getColorsRight());
        result = CodeRow.compareTwoRows(guess, secret);

        assertEquals(3, result.getSpotsRight());
        assertEquals(0, result.getColorsRight());
    }

    @Test
    public void testRandomBoard () {
        Random random = new Random();
        System.out.println("No duplicates allowed:");
        for (int index = 0; index < 10; index++) {
            CodeRow codeRow = new CodeRow(random, 4, false, 6);
            System.out.println(codeRow);
            Set<Color> colors = new HashSet<>();
            for (Color color : codeRow.getColors()) {
                //Set will return false if it cannot add the item because it's already contained
                assertTrue(colors.add(color));
            }
        }
    }

    @Test
    public void testCodeRowJsonSerialization () {
        CodeRow test = new CodeRow(new Random(), 4, false, 6);
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(test);
        System.out.println(jsonString);
    }

    /*public String jsonSave () {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(this);
        return jsonString;
    }

    public static PaintRecord jsonRestore(String jsonString) {
        JsonParser listParser = new JsonParser();
        PaintRecord record = listParser.parse(jsonString, PaintRecord.class);
        return record;
    }*/

}