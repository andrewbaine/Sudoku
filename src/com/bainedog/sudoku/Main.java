/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

/**
 *
 * @author baine
 */
public class Main {

    public static void main(String[] args) {

        for (Solution s : new SolutionGenerator(cells)) {
            int stop = (int)(Math.pow(s.getOrder(), 2));
            for (int i = 0; i < stop; i++) {
                String line = "";
                for (int j = 0; j < stop; j++) {
                    line += s.get(i,j) + " ";
                }
                System.out.println(line);
            }
            System.out.println();
        }
        System.out.println("Done");
        System.exit(0);
    }


    static int[][] cells2 = new int[][] {
            {-1,-1,-1,-1},
            {-1,-1,-1,-1},
            {-1,-1,-1,-1},
            {-1,-1,-1,-1}
        };


    static int[][] cells = new int[][] {
            {-1, 1,-1,   -1,-1, 0,    7,-1, 2},
            {-1,-1, 4,   -1,-1,-1,   -1, 0, -1},
            {-1,-1,-1,    3, 8,-1,   -1,-1,-1},

            { 8, 3, 0,   -1,-1, 4,    6, 5,-1},
            {-1,-1,-1,   -1,-1,-1,   -1,-1,-1},
            {-1, 7, 6,    8,-1,-1,    3, 2, 0},

            {-1,-1,-1,   -1, 5, 3,   -1,-1,-1},
            {-1, 8,-1,   -1,-1,-1,    2,-1,-1},
            { 2,-1, 7,    1,-1,-1,   -1, 4,-1}
        };
}
