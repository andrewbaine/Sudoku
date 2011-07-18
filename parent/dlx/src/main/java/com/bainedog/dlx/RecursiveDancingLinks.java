/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.dlx;

/**
 *
 * @author andrew
 */
public class RecursiveDancingLinks extends DancingLinks {

    @Override
    protected void search(DancingLinksHelper helper) throws InterruptedException {
        if (helper.h.right == helper.h) {
            helper.publishSolution();
        } else {
            Column c = helper.chooseColumn();
            c.cover();
            for (Node r = c.down; r != c; r = r.down) {
                helper.o.push(r);
                r.eachCoverColumn();
                search(helper);
                helper.o.pop();
                r.eachUncoverColumn();
            }
            c.uncover();
        }
    }

}
