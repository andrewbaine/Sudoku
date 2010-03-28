/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import com.bainedog.dlx.IterativeDancingLinks;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author baine
 */
public class SolutionTest {

    public SolutionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    
    @Test
    public void testRowConsistency() {
    }

    @Test
    public void testColumnConsistency() {
    }

    @Test
    public void testBoxConsistency() {
    }

    @Test
    public void testP1() {
        try {
            Set<Sudoku.Triple> triples = new HashSet<Sudoku.Triple>();
            int count;
            Scanner in = new Scanner(new File("resources/p1.txt"));
            for (count = 0; in.hasNextLine(); count++) {
                String line = in.nextLine();
                String[] tokens = line.split("\\s+");
                for (int j = 0; j < tokens.length; j++) {
                    String token = tokens[j];
                    if (!token.equals(".")) {
                        int x = Integer.parseInt(token) - 1;
                        triples.add(new Sudoku.Triple(count, j, x));
                    }
                }
            }
            int order = (int) Math.round(Math.sqrt(count));
            Sudoku puzzle = new Sudoku(order, triples);
            System.out.println(Util.toString(puzzle));

            SolutionGenerator g = new SolutionGenerator(new IterativeDancingLinks());
            Iterable<Sudoku> solutions = g.solutions(puzzle);
            System.out.println(Util.toString(solutions.iterator().next()));

        } catch (FileNotFoundException ex) {
            Logger.getLogger(SolutionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
