/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import com.bainedog.dlx.Column;
import com.bainedog.dlx.DLX;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author baine
 */
public abstract class SolutionGenerator {

    private final static String ROW = "row";
    private final static String COLUMN = "column";
    private final static String NUMBER = "number";
    private final static String BOX = "box";

    private final DLX dancingLinks;

    public SolutionGenerator(DLX dancingLinks) {
        this.dancingLinks = dancingLinks;
    }

    public void halt() {
        this.dancingLinks.halt();
    }

    public Iterable<Sudoku> solutions(Sudoku puzzle) {

        final Column h = SolutionGenerator.header(puzzle);
        final Iterator<List<List<String>>> iterator = this.dancingLinks.solutions(h).iterator();

        return new Iterable<Sudoku>() {
            public Iterator<Sudoku> iterator() {
                return new Iterator<Sudoku>() {

                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    public Sudoku next() {
                        return translate(iterator.next());
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("Remove not supported.");
                    }

                };
            }

        };
    }

    private static final Column header(Sudoku puzzle) {
        final Column h = Column.header();
        int length = puzzle.order * puzzle.order;

        Column[] columns = new Column[4 * length * length];

        int index = 0;

        String formatString = "%s=%d,%s=%d";

        // row constraints
        for (int i = 0; i < length; i++) {
            for (int n = 0; n < length; n++) {
                columns[index++] = h.addColumn("");
            }
        }

        // column constraints
        for (int j = 0; j < length; j++) {
            for (int n = 0; n < length; n++) {
                columns[index++] = h.addColumn("");
            }
        }

        // cell constraints
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                columns[index++] = h.addColumn(String.format(formatString, ROW, i, COLUMN, j));
            }
        }

        // box constraints
        for (int b = 0; b < length; b++) {
            for (int n = 0; n < length; n++) {
                columns[index++] = h.addColumn(
                        String.format(formatString, BOX, b, NUMBER, n));

            }
        }

        int order = puzzle.order;

        Integer[][] grid = new Integer[length][length];
        for (Sudoku.Triple t : puzzle.triples) {
            grid[t.row][t.column] = t.number;
        }

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                for (int n = 0; n < length; n++) {
                    if (grid[i][j] == null || grid[i][j] == n) {
                        Column rowConstraint = columns[i * length + n];
                        Column columnConstraint = columns[length * length + j * length + n];
                        Column cellConstraint = columns[2 * length * length + i * length + j];
                        int b = (i / order) * order + j / order;
                        Column boxConstraint = columns[3 * length * length + b * length + n];
                        Column.stitchRow(rowConstraint, columnConstraint, cellConstraint, boxConstraint);
                    }
                }
            }
        }

        return h;
    }

    private static Sudoku translate(List<List<String>> list) {
        Set<Sudoku.Triple> triples = new HashSet<Sudoku.Triple>();
        int length = (int)Math.round(Math.sqrt(list.size()));
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (List<String> headers : list) {
            for (String h : headers) {
                String[] tokens = h.split(",");
                for (String token : tokens) {
                    int x = token.indexOf('=');
                    if (x > -1) {
                        map.put(token.substring(0, x), Integer.parseInt(token.substring(x+1)));
                    }
                }
            }
            triples.add(Sudoku.Triple.triple(map.get(ROW), map.get(COLUMN), map.get(NUMBER)));
        }
        return new Sudoku(triples);
    }

}
