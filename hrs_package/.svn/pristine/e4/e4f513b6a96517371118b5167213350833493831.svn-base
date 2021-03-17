package com.hausontech.hrs.daoImpl.engine.quartz;  
  
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.hausontech.hrs.api.dataProcessing.dao.IDataProcessDao;
import com.hausontech.hrs.api.engine.ICalculationDao;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.etlProcess.JobInstanceBean;
import com.hausontech.hrs.daoImpl.IBaseDao2;
import com.hausontech.hrs.daoImpl.dataPorcessing.mapper.HaeAllocInstanceMapper;
import com.hausontech.hrs.daoImpl.etlProcess.mapper.JobInstanceMapper;
  
public class QuartzManager {  
    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();  
    private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";  
    private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";  
  
    /** 
     * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名 
     * @param jobName 任务名 
     * @param cls 任务 
     * @param time 时间设置 
     */  
    @SuppressWarnings("rawtypes")  
    public static void addJob(String jobName, Class cls, String time,JobInstanceBean reqBean,JobInstanceMapper jobMapper,IBaseDao2 baseDao2) { 
    	try {
            Scheduler sched = gSchedulerFactory.getScheduler();  
            // 任务名，任务组，任务执行类  
            JobDetail jobDetail=JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();             //可以传递参数  
            jobDetail.getJobDataMap().put("reqBean",reqBean); 
            jobDetail.getJobDataMap().put("jobMapper",jobMapper);
            jobDetail.getJobDataMap().put("jobName",jobName);
            jobDetail.getJobDataMap().put("baseDao2",baseDao2);
            // 触发器  
            CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger()  
            .withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(CronScheduleBuilder.cronSchedule(time)).build() ;  
            // 触发器名,触发器组   
            // 触发器时间设定  
            sched.scheduleJob(jobDetail, trigger);  
            // 启动  
            if (!sched.isShutdown()) {  
                sched.start();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
    
    /** 
     * 获取执行任务状态
     * @param jobName 任务名 
     */  
    @SuppressWarnings("rawtypes")  
    public static String getJobStatue(String jobName) { 
    	String stateName="";
    	try {
    		StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
    		Scheduler scheduler = schedulerFactory.getScheduler();
    		TriggerKey triggerKey=new TriggerKey(jobName,TRIGGER_GROUP_NAME);
    		TriggerState state = scheduler.getTriggerState(triggerKey);
    		stateName=state.name();
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }
		return stateName;  
    }
    
    /** 
     * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名) 
     * @param jobName 
     * @param time 
     */  
    @SuppressWarnings("rawtypes")  
    public static void modifyJobTime(String jobName,Class cls, String time,JobInstanceBean reqBean,JobInstanceMapper jobMapper,IBaseDao2 baseDao2) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();
            TriggerKey triggerKey=new TriggerKey(jobName,TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
            if (trigger == null) {  
                return;  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(time)) {  
                removeJob(jobName);  
                addJob(jobName, cls, time,reqBean,jobMapper,baseDao2);  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
  
    /** 
     * 移除一个任务(使用默认的任务组名，触发器名，触发器组名) 
     * @param jobName 
     */  
    public static void removeJob(String jobName) { 
    	JobKey jobKey=new JobKey(jobName,JOB_GROUP_NAME);
    	TriggerKey triggerKey=new TriggerKey(jobName,TRIGGER_GROUP_NAME);
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.pauseTrigger(triggerKey);// 停止触发器  
            sched.unscheduleJob(triggerKey);// 移除触发器  
            sched.deleteJob(jobKey);// 删除任务  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * 启动所有定时任务 
     */  
    public static void startJobs() {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            sched.start();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * 关闭所有定时任务 
     */  
    public static void shutdownJobs() {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();  
            if (!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }

    @SuppressWarnings("rawtypes")  
    public static void addJob(String jobName, Class cls, String time,AllocRequestInstanceHistoryRecord requestHistoryBean, HaeAllocInstanceMapper allocInstanceMapper,IBaseDao2 baseDao2) { 
    	try {
            Scheduler sched = gSchedulerFactory.getScheduler();  
            // 任务名，任务组，任务执行类  
            JobDetail jobDetail=JobBuilder.newJob(cls).withIdentity(jobName, JOB_GROUP_NAME).build();             //可以传递参数  
            jobDetail.getJobDataMap().put("requestHistoryBean",requestHistoryBean); 
            jobDetail.getJobDataMap().put("allocInstanceMapper",allocInstanceMapper);
            jobDetail.getJobDataMap().put("baseDao2",baseDao2);
            jobDetail.getJobDataMap().put("jobName",jobName);
            // 触发器  
            CronTrigger trigger = (CronTrigger)TriggerBuilder.newTrigger()  
            .withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(CronScheduleBuilder.cronSchedule(time)).build() ;  
            // 触发器名,触发器组   
            // 触发器时间设定  
            sched.scheduleJob(jobDetail, trigger);  
            // 启动  
            if (!sched.isShutdown()) {  
                sched.start();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }

    @SuppressWarnings("rawtypes")  
    public static void modifyJobTime(String jobName,Class cls, String time,AllocRequestInstanceHistoryRecord requestHistoryBean,
    		HaeAllocInstanceMapper allocInstanceMapper,IBaseDao2 baseDao2) {  
        try {  
            Scheduler sched = gSchedulerFactory.getScheduler();
            TriggerKey triggerKey=new TriggerKey(jobName,TRIGGER_GROUP_NAME);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
            if (trigger == null) {  
                return;  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(time)) {  
                removeJob(jobName);  
                addJob(jobName, cls, time,requestHistoryBean,allocInstanceMapper,baseDao2);  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    } 
	 
}  