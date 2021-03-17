package com.hausontech.hrs.daoImpl.engine.core;

import com.hausontech.hrs.api.engine.IThreadManager;
import com.hausontech.hrs.daoImpl.engine.thread.ThreadManager;

public class HrsThreadManagerImpl implements IThreadManager{
	private int maxThread = 1;
	private int _threadCount = 0;

	public HrsThreadManagerImpl(int maxThread) {
		this.maxThread = maxThread;
	}

	public void run(Runnable service, int priviledge) {
		while (threadCount() >= maxThread) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		int i = incrementThread();

		HrsRunnable newService = new HrsRunnable(this, service);
		ThreadManager.getInstance().run(newService, priviledge);
	}

	public void run(Runnable service) {
		while (threadCount() >= maxThread) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
		}
		int i = incrementThread();


		IThreadManager threadManager = ThreadManager.getInstance();
		HrsRunnable newService = new HrsRunnable(this, service);
		threadManager.run(newService);
	}

	public void waitForAll() throws InterruptedException {
		while (threadCount() > 0) {
			Thread.sleep(500);
		}
	}

	public synchronized int threadCount() {
		return _threadCount;
	}

	protected synchronized int incrementThread() {
		_threadCount++;
		return _threadCount;
	}

	protected synchronized int decrementThread() {
		_threadCount--;
		return _threadCount;
	}

	class HrsRunnable implements Runnable {
		Runnable service = null;
		HrsThreadManagerImpl manager = null;

		HrsRunnable(HrsThreadManagerImpl manager, Runnable service) {
			this.service = service;
			this.manager = manager;
		}
		public void run() {
			try {
				service.run();			
			} finally {
				manager.decrementThread();
			}
		}
	}
}
