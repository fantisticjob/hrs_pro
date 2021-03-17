package com.hausontech.hrs.daoImpl.engine.thread;
import java.util.*;

public class UnQueuedThreadPool extends Object {
	public ObjectFIFO idleWorkers;
	public ThreadPoolWorker[] workerList;

	public UnQueuedThreadPool(int numberOfThreads) {
		// make sure that it's at least one
		numberOfThreads = Math.max(1, numberOfThreads);

		idleWorkers = new ObjectFIFO(numberOfThreads);
		workerList = new ThreadPoolWorker[numberOfThreads];

		for ( int i = 0; i < workerList.length; i++ ) {
			workerList[i] = new ThreadPoolWorker(idleWorkers);
		}
	}

	public UnQueuedThreadPool(int numberOfThreads,ClassLoader cl) {
		// make sure that it's at least one
		numberOfThreads = Math.max(1, numberOfThreads);

		idleWorkers = new ObjectFIFO(numberOfThreads);
		workerList = new ThreadPoolWorker[numberOfThreads];

		for ( int i = 0; i < workerList.length; i++ ) {
			workerList[i] = new ThreadPoolWorker(idleWorkers,cl);
		}
	}
	public void execute(Runnable target) throws InterruptedException {
		// block (forever) until a worker is available
		ThreadPoolWorker worker = (ThreadPoolWorker) idleWorkers.remove();
		worker.process(target);
	}

	public void stopRequestIdleWorkers() {
		try {
			Object[] idle = idleWorkers.removeAll();
			for ( int i = 0; i < idle.length; i++ ) {
				( (ThreadPoolWorker) idle[i] ).stopRequest();
			}
		} catch ( InterruptedException x ) {
			Thread.currentThread().interrupt(); // re-assert
		}
	}

	public void stopRequestAllWorkers() {
		// Stop the idle one's first since that won't interfere with anything
		// productive.
		stopRequestIdleWorkers();

		// give the idle workers a quick chance to die 
		try { Thread.sleep(250); } catch ( InterruptedException x ) { }
		
		// Step through the list of ALL workers that are still alive.
		for ( int i = 0; i < workerList.length; i++ ) {
			if ( workerList[i].isAlive() ) {
				workerList[i].stopRequest();
			}
		}
	}
   
}
