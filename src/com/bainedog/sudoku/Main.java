/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import com.bainedog.dlx.IterativeDancingLinks;
import com.bainedog.dlx.RecursiveDancingLinks;
import java.util.Iterator;

/**
 *
 * @author baine
 */
public class Main {

    public static void main(String[] args) {

        SolutionGenerator solutionGenerator = new SolutionGenerator(new IterativeDancingLinks());

        for (Solution s : solutionGenerator.solutions(new Puzzle(cells))) {
            System.out.println(Util.toString(s));
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
            {-1, 1,-1,   -1,-1, 0,   -1,-1,-1},
            {-1,-1, 4,   -1,-1,-1,   -1, 0,-1},
            {-1,-1,-1,    3, 8,-1,   -1,-1,-1},

            { 8, 3, 0,   -1,-1,-1,    6, 5,-1},
            {-1,-1,-1,   -1,-1,-1,   -1,-1,-1},
            {-1, 7, 6,   -1,-1,-1,    3, 2, 0},

            {-1,-1,-1,   -1, 5, 3,   -1,-1,-1},
            {-1, 8,-1,   -1,-1,-1,    2,-1,-1},
            { 2,-1, 7,    1,-1,-1,   -1, 4,-1}
        };
}
