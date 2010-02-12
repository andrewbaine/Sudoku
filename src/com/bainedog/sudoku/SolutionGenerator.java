/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import com.bainedog.dlx.Column;
import com.bainedog.dlx.DLX;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author baine
 */
public class SolutionGenerator {

    private final static String ROW = "row";
    private final static String COLUMN = "column";
    private final static String NUMBER = "number";
    private final static String BOX = "box";

    private final DLX dancingLinks;

    public SolutionGenerator(DLX dancingLinks) {
        this.dancingLinks = dancingLinks;
    }

    public Iterable<Solution> solutions(Puzzle puzzle) {

        final Column h = SolutionGenerator.header(puzzle);
        final Iterator<List<List<String>>> iterator = this.dancingLinks.solutions(h).iterator();

        return new Iterable<Solution>() {
            public Iterator<Solution> iterator() {
                return new Iterator<Solution>() {

                    public boolean hasNext() {
                        return iterator.hasNext();
                    }

                    public Solution next() {
                        return translate(iterator.next());
                    }

                    public void remove() {
                        throw new UnsupportedOperationException("Remove not supported.");
                    }

                };
            }

        };
    }

    private static final Column header(Puzzle puzzle) {
        final Column h = Column.header();
        int length = puzzle.getLength();

        Column[] columns = new Column[4 * length * length];

        int index = 0;

        String formatString = "%s=%d,%s=%d";

        // row constraints
        for (int i = 0; i < length; i++) {
            for (int n = 0; n < length; n++) {
                columns[index++] = h.addColumn(String.format(formatString, ROW, i, NUMBER, n));
            }
        }

        // column constraints
        for (int j = 0; j < length; j++) {
            for (int n = 0; n < length; n++) {
                columns[index++] = h.addColumn(String.format(formatString, COLUMN, j, NUMBER, n));
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

        int order = puzzle.getOrder();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                for (int n = 0; n < length; n++) {
                    if (puzzle.get(i, j) == null || puzzle.get(i, j) == n) {
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

    private static Solution translate(List<List<String>> list) {
        int length = (int)Math.round(Math.sqrt(list.size()));
        int[][] cells = new int[length][length];
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (List<String> headers : list) {
            for (String h : headers) {
                String[] tokens = h.split(",");
                for (String token : tokens) {
                    int x = token.indexOf('=');
                    map.put(token.substring(0, x), Integer.parseInt(token.substring(x+1)));
                }
            }
            cells[map.get(ROW)][map.get(COLUMN)] = map.get(NUMBER);
        }
        return new Solution(cells);
    }

}
