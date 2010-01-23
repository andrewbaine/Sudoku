/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

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

    private static final String CHARS = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
