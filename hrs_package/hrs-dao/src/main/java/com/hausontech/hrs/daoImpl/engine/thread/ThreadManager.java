package com.hausontech.hrs.daoImpl.engine.thread;

import java.util.LinkedList;

import com.hausontech.hrs.api.engine.IThreadManager;

public class ThreadManager {

    public static final String MAX_THREADS_KEY = "threads.max";
    public static int defPriority = Thread.NORM_PRIORITY;
    public static int maxThreads = 50;

    private static Object lockObj = new Object();
    private static LinkedList freeQ = new LinkedList();
    private static _ThreadManager threadManager = new _ThreadManager();
    private static ThreadGroup threadGrp = new ThreadGroup(ThreadManager.class.getName());
    private static int idCounter = 0;   // unique ID for each RunnableAdapter obj.

    static {
        String s = System.getProperty(MAX_THREADS_KEY);
        if (s != null)
            maxThreads = Integer.parseInt(s);
    }

    private ThreadManager() {
    }

    public static IThreadManager getInstance() {
        return threadManager;
    }
    /**Get the thread group */
    public static ThreadGroup getThreadGroup() {
        return threadGrp;
    }

    /*
     * Mark the RunnableAdapter as free again.
     * The RunnableAdapter is moved from the busy queue to free queue.
     * This is called by a RunnableAdapter after executing a Runnable object.
     *
     * @param   runAdapter   The Runnable Adapter that is to be marked as free.
     */
    static void markDone (RunnableAdapter runAdapter) {
        markDone(runAdapter, false);
    }

    static void markDone(RunnableAdapter runAdapter, boolean threadStopped) {
        if (threadStopped) {
            runAdapter = newRunnableAdapter(runAdapter.getId().intValue());
        }

        if (runAdapter != null) {
            synchronized(freeQ) {
                freeQ.addLast(runAdapter);
                freeQ.notify();
            }
        }
        else {
            synchronized(lockObj) {
                ++maxThreads;   // compensate for lost thread
            }
        }
    }

    /*
     * An internal method for feteching the first free RunnableAdapter/thread from
     * the free queue. Grow the free queue if necessary. The fetched free
     * RunnableAdapter is put on the busy queue.
     *
     * It is important to follow the the locking sequence of (this object)->(freeQ)
     * to avoid deadlock.
     */
    private static RunnableAdapter getRunnableAdapter() {
        RunnableAdapter runAdapter = null;

        synchronized(freeQ) {
            if (freeQ.size() > 0)
                runAdapter = (RunnableAdapter) freeQ.removeFirst();
        }

        if (runAdapter == null) {
            boolean toCreate = true;

            synchronized(lockObj) {
                if (idCounter < maxThreads)
                    ++idCounter;
                else
                    toCreate = false;
            }

            if (toCreate) {
                runAdapter = newRunnableAdapter(idCounter);
                //Thread.yield();
            }

            if (runAdapter == null)
                runAdapter = waitForRunnableAdapter();
        }

        return runAdapter;
    }

    private static RunnableAdapter waitForRunnableAdapter() {
        RunnableAdapter runAdp = null;

        synchronized(freeQ) {
            while (freeQ.size() == 0) {
                try {
                    freeQ.wait();
                }
                catch (InterruptedException e) {
                }
            }

            runAdp = (RunnableAdapter) freeQ.removeFirst();
        }

        return runAdp;
    }

    private static RunnableAdapter newRunnableAdapter(int id) {
        RunnableAdapter runAdp = null;
        try {
            runAdp = new RunnableAdapter (new Long(id));
            Thread t = new Thread(threadGrp, runAdp);
            t.setDaemon(true);
            t.start();
        }
        catch (Throwable e) {
            e.printStackTrace();
        }

        return runAdp;
    }

    private static class _ThreadManager implements IThreadManager {
        public void run (Runnable objToRun) {
            run (objToRun, defPriority);
        }

        public void run (Runnable objToRun, int pri) {
            RunnableAdapter runAdapter = getRunnableAdapter();
            runAdapter.setRunnable(objToRun, pri);
        }
    }
}
