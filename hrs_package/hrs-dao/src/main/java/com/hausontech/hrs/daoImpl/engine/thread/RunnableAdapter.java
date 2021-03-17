package com.hausontech.hrs.daoImpl.engine.thread;

import java.util.Date;

/**
 * Runnable Adapter - Adapts a Runnable to run within a pre-created thread. This
 * object class is used by ThreadManager.
 * 
 * @version
 * @author Hao-Jen Fu
 */
class RunnableAdapter implements Runnable {
	private Long id;
	private Runnable objToRun = null;
	private int priority;

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            A unique ID of the object.
	 * @param ThreadManager
	 *            A back reference to ThreadManager obj.
	 */
	RunnableAdapter(Long id) {
		this.id = id;
	}

	/**
	 * A method called by ThreadManager, and calls the target Runnable object.
	 * 
	 * The thread blocks/waits until a Runnable object is available and until
	 * notified by the ThreadManager.
	 */
	public synchronized void run() {
		boolean stopThread = false;
		while (true) {
			while (objToRun == null) {
				try {
					wait();
				} catch (InterruptedException e) { // OK
				} catch (Exception e) { // What now?
					System.err.println(this.getClass().getName() + "at " + new Date());
					e.printStackTrace();
				}
			}
			try {
				Thread.currentThread().setPriority(priority);
			} catch (Exception e) {
				System.err.println(this.getClass().getName() + "at " + new Date());
				e.printStackTrace();
			}
			try {
				objToRun.run();
			} catch (ThreadDeath e) {
				stopThread = true;
				throw e;
			} catch (Throwable e) { // we need to protect this thread
				System.err.println("* Error from an async thread *");
				e.printStackTrace();
				// perhaps we need a way to pass status back to the objToRun
			} finally {
				objToRun = null;
				ThreadManager.markDone(this, stopThread); // Make the thread
															// free again
			}
		}
	}

	Long getId() {
		return id;
	}

	/**
	 * This method is called by ThreadManager to <1> dispatch a Runnable object
	 * to run. <2> wake up the blocking thread.
	 */
	synchronized void setRunnable(Runnable objToRun, int pri) {
		this.objToRun = objToRun;
		priority = pri;
		notifyAll();
	}

	public String toString() {
		return "RunnableAdapter[ID = " + id + ", " + Thread.currentThread() + "]";
	}

} // The End

