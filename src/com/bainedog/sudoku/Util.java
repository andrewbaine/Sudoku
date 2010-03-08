/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author baine
 */
public class Util {

    public static String toString(Sudoku sudoku) {
        int order = sudoku.getOrder();
        StringBuilder str = new StringBuilder();
        String breakString = breakString(order);
        for (int i = 0; i < order * order; i++) {
            if (i % order == 0) {
                    str.append(breakString);
                    
            }
            for (int j = 0; j < order * order; j++) {
                if (j % order == 0) {
                    str.append("| ");
                }
                int index = sudoku.get(i, j);
                char c = index < 0 ? ' ' : CHARS.charAt(index);
                str.append(c).append(' ');

            }
            str.append("|\n");
        }
        str.append(breakString);
        return str.toString();
    }

    private static String breakString(int order) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < order; i++) {
            result.append("+-");
            for (int j = 0; j < order; j++) {
                result.append("--");
            }
        }
        result.append("+\n");
        return result.toString();
    }

    private static Puzzle readPuzzle(InputStream in, char blank, char separator) {
        Map <Character, Integer> symbols = new HashMap<Character, Integer>();
        Scanner scanner = new Scanner(in);

        List<String> lines = new ArrayList<String>();
        int count = 0;
        while (scanner.hasNextLine()) {
           String line = scanner.nextLine();
           lines.add(scanner.nextLine());
        }


        int[][] cells = new int[lines.size()][lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            String[] strings = lines.get(i).split(Character.toString(separator));

        }

        return new Puzzle(cells);
    }

    private static final String CHARS = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
