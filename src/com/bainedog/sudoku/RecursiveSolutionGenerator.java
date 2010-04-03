/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import com.bainedog.dlx.RecursiveDancingLinks;

/**
 *
 * @author andrew
 */
public class RecursiveSolutionGenerator extends SolutionGenerator {

    public RecursiveSolutionGenerator() {
        super(new RecursiveDancingLinks());
    }
}
