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
        int[][] cells = new int[][] {
            {-1, 7,-1,-1, 3, 6,-1,-1,-1},
            { 5,-1,-1,-1,-1,-1,-1, 8, 6},
            {-1,-1,-1, 5, 9,-1, 4,-1,-1},

            { 1,-1,-1, 2,-1,-1,-1, 6,-1},
            { 0,-1, 2,-1,-1,-1, 8,-1, 1},
            {-1, 5,-1,-1,-1, 3,-1,-1, 7},

            {-1,-1, 8,-1, 6, 5,-1,-1,-1},
            { 6, 9,-1,-1,-1,-1,-1,-1, 8},
            {-1,-1,-1, 7, 8,-1,-1, 2,-1}
        };
        for (Solution s : new SolutionGenerator(cells)) {
            for (int i = 0; i < 9; i++) {
                String line = "";
                for (int j = 0; j < 9; j++) {
                    line += s.get(i,j) + " ";
                }
                System.out.println(line);
            }
            System.out.println();
        }
        System.out.println("Done");
        System.exit(0);
    }
}
