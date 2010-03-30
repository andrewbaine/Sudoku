/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import java.util.Set;

/**
 *
 * @author baine
 */
public final class Sudoku {

    public Sudoku(Set<Triple> triples) {
        this((int)Math.round(Math.sqrt(Math.sqrt(triples.size()))), triples);
    }

    public Sudoku(int order, Set<Triple> triples) {
        this.order = order;
        this.triples = triples;
    }

    @Override
    public boolean equals(Object other) {

        if (other instanceof Sudoku) {
            Sudoku s = (Sudoku)other;
            return this.triples.size() == s.triples.size()
                    && this.triples.containsAll(s.triples);
        } else {
            return false;
        }
    }

    final Set<Triple> triples;
    public final int order;

    public static class Triple {

        public Triple(int row, int column, int number) {
            this.row = row;
            this.column = column;
            this.number = number;
        }

        public final int row;
        public final int column;
        public final int number;

        @Override
        public String toString() {
            return String.format("(%d, %d, %d)", row, column, number);
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }

        @Override
        public boolean equals(Object other) {
            if (other instanceof Triple) {
                Triple t = (Triple)other;
                return t.row == row && t.column == column && t.number == number;
            } else {
                return false;
            }
        }
    }
    
}
