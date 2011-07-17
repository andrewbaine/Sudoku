/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import com.bainedog.dlx.IterativeDancingLinks;

/**
 *
 * @author andrew
 */
public class IterativeSolutionGenerator extends SolutionGenerator {

    public IterativeSolutionGenerator() {
        super(new IterativeDancingLinks());
    }
}
