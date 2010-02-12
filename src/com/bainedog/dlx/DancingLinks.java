/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bainedog.dlx;

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
abstract class DancingLinks implements DLX {

    private static final Logger LOGGER = Logger.getLogger(DancingLinks.class.getName());
    private static final List<List<String>> POISON = new ArrayList<List<String>>();

    public DancingLinks() {
    }

    protected abstract void search(DancingLinksHelper helper) throws InterruptedException;

    public Iterable<List<List<String>>> solutions(final Column h) {

        final DancingLinksHelper helper = new DancingLinksHelper(h);

        new Thread(
                new Runnable() {

                    public void run() {
                        try {
                            search(helper);
                            helper.publishPoison();
                        } catch (InterruptedException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                            throw new RuntimeException();
                        }
                    }
                }).start();

        return new Iterable<List<List<String>>>() {

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
                                next = helper.queue.take();
                            }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(DancingLinks.class.getName()).log(Level.SEVERE, null, ex);
                            next = POISON;
                        }
                    }

                    private boolean nextIsPoison() {
                        return next == POISON;
                    }
                    private List<List<String>> next = null;
                };
            }

        };
    }

    static final class DancingLinksHelper {

        public DancingLinksHelper(Column h) {
            this.h = h;
        }


        void publishSolution() throws InterruptedException {
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

        void publishPoison() throws InterruptedException {
            queue.put(POISON);
        }

        final Column h;
        final Stack<Node> o = new Stack<Node>();
        final BlockingQueue<List<List<String>>> queue = new LinkedBlockingQueue<List<List<String>>>(100);
    }
}
