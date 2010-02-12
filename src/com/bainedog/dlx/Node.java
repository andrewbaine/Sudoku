/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bainedog.dlx;

/**
 *
 * @author andrew
 */
public class Node {

    protected Node left;
    protected Node right;
    protected Node up;
    protected Node down;
    protected Column column;
    protected boolean visited;

    Node() {
        this.left = this;
        this.right = this;
        this.up = this;
        this.down = this;
        this.column = null;
    }

    Node(Column c) {
        this();
        this.column = c;
        c.addAtBottom(this);
    }

    public Node(Column c, Node row) {
        this(c);
        row.addAtRight(this);
    }

    protected void addAtRight(Node node) {
        this.left.right = node;
        node.right = this;
        node.left = this.left;
        this.left = node;
    }

    public void eachCoverColumn() {
        for (Node j = this.right; this != j; j = j.right) {
            j.column.cover();
        }

    }

    public void eachUncoverColumn() {
        for (Node j = this.left; this != j; j = j.left) {
            j.column.uncover();
        }
    }
}
