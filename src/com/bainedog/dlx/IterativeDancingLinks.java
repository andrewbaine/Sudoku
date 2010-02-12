/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.dlx;

import java.util.Stack;

/**
 *
 * @author andrew
 */

class IterativeDancingLinks extends DancingLinks {

    @Override
    protected void search(DancingLinksHelper helper) throws InterruptedException {
        Stack<Node> stack = new Stack<Node>();
        stack.push(helper.h.chooseColumn());
        while (!stack.empty()) {
            Node x = stack.pop();
            if (x instanceof Column) {
                Column c = (Column)x;
                if (c.visited) {
                    c.visited = false;
                    c.uncover();
                } else {
                    c.visited = true;
                    c.cover();
                    stack.push(c);
                    for (Node r = c.up; r != c; r = r.up) {
                        stack.push(r);
                    }
                }
            } else {
                if (x.visited) {
                    x.visited = false;
                    Node r = helper.o.pop();
                    r.eachUncoverColumn();
                } else {
                    x.visited = true;
                    Node r = x;
                    helper.o.push(r);
                    r.eachCoverColumn();
                    stack.push(r);
                    if (helper.h.right == helper.h) {
                        helper.publishSolution();
                    } else {
                        stack.push(helper.h.chooseColumn());
                    }
                }
            }
        }
        helper.publishPoison();
    }

}
