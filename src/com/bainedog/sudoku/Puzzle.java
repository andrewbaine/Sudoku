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

    public Puzzle(int[][] cells) {
        super(cells);
        this.validate();
    }

    private void validate() {
        int order = this.getOrder();
        if (order * order != this.getLength()) {
            throw new IllegalArgumentException("Puzzle not a valid order");
        }
        int length = this.getLength();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                Integer n = this.get(i, j);
                if (n != null && n >= length) {
                    throw new IllegalArgumentException(
                            "Number " + n + " is too big for a puzzle of length " + length);

                }
            }
        }
    }

}
