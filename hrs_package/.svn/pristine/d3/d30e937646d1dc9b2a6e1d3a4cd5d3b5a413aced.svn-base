package com.hausontech.hrs.daoImpl.engine.thread;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

public class ThreadPool
    extends ThreadGroup
{
    /**
     * Create a new thread pool with at least <code>min</code>
     * and at most <code>max</code> threads in it.
     */
    public ThreadPool(String name, int min, int max)
    {
        super(name);
        init(min, max);
    }

    /**
     * Create a new thread pool with at least <code>min</code>
     * and at most <code>max</code> threads in it.
     */
    public ThreadPool(ThreadGroup parent, String name, int min, int max)
    {
        super(parent, name);
        init(min, max);
    }

    /**
     * Start at least <code>min</code> threads
     */
    private void init(int min, int max)
    {
        if (min < 0 || max < 1 || max < min)
            throw new IllegalArgumentException();

        this.min = min;
        this.max = max;
        for (int i = 0; i < min; ++i)
            startThread();
    }

    /**
     * Schedule <code>runnable</code> to be run <i>asynchronously</i>
     */
    public void run(Runnable runnable)
    {
        if (queue.enqueue(runnable) > THRESHOLD)
            startThread(); // we might start more threads than necessary
                           // is it really a problem?
                           // I don't think so as the number of threads
                           // is limited anyway.
    }

    /**
     * @return the number of requests awaiting to be serviced
     */
    public int getQueueSize()
    {
        return queue.size();
    }

    /**
     * set the priority for the <u>new</u> threads in this pool.
     * <br>The priority of already running are not affected.
     */
    public void setPriority(int pri)
    {
        if (pri < Thread.MIN_PRIORITY || pri > Thread.MAX_PRIORITY
            || pri > getMaxPriority())
        {
            throw new IllegalArgumentException();
        }
        priority = pri;
    }

    /**
     * set the priority boost for the pool
     */
    public void setBoost(int b)
    {
        boost = b;
    }

    /**
     * Start a new thread, but not more than <code>max</code>
     */
    private void startThread()
    {
        synchronized (this)
        {
            if (count >= max)
                return; // don't create more than max threads
                
            ++count;
        }

        int pri = boostThreadPriority();
        try
        {            
            int i = ++id; // not important to synchronize
            Thread t = new Thread(this, new Runner(), "Runner-" + i);
            t.setDaemon(true);
            t.start();
        }
        finally
        {
            setThreadPriority(pri);    
        }
    }

    /** 
     * Should the current thread terminate itself?
     * @return <code>true</code> if there are more than <code>min</code>
     *         threads currently running (including the current one)
     */    
    private synchronized boolean endThread()
    {
        if (count <= min)
            return false; // don't terminate more than min threads
                
        --count;
        return true;
    }

    /**
     * increase current Thread's priority by {@link #boost}
     * @return previous priority, or <code>-1</code> if priority
     *         couldn't or needn't be changed.
     */
    private int boostThreadPriority()
    {
        if (boost <= 0)
            return -1;

        Thread me = Thread.currentThread();
        return setThreadPriority(me, me.getPriority() + boost);
    }

    /**
     * set current thread's priority to <code>pri</code>
     * @return previous priority, or <code>-1</code> if priority
     *         couldn't or needn't be changed.
     */
    private void setThreadPriority(int pri)
    {
        setThreadPriority(Thread.currentThread(), pri);
    }
    
    /**
     * set thread's <code>thread</code> priority to <code>pri</code>
     * @return previous priority, or <code>-1</code> if priority
     *         couldn't or needn't be changed.
     */
    private int setThreadPriority(Thread thread, int pri)
    {
        if (pri < Thread.MIN_PRIORITY)
            pri = Thread.MIN_PRIORITY;
        int maxPri = getMaxPriority();
        if (pri > maxPri)
            pri = maxPri;
            
        try
        {
            int p = thread.getPriority();
            thread.setPriority(pri);
            return p;
        }
        catch (Exception e)
        {
            if (DEBUG)
                e.printStackTrace();
            return -1;
        }
    }
        
    /**
     * The queue of <code>Runnable</code> schedule to be run
     */
    private Queue queue = new Queue();
    
    /**
     * minimum number of threads in the pool
     */
    private int min;
    
    /**
     * maximum number of threads in the pool
     */ 
    private int max;

    /**
     * current number of threads in the pool
     */
    private int count = 0;

    /**
     * priority of the threads in the pool
     */
    private int priority = THREAD_PRIORITY;

    /**
     * thread priority boost
     */
    private int boost = THREAD_BOOST;

    /**
     * id of the last creadted thread
     */
    private int id = 0;

    /**
     * How long to wait for a <code>Runnable</code> to be schedule
     * before terminating a thread
     */
    private final static long TIMEOUT = Long.getLong("com.edocs.threads.timeout", 5).longValue() * 60 * 1000; // 5 min
    
    /**
     * How many <code>Runnable</code> allowed in the <code>queue</code>
     * before creating a new thread
     */
    private final static int THRESHOLD = Integer.getInteger("com.edocs.threads.threshold", 1).intValue();

    /**
     * default thread priority
     */
    private final static int THREAD_PRIORITY = Integer.getInteger("com.edocs.threads.priority", Thread.NORM_PRIORITY).intValue();
    
    /**
     * default thread priority boost
     */
    private final static int THREAD_BOOST = Integer.getInteger("com.edocs.threads.boost", 1).intValue();
    
    private final static boolean DEBUG = Boolean.getBoolean("com.edocs.threads.debug");

    /**
     * <code>Runner</code> executes scheduled <code>Runnable</code> objects
     * @see #run(Runnable)
     */        
    private class Runner
        implements Runnable
    {
        public void run()
        {   
            final Thread me = Thread.currentThread();         
            final String name = me.getName();
            final ClassLoader cl = me.getContextClassLoader();
                        
            Runnable runnable = null;
            while (! Thread.interrupted())
            {
                // reset thread context
                me.setName(name);
                me.setContextClassLoader(cl);                
                
                try
                {
                    setThreadPriority(priority + boost);
                    try
                    {                   
                        runnable = queue.dequeue(TIMEOUT);
                    }
                    finally
                    {
                        setThreadPriority(priority);
                    }
                    
                    if (runnable == null)
                    {
                        // queue has been empty for too long...
                        if (endThread())                            
                            break; // that's it for this thread
                    }
                    else
                    {
                        runnable.run();
                    }
                }
                catch (ThreadDeath t)
                {
                    break;
                }
                catch (Throwable t)
                {
                    if (DEBUG)
                    {
                        if (runnable != null)
                            System.err.println(runnable);
                        t.printStackTrace();
                    }
                }                
            }
                
            setThreadPriority(Thread.MIN_PRIORITY);
        }
    }

    /**
     * A blocking FIFO queue -- {@link #dequeue(long) blocks until a
     * <code>Runnable</code> is queued
     */
    private static class Queue
    {
        /**
         * Add a <code>Runnable</code> object to the queue
         * @return the number of <code>Runnable</code>'s in the queue
         *         <u>after</u> insertion.
         */
        synchronized int enqueue(Runnable runnable)
        {
            elements.addLast(runnable);
            notify();
            return elements.size();
        }

        /**
         * @return block for as long as <code>timeout</code> (in milliseconds)
         *         until a <code>Runnable</code> is queued.
         *         <br>Returns <code>null</code> if none becomes available.
         */
        synchronized Runnable dequeue(long timeout)
            throws InterruptedException
        {
            while (elements.size() == 0)
            {
                wait(timeout);
                break;
            }
            try
            {
                if (elements.size() == 0)
                    return null;
                else
                    return (Runnable) elements.removeFirst();
            }
            catch (NoSuchElementException e)
            {
                throw new Error("Internal Error: " + e);
            }            
        }

        /**
         * @return the number of <code>Runnable</code> in the queue
         */
        synchronized int size()
        {
            return elements.size();
        }
        
        private LinkedList elements = new LinkedList();
    }


    public static void main(String[] args)
        throws Exception
    {
        final long count = 10000000;
                
        Runnable test = new Runnable()
            {
                public void run()
                {
                    long start = System.currentTimeMillis();
                    long j = count / 10;
                    for (long i = 0; i < j; ++i)
                        ;
                    long end = System.currentTimeMillis();
                    System.out.println(j + " in " + (end - start) + " ms");                                        
                    synchronized (ThreadPool.class)
                    {
                        n += j;
                        if (n >= count)
                            ThreadPool.class.notify();
                    }
                }
            };

        ThreadPool pool = new ThreadPool("test", 10, 10);
        long start, end;

        for (int j = 0; j < 5; ++j)
        { 
            System.gc();
            n = 0;
            start = System.currentTimeMillis();
            for (int i = 0; i < 10; ++i)
                new Thread(test).start();
            while (n < count)
            {
                synchronized (ThreadPool.class)
                {
                    ThreadPool.class.wait();
                }
            }
            end = System.currentTimeMillis();        
            System.out.println("\tthreads:\t" + n + " in " + (end - start) + " ms");
                    
            System.gc();
            n = 0;
            start = System.currentTimeMillis();
            for (int i = 0; i < 10; ++i)
                pool.run(test);
            while (n < count)
            {
                synchronized (ThreadPool.class)
                {
                    ThreadPool.class.wait();
                }
            }
            end = System.currentTimeMillis();        
            System.out.println("\tpool:\t\t" + n + " in " + (end - start) + " ms");
        }
    }
    private static long n = 0;

    public static void main1(String[] args)
        throws Exception
    {
        ThreadPool pool = new ThreadPool("test", 2, 5);
        pool.setPriority(Thread.NORM_PRIORITY - 5);
        pool.setBoost(10);

        Runnable test = new Runnable()
            {
                public void run()
                {
                    System.out.println(Thread.currentThread() + " starting");
                    try
                    {
                        Thread.currentThread().sleep(500);
                    }
                    catch(InterruptedException e)
                    {
                    }
                    System.out.println(Thread.currentThread() + " stopping");
                }
            };

        Random r = new Random();
        while (pool.count < pool.max)
        {
            System.out.println(pool.count + " threads in pool (" + pool.min + "/" + pool.max + ")");
            System.out.println(pool.queue.size() + " Runnable in the queue");
            System.out.println("scheduling new Runnable");
            pool.run(test);
            Thread.currentThread().sleep((long)(r.nextDouble() * 300));
        }

        while (pool.queue.size() > 0)
        {
            System.out.println(pool.count + " threads in pool");
            System.out.println(pool.queue.size() + " Runnable in the queue");
        }

        for (int c = pool.count; c > pool.min; )
        {
            if (pool.count == c)
                Thread.currentThread().sleep(10 * 1000);
            else
                c = pool.count;
                
            System.out.println(pool.count + " threads in pool");
        }
    }
}
