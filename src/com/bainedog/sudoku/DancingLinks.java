/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bainedog.sudoku;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author baine
 */
class DancingLinks implements Iterable<List<List<String>>> {

    private final Stack<Node> o = new Stack<Node>();
    private final BlockingQueue<List<List<String>>> queue =
            new LinkedBlockingQueue<List<List<String>>>(100);
    private final Column h;

    public DancingLinks(Column h) {
        this.h = h;
        new Thread(
            new Runnable() {
                public void run() {
                    try {
                        search();
                        publishPoison();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DancingLinks.class.getName()).log(Level.SEVERE, null, ex);
                        throw new RuntimeException();
                    }
                }
            }
        ).start();
    }

    public Iterator<List<List<String>>> iterator() {
        return new Iterator<List<List<String>>>() {

            public boolean hasNext() {
                advance();
                return !(next == POISON);
            }

            public List<List<String>> next() {
                if (hasNext()) {
                    List<List<String>> result = next;
                    next = null;
                    return result;
                } else {
                    throw new NoSuchElementException();
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            private synchronized void advance() {
                try {
                    if (next == null) {
                        next = queue.take();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(SolutionGenerator.class.getName()).log(Level.SEVERE, null, ex);
                    next = POISON;
                }
            }

            private boolean nextIsPoison() {
                return next == POISON;
            }

            private List<List<String>> next = null;
        };
    }

    private void search() throws InterruptedException {
        if (h.right.equals(h)) {
            publishSolution();
        } else {
            Column c = chooseColumn();
            cover(c);
            for (Node r = c.down; !r.equals(c); r = r.down) {
                o.push(r);
                for (Node j = r.right; !j.equals(r); j = j.right) {
                    cover(j.column);
                }
                search();
                r = o.pop(); c = r.column;
                for (Node j = r.left; !j.equals(r); j = j.left) {
                    uncover(j.column);
                }
            }
            uncover(c);
        }
    }

    private Column chooseColumn() {
        Column c = (Column)h.right;
        int s = c.size;
        for (Column j = (Column)(h.right); !j.equals(h); j = (Column)(j.right)) {
            if (j.size < s) {
                c = j; s = j.size;
            }
        }
        return c;
    }

    private void cover(Column c) {
        c.right.left = c.left; c.left.right = c.right;
        for (Node i = c.down; !i.equals(c); i = i.down) {
            for (Node j = i.right; !j.equals(i); j = j.right) {
                j.down.up = j.up; j.up.down = j.down;
                j.column.size -= 1;
            }
        }
    }

    private void uncover(Column c) {
        for (Node i = c.up; !i.equals(c); i = i.up) {
            for (Node j = i.left; !j.equals(i); j = j.left) {
                j.column.size += 1;
                j.down.up = j; j.up.down = j;
            }
        }
        c.right.left = c; c.left.right = c;
    }

    public static class Node {

        protected Node() {
            this.column = null;
        }

        public Node(Column c) {
            this.column = c;
            Node x = c;
            while (x.down != null) {
                x = x.down;
            }
            x.down = this;
            this.up = x;
        }

        public void linkHorizontally(Node other) {
            this.right = other;
            other.left = this;
        }

        public void linkVertically(Node other) {
            this.down = other;
            other.up = this;
        }

        public void unlinkHorizontally() {
            this.left.right = this.right;
            this.right.left = this.left;
        }

        public void unlinkVertically() {
            this.down.up = this.up;
            this.up.down = this.down;
        }

        public void relinkHorizontally() {
            this.left.right = this;
            this.right.left = this;
        }

        public void relinkVertically() {
            this.up.down = this.down;
            this.down.up = this.up;
        }

        Node left;
        Node right;
        Node up;
        Node down;
        final Column column;
    }

    public static class Column extends Node {

        public Column(String name) {
            super();
            this.name = name;
        }

        public static Column header() {
            return new Column(null);
        }

        int size = 0;
        final String name;

        void close() {
            Node n = this;
            while (n.down != null) {
                n = n.down;
            }
            n.linkVertically(this);
        }
    }

    private void publishSolution() throws InterruptedException {
        List<List<String>> solution = new LinkedList<List<String>>();
        for (Node r : o) {
            List<String> row = new LinkedList<String>();
            row.add(r.column.name);
            for (Node i = r.right; i != r; i = i.right) {
                row.add(i.column.name);
            }
            solution.add(row);
        }
        queue.put(solution);
    }

    private void publishPoison() throws InterruptedException {
        queue.put(POISON);
    }

    private final List<List<String>> POISON = new ArrayList<List<String>>();
}
