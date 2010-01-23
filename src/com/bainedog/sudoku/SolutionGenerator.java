/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author baine
 */
public class SolutionGenerator implements Iterable<Solution> {

    private final static String ROW = "row";
    private final static String COLUMN = "column";
    private final static String NUMBER = "number";
    private final static String BOX = "box";

    private final DancingLinks.Column[] columns;
    private final DancingLinks.Column h = DancingLinks.Column.header();
    private final DancingLinks dancingLinks;

    public SolutionGenerator(int[][] cells) {
        int length = cells.length;
        columns = new DancingLinks.Column[4 * length * length];
        this.initializeColumns(length);
        this.initializeRows(cells);
        dancingLinks = DancingLinks.iterativeDancingLinks(h);
    }

    private void initializeColumns(int length) {
        int index = 0;

        String formatString = "%s=%d,%s=%d";

        // row constraints
        for (int i = 0; i < length; i++) {
            for (int n = 0; n < length; n++) {
                columns[index++] = new DancingLinks.Column(
                        String.format(formatString, ROW, i, NUMBER, n), h);
            }
        }

        // column constraints
        for (int j = 0; j < length; j++) {
            for (int n = 0; n < length; n++) {
                columns[index++] = new DancingLinks.Column(
                        String.format(formatString, COLUMN, j, NUMBER, n), h);
            }
        }

        // cell constraints
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                columns[index++] = new DancingLinks.Column(
                        String.format(formatString, ROW, i, COLUMN, j), h);
            }
        }

        // box constraints
        for (int b = 0; b < length; b++) {
            for (int n = 0; n < length; n++) {
                columns[index++] = new DancingLinks.Column(
                        String.format(formatString, BOX, b, NUMBER, n), h);

            }
        }
    }
    
    private void initializeRows(int[][] cells) {
        int length = cells.length;
        int order = (int) Math.round(Math.sqrt(length));
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                for (int n = 0; n < length; n++) {
                    if (cells[i][j] == -1 || cells[i][j] == n) {
                        DancingLinks.Column rowConstraint = columns[i * length + n];
                        DancingLinks.Node row = new DancingLinks.Node(rowConstraint);

                        DancingLinks.Column columnConstraint = columns[length * length + j * length + n];
                        DancingLinks.Node column = new DancingLinks.Node(columnConstraint, row);

                        DancingLinks.Column cellConstraint = columns[2 * length * length + i * length + j];
                        DancingLinks.Node cell = new DancingLinks.Node(cellConstraint, row);

                        int b = (i / order) * order + j / order;
                        DancingLinks.Column boxConstraint = columns[3 * length * length + b * length + n];
                        DancingLinks.Node box = new DancingLinks.Node(boxConstraint, row);
                    }
                }
            }
        }
    }

    public Iterator<Solution> iterator() {
        return new Iterator<Solution>() {

            public boolean hasNext() {
                return iterator.hasNext();
            }

            public Solution next() {
                return translate(iterator.next());
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
            Iterator<List<List<String>>> iterator = SolutionGenerator.this.dancingLinks.iterator();
        };
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
