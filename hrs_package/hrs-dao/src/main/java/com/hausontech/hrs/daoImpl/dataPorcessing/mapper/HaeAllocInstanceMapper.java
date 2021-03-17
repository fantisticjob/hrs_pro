package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceHistoryRecord;
import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceRecord;
import com.hausontech.hrs.bean.dataProcess.AllocScheduleInstanceBean;
@Repository
public interface HaeAllocInstanceMapper {
	/*获取报表运行记录界面数据*/
	public List<AllocRequestInstanceRecord> getAllocRequestInstance(@Param("start")String start,@Param("ruleType") String ruleType,@Param("period") String period,@Param("count") String count)throws SQLException;
	/*获取状态ID、状态TYPE*/
	public String getStatus(@Param("statusId")String statusId,@Param("statusType")String statusType)throws SQLException;
	/*增加报表运行记录*/
	public int saveAllocRequestInstance(AllocRequestInstanceRecord jobRecord)throws SQLException;
	/*删除报表调度运行记录*/
	public int deleteScheduleRecordForInstanceId(long scheduleId)throws SQLException;
	/*删除报表运行记录*/
	public int deleteAllocRequestInstance(@Param("tableName")String tableName,@Param("instanceId")String instanceId)throws SQLException;
	/*更新报表运行记录*/
	public int updateAllocRequestInstance(AllocRequestInstanceRecord jobBean)throws SQLException;
	/*获取报表运行记录界面数据总数*/
	public int getAllocRequestInstanceCount(@Param("ruleType") String ruleType,@Param("period") String period)throws SQLException;
	/*根据主键获取报表运行记录*/
	public AllocRequestInstanceRecord getAllocRequestInstanceByPrimaryKey(long instanceId)throws SQLException;
	/*根据规则ID获取规则类型*/
	public List<Long> getruleListForLoop(@Param("ruleId")long ruleId)throws SQLException;
	/*增加报表运行实例历史记录*/
	public int saveAllocRequestInstanceHistoryRecord(AllocRequestInstanceHistoryRecord requestHistoryBean)throws SQLException;
	/*更新报表运行实例历史记录*/
	public int updateAllocRequestInstanceHistoryRecord(AllocRequestInstanceHistoryRecord requestHistoryBean)throws SQLException;
	/*获取报表运行实例历史记录*/
	public List<AllocRequestInstanceHistoryRecord> findAllocInstanceHistory(@Param("start")String start,@Param("instanceId") String instanceId, @Param("count")String count)throws SQLException;
	/*获取报表运行实例历史记录总数*/
	public int getAllocRequestInstanceHistoryRecordCount(@Param("instanceId")String instanceId,@Param("status")String status)throws SQLException;
	/*获取报表调度运行数据界面*/
	public List<AllocScheduleInstanceBean> getAllocScheduleInstance(@Param("start")String start, @Param("ruleType")String ruleType,@Param("period") String period,@Param("count")String count)throws SQLException;
	/*更新报表调度运行记录*/
	public int updateAllocSchedule(AllocScheduleInstanceBean reqBean)throws SQLException;
	/*新增报表调度运行记录*/
	public int saveAllocScheduleInstance(AllocScheduleInstanceBean scheduleBean)throws SQLException;
	/*获取报表调度运行数据界面总数*/
	public int getAllocScheduleInstanceCount(@Param("ruleType")String ruleType)throws SQLException;
	/*根据ID获取报表调度运行记录*/
	public AllocScheduleInstanceBean getScheduleById(String scheduleId)throws SQLException;
	/*获取账簿数据*/
	public List<Map<String, Object>> getLedgerListMapForDisplay()throws SQLException;
	/*获取规则组数据*/
	public List<Map<String, Object>> getGroupRuleList()throws SQLException;
	/*获取规则数据*/
	public List<Map<String, Object>> getruleList(String type)throws SQLException;
	/*获取期间数据*/
	public List<Map<String, Object>> getAvailablePeriodListMapForDisplay()throws SQLException;
	/*根据id获取增加报表运行实例历史记录*/
	public AllocRequestInstanceHistoryRecord getAllocRequestInstanceHistoryByPrimaryKey(long historyId)throws SQLException;
	/*根据historyId调用分摊规则运行函数*/
	public void getItemContentsAlloc(Map map)throws SQLException;
	/*根据historyId调用分摊规则回滚函数*/
	public void getRollbackItemContentsAlloc(Map map)throws SQLException;
	
	
	/*String getRollbackItemContentsAlloc(AllocRequestInstanceHistoryRecord requestHistoryBean)throws SQLException;*/


}