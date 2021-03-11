package com.hausontech.hrs.daoImpl.engine.thread;

import java.util.Vector;

import com.hausontech.hrs.api.engine.IRunnableQueue;
import com.hausontech.hrs.api.engine.IThreadManager;

public class RunnableQueue {

    private static IThreadManager threadManager = ThreadManager.getInstance();
    private static _RunnableQueue runQueue = new _RunnableQueue();
    private static Vector theQueue = new Vector();
    private static int priority = Thread.MIN_PRIORITY;

    private RunnableQueue() {
    }

    public static IRunnableQueue getInstance() {
        return runQueue;
    }

    private static class Dispatcher implements Runnable {

        public void run () {
            Runnable runnable;

            while(true) {
                synchronized(theQueue) {
                    while (theQueue.size() == 0) {
                        try {
                            theQueue.wait();
                        }
                        catch (InterruptedException e) {
                        }
                        catch (Exception e) {
                        }
                    }

                    runnable = (Runnable) theQueue.remove(0);
                }

                threadManager.run(runnable, priority);
            }
        }
    }

    private static class _RunnableQueue implements IRunnableQueue {

        public _RunnableQueue() {
            threadManager.run (new Dispatcher(), Thread.MIN_PRIORITY);
        }

        public void enqueue(Runnable runnable, int priority) {
            RunnableQueue.priority = priority;
            enqueue(runnable);
        }

        public void enqueue(Runnable runnable) {
            synchronized(theQueue) {
                theQueue.addElement(runnable);
                theQueue.notifyAll();
            }
        }
    }

    //
    public static void main (String[] args) {
        IRunnableQueue rq = RunnableQueue.getInstance();
        int i, n = 100;

        for (i = 0; i < n; ++i) {
            System.out.println("Test run #" + i);
            rq.enqueue(new Runnable() {
                public void run () {
                    long t = (long)(Math.random() * 3000) + 1000;
                    try {
                        Thread.sleep (t);
                    }
                    catch (Exception e) {
                    }
                    System.out.println("Hello, world.-" + Thread.currentThread());
                }
            });

            try {
                Thread.sleep((long)(Math.random() * 1000));
            }
            catch (Exception e) {}
        }

        try {
            Thread.sleep (10000);
        }
        catch (Exception e) {}

        Thread.currentThread().getThreadGroup().list();
        System.out.flush ();

    }

}