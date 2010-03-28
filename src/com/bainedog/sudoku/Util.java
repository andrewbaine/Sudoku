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
        int order = sudoku.order;
        int length = order * order;
        Integer[][] cells = new Integer[length][length];
        for (Sudoku.Triple t : sudoku.triples) {
            cells[t.row][t.column] = t.number;
        }

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
                Integer index = cells[i][j];
                char c = index == null ? ' ' : CHARS.charAt(index);
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

    private static final String CHARS = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
