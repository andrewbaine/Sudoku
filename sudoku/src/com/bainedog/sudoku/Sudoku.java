/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    public Sudoku(int order) {
        this.order = order;
        this.triples = new HashSet<Triple>();
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

    public final Set<Triple> triples;
    public final int order;

    public static class Triple {

        private static Map<Integer, Map<Integer, Map<Integer, Triple>>> cache = 
                new HashMap<Integer, Map<Integer, Map<Integer, Triple>>>();

        public static Triple triple(int r, int c, int n) {
            Map<Integer, Map<Integer, Triple>> row = cache.get(r);
            if (row == null) {
                row = new HashMap<Integer, Map<Integer, Triple>>();
                cache.put(r, row);
            }
            Map<Integer, Triple> col = row.get(c);
            if (col == null) {
                col = new HashMap<Integer, Triple>();
                row.put(c, col);
            }
            Triple t = col.get(n);
            if (t == null) {
                t = new Triple(r, c, n);
                col.put(n, t);
            }
            return t;
        }

        private Triple(int row, int column, int number) {
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
