package com.hausontech.hrs.serviceImpl.etlProcess;  
  
import java.sql.SQLException;
import org.pentaho.di.core.exception.KettleException;
import org.quartz.DisallowConcurrentExecution; 
import org.quartz.JobDataMap;  
import org.quartz.JobExecutionContext;  
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.StatefulJob;
import com.hausontech.hrs.api.etlProcess.dao.IJobProcessDao;
import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;  
  
/** 
 * 任务执行类 
 * KETTLE初始化、运行等在service层所以job执行任务类只能在service层
 */
@PersistJobDataAfterExecution  
@DisallowConcurrentExecution
public class QuartzStatefulJob implements StatefulJob {  
    @Override  
    public synchronized  void execute(JobExecutionContext content) throws JobExecutionException {    
        JobDataMap dataMap = content.getJobDetail().getJobDataMap();  
        JobInstanceBean dbJob = (JobInstanceBean) dataMap.get("reqBean");
        String jobName=(String) dataMap.get("jobName");
        IJobProcessDao jobProcessDao =  (IJobProcessDao) dataMap.get("jobProcessDao");
        KettleJobServiceImpl kettle=new KettleJobServiceImpl();
		try {
			kettle.initKettleEnv();
		} catch (KettleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dbJob.setStatus("Processing");
		dbJob.setMessage("ETL job is running");
		try {
			jobProcessDao.saveEtlJobInstance(dbJob);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long startTime = System.currentTimeMillis();
		try {
			kettle.runJobFromFileSystem(dbJob);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		long ecalTime = System.currentTimeMillis() - startTime;
		dbJob.setElapsedTime((int)ecalTime);
		dbJob.setStatus("Done");
		try {
			jobProcessDao.updateEtlJobInsRecord(dbJob);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }  
}  