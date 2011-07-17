/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.dlx;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author andrew
 */

public class IterativeDancingLinks extends DancingLinks {

    @Override
    protected void search(DancingLinksHelper helper) throws InterruptedException {
        Set<Node> visited = new HashSet<Node>();
        Stack<Node> stack = new Stack<Node>();
        stack.push(helper.chooseColumn());
        while (!stack.empty()) {
            Node x = stack.pop();
            if (x instanceof Column) {
                Column c = (Column)x;
                if (visited.remove(c)) {
                    c.uncover();
                } else {
                    visited.add(c);
                    c.cover();
                    stack.push(c);
                    for (Node r = c.up; r != c; r = r.up) {
                        stack.push(r);
                    }
                }
            } else {
                if (visited.remove(x)) {
                    Node r = helper.o.pop();
                    r.eachUncoverColumn();
                } else {
                    visited.add(x);
                    Node r = x;
                    helper.o.push(r);
                    r.eachCoverColumn();
                    stack.push(r);
                    if (helper.h.right == helper.h) {
                        helper.publishSolution();
                    } else {
                        stack.push(helper.chooseColumn());
                    }
                }
            }
        }
        helper.publishPoison();
    }

}
