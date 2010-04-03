/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bainedog.dlx;

import java.util.ArrayList;

/**
 *
 * @author andrew
 */
public final class Column extends Node {

    private int size = 0;
    public final String name;

    public Column addColumn(String name) {
        Column result = new Column(name);
        this.addAtRight(result);
        return result;
    }

    private Column(String name) {
        super();
        this.name = name;
    }

    public Column(String name, Column h) {
        this(name);
        h.addAtRight(this);
    }

    public static Column header() {
        return new Column(null);
    }

    public int getSize() {
        return this.size;
    }

    void addAtBottom(Node n) {
        this.up.down = n;
        n.down = this;
        n.up = this.up;
        this.up = n;
        this.size += 1;
    }

    public void cover() {
        this.right.left = this.left;
        this.left.right = this.right;
        for (Node i = this.down; !this.equals(i); i = i.down) {
            for (Node j = i.right; !j.equals(i); j = j.right) {
                j.down.up = j.up;
                j.up.down = j.down;
                j.column.size -= 1;
            }
        }
    }

    public void uncover() {
        for (Node i = this.up; this != i; i = i.up) {
            for (Node j = i.left; !j.equals(i); j = j.left) {
                j.column.size += 1;
                j.down.up = j;
                j.up.down = j;
            }
        }
        this.right.left = this;
        this.left.right = this;
    }

    public static void stitchRow(Column c, Column... columns) {
        Node n = new Node(c);
        for (int i = 0; i < columns.length; i++) {
            new Node(columns[i], n);
        }
    }
}
