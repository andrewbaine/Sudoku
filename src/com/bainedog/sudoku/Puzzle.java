/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

/**
 *
 * @author baine
 */
public class Puzzle extends Sudoku {

    private final int[][] cells;

    public Puzzle(int[][] cells) {
        this.cells = new int[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                this.cells[i][j] = cells[i][j];
            }
        }
        this.validate();
    }

    private void validate() {
        if (cells.length != cells[0].length) {
            throw new IllegalArgumentException("Cells aren't square");
        }
        int order = this.getOrder();
        if (order * order != cells.length) {
            throw new IllegalArgumentException("Puzzle not a valid order");
        }
        int length = this.getLength();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                int n = this.get(i, j);
                if (n >= length) {
                    throw new IllegalArgumentException(
                            "Number " + n + " is too big for a puzzle of length " + length);

                }
            }
        }
    }

    @Override
    public final int get(int i, int j) {
        return cells[i][j];
    }

    @Override
    public final int getOrder() {
        return (int) Math.round(Math.sqrt(cells.length));
    }

}
