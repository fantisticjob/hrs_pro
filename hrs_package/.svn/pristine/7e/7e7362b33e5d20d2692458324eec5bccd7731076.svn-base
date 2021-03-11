package com.hausontech.hrs.daoImpl.engine.quartz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleTypeQuartzAddJob {
	public static String getQuartzTimeByType(String scheduleType,String scheduleData){
		Calendar calendar = Calendar.getInstance();
		//获取当月
		String month=calendar.get(Calendar.MONTH)+1+"";
		String time="";
		//判断执行区间生成对应cron表达式 1 时  、2 天、3 周、4 月、 5 自定义
		if(scheduleType.equals("5")){
			SimpleDateFormat sdf =new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
			Date dateTime = null;
			try {
				dateTime = sdf.parse(scheduleData);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			calendar.setTime(dateTime);
			String minute=calendar.get(Calendar.MINUTE)+"";
			String second=calendar.get(Calendar.SECOND)+"";
			String hour=calendar.get(Calendar.HOUR_OF_DAY)+"";
			String year=calendar.get(Calendar.YEAR)+"";
			month=calendar.get(Calendar.MONTH)+1+"";
			String day=calendar.get(Calendar.DAY_OF_MONTH)+"";
			time=second+" "+minute+" "+hour+" "+day+" "+month+" ? "+year;
		}else if(scheduleType.equals("1")){
			time="0 "+scheduleData+" * * * ?";  	
		}else if(scheduleType.equals("2")){
			time="0 0 "+scheduleData+" * * ?";  	
		}else if(scheduleType.equals("3")){
			time="0 0 6 ? * "+(Integer.parseInt(scheduleData)+1);
		}else if(scheduleType.equals("4")){
			if(month.equals("2")){
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				calendar.roll(Calendar.DAY_OF_MONTH, -1);
				int lastDay=calendar.get(Calendar.DAY_OF_MONTH);
				if(Integer.parseInt(scheduleData)>lastDay){
					scheduleData=String.valueOf(lastDay);
				}
			}else if(month.equals("4")||month.equals("6")||month.equals("9")||month.equals("11")){
				if(Integer.parseInt(scheduleData)>30){
					scheduleData="30";
				}
			}
			time="0 0 6 "+scheduleData+" * ?";
		}
		return time;
		
	}
	public static void main(String[] args) {
		ScheduleTypeQuartzAddJob.getQuartzTimeByType("4", "31");
	}
}
