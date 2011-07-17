/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku.mains;

import com.bainedog.sudoku.RecursiveSolutionGenerator;
import com.bainedog.sudoku.Sudoku;
import com.bainedog.sudoku.Util;
import com.bainedog.sudoku.puzzle.PuzzleGenerator;

/**
 *
 * @author andrew
 */
public class GeneratePuzzles {

    public static void main(String[] args) {

        PuzzleGenerator g = new PuzzleGenerator(new RecursiveSolutionGenerator());
        for (int i = 0; i < 100; i++) {
            Sudoku s = g.generatePuzzle(3);
            System.out.println(Util.toString(s));
            System.out.println(s.triples.size());
        }
    }

}
