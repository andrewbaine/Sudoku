/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.dlx;

import java.util.List;

/**
 *
 * @author andrew
 */
public interface DLX {

    Iterable<List<List<String>>> solutions(Column h);
    
}
