package com.hausontech.hrs.api.engine;

public interface IThreadManager {    
	
	/**
     * Run a Runnable object in a free thread at a MEDIUM priority.
     *
     * @param   objToRun   The Runnable object to run.
    */
   public void run (Runnable objToRun);

   /**
    * Run a Runnable object in a free thread at a specified priority.
    *
    * @param   objToRun   The Runnable object to run.
    * @param   pri        The priority of the running thread.
    */
   public void run (Runnable objToRun, int pri);
   
}
