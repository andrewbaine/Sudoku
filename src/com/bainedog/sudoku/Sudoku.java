/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

/**
 *
 * @author baine
 */
public abstract class Sudoku {

    public abstract int getOrder();
    public abstract int get(int i, int j);
    public final int getLength() {
        return getOrder() * getOrder();
    }
}
