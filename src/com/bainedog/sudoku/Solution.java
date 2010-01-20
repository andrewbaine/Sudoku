/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import java.util.BitSet;

/**
 *
 * @author baine
 */
public class Solution {

    public Solution(int[][] cells) {
        validate(cells);
        this.cells = new int[cells.length][cells.length];
        for (int i = 0;i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                this.cells[i][j] = cells[i][j];
            }
        }
    }

    public int getOrder() {
        return (int)(Math.sqrt(this.cells.length));
    }

    public int get(int i, int j) {
        return cells[i][j];
    }

    private void validate(int[][] cells) {
        int height = cells.length;
        int width = cells[0].length;

        if (height != width) {
            throw new NotSquareException();
        }

        double x = Math.sqrt(height);
        double ord = Math.floor(x);

        if (ord != x) {
            throw new NotOrderedException();
        }

        BitSet numbers = new BitSet();
        numbers.set(0, cells.length);

        int order = (int)ord;
        int mask = (1 << cells.length) - 1;

        // check rows
        for (int i = 0; i < cells.length; i++) {
            int rowBits = 0;
            int columnBits = 0;
            int boxBits = 0;
            for (int j = 0; j < cells.length; j++) {
                rowBits |= 1 << cells[i][j];
                columnBits |= 1 << cells[j][i];
                boxBits |= 1 << 
                        (cells[(i / order) * order + j / order][(i % order) * order + j % order]);
            }
            if (rowBits != mask) {
                throw new IllegalRowException(
                        String.format("Row %d produces mask %s instead of %s", i,
                                Integer.toString(rowBits, 2), Integer.toString(mask, 2)));
            }
            if (columnBits != mask) {
                throw new IllegalColumnException();
            }
            if (boxBits != mask) {
                throw new IllegalBoxException();
            }
        }
    }

    private int[][] cells;

    class NotSquareException extends IllegalArgumentException {};
    class NotOrderedException extends IllegalArgumentException {};
    class IllegalRowException extends IllegalArgumentException {

        private IllegalRowException(String string) {
            super(string);
        }
    };
    class IllegalColumnException extends IllegalArgumentException {};
    class IllegalBoxException extends IllegalArgumentException {};
}
