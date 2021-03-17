package com.hausontech.hrs.daoImpl.allocationManager.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.allocationManager.AllocDriverAccountBean;
import com.hausontech.hrs.bean.allocationManager.AllocRuleBean;
import com.hausontech.hrs.bean.allocationManager.AllocRulesGroupHaeaderBean;
import com.hausontech.hrs.bean.allocationManager.AllocRulesGroupLineBean;
import com.hausontech.hrs.bean.allocationManager.AllocSourceAccountBean;
import com.hausontech.hrs.bean.allocationManager.AllocSourceBean;
import com.hausontech.hrs.bean.allocationManager.AllocTargetAccountBean;
import com.hausontech.hrs.bean.allocationManager.DimFilterHeaderBean;
import com.hausontech.hrs.bean.allocationManager.DimFilterLineBean;
import com.hausontech.hrs.bean.allocationManager.DriverStaticHeader;
import com.hausontech.hrs.bean.allocationManager.DriverStaticLine;
import com.hausontech.hrs.bean.allocationManager.model.AllocDriverRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocRuleRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocSourceRecord;
import com.hausontech.hrs.bean.allocationManager.model.AllocTargetRecord;
@Repository
public interface HaeDimFilterHeaderMapper {
	/*获取维度筛选条件设置界面数据*/
	public List<DimFilterHeaderBean> findDimFilterHeader(@Param("start")String start,@Param("filterHeaderName") String filterHeaderName,@Param("dimensionSegment") String dimensionSegment,
			@Param("count")String count)throws SQLException;
	/*增加维度筛选条件记录*/
	public int createNewFilterHeader(DimFilterHeaderBean dimFilterHeaderBean)throws SQLException;
	/*更新维度筛选条件记录*/
	public int updateDimFilterHeader(DimFilterHeaderBean updateBean)throws SQLException;
	/*获取子值范围界面数据*/
	public List<DimFilterLineBean> getFilterLineValuesByCondition(@Param("start")String start,@Param("filterHeaderName") String filterHeaderName,@Param("dimensionSegment") String dimensionSegment,@Param("filterHeaderId")String filterHeaderId,@Param("count") String count)throws SQLException;
	/*增加子值范围记录*/
	public int createFilterLineValues(DimFilterLineBean queryBean)throws SQLException;
	/*删除子值范围记录*/
	public int destroyFilterLineValues(int filterLineId)throws SQLException;
	/*更新子值范围记录*/
	public int updateFilterLineValues(DimFilterLineBean queryBean)throws SQLException;
	/*获取子值范围界面数据总数*/
	public int getCountByFilterLineValuesByCondition(@Param("filterHeaderId")int filterHeaderId)throws SQLException;
	/*获取维度筛选条件设置界面数据总数*/
	public int getCountFilterHeader(@Param("filterHeaderName")String filterHeaderName,@Param("dimensionSegment") String dimensionSegment)throws SQLException;
	/*获取静态分摊因子头表设置界面数据*/
	public List<DriverStaticHeader> findDriverStaticHeader(@Param("start")String start, @Param("driverCode")String driverCode,@Param("description") String description,@Param("count") String count)throws SQLException;
	/*增加静态分摊因子头表设置记录*/
	public int createNewDriverStaticHeader(DriverStaticHeader createBean)throws SQLException;
	/*删除静态分摊因子头表设置记录*/
	public int deleteDriverStaticHeader(int staticHeaderId)throws SQLException;
/*静态分摊因子头表设置记录*/
	public int deleteDriverStaticHeaderLine(@Param("staticHeaderId")int staticHeaderId,@Param("staticLineId")int staticLineId)throws SQLException;
	/*更新静态分摊因子头表设置记录*/
	public int updateDriverStaticHeader(DriverStaticHeader updateBean)throws SQLException;
	/*获取静态分摊因子子值范围界面数据*/
	public List<DriverStaticLine> getDriverStaticLine(@Param("start")String start,@Param("dimValue") String dimValue,@Param("headerId") String headerId,@Param("driverCode") String driverCode,@Param("count") String count)throws SQLException;
	/*更新静态分摊因子子值范围记录*/
	public int updateDriverStaticLine(DriverStaticLine updateBean)throws SQLException;
	/*增加静态分摊因子子值范围记录*/
	public int createDriverStaticLine(DriverStaticLine createBean)throws SQLException;
	/*获取静态分摊因子头表设置界面数据总数*/
	public int getCountDriverStaticHeader(@Param("driverCode")String driverCode,@Param("description") String description)throws SQLException;
	/*获取静态分摊因子子值范围界面数据总数*/
	public int getCountDriverStaticLine(@Param("headerId")String headerId,@Param("dimValue") String dimValue,@Param("driverCode") String driverCode)throws SQLException;
	/*获取规则数据*/
	public List<Map<String, Object>> getRuleList()throws SQLException;
	/*获取静态分摊因子数据*/
	public List<Map<String, Object>> getStaticList()throws SQLException;
	/*获取筛选维度数据*/
	public List<Map<String, Object>> getSegmentList(String condition)throws SQLException;
	/*获取分摊规则组设置界面数据*/
	public List<AllocRulesGroupHaeaderBean> getAllocRequestInstance(@Param("start")String start,@Param("groupName") String groupName,@Param("count") String count)throws SQLException;
	/*增加分摊规则组设置记录*/
	public int createNewAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean createBean)throws SQLException;
	/*更新分摊规则组设置记录*/
	public int updateAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean updateBean)throws SQLException;
	/*根据表名删除分摊规则组设置记录*/
	public int deleteAllocRulesGroupHaeader(@Param("tableName")String tableName,@Param("groupHeaderId") String groupHeaderId)throws SQLException;
	/*获取分摊规则设置界面数据*/
	public List<AllocRulesGroupLineBean> getAllocRequestLines(@Param("start")String start,@Param("headerId")String headerId,@Param("count") String count)throws SQLException;
	/*获取分摊规则设置界面数据总数*/
	public int getCountAllocRulesGroupLine(int groupHeaderId)throws SQLException;
	/*获取分摊规则组设置界面数据总数*/
	public int getCountAllocRulesGroupHaeader(@Param("groupName")String groupName)throws SQLException;
	/*增加分摊规则设置记录*/
	public int createAllocRulesGroupLine(AllocRulesGroupLineBean createBean)throws SQLException;
	/*更新分摊规则设置记录*/
	public int updateAllocRulesGroupLine(AllocRulesGroupLineBean updateBean)throws SQLException;
	/*删除分摊规则设置记录*/
	public int deleteAllocRulesGroupLine(int groupLineId)throws SQLException;
	/*增加分摊规则设置界面记录*/
	public int addAllocRule(AllocRuleRecord allocRule)throws SQLException;
	/*删除分摊规则设置界面记录*/
	public void deleteAllocRule(long allocRuleId)throws SQLException;
	/*更新分摊规则设置界面记录*/
	public int updateAllocRule(AllocRuleRecord allocRule)throws SQLException;
	/*更新分摊因子配置界面记录*/
	public int updateAllocDriver(AllocDriverRecord allocDriverRecord)throws SQLException;
	/*更新动态分摊因子账户设置界面记录*/
	public int updateAllocDriverAccount(AllocDriverAccountBean updateBean)throws SQLException;
	/*更新分摊源配置界面记录*/
	public int updateAllocSource(AllocSourceRecord allocSourceRecord)throws SQLException;
	/*更新来源账户设置界面记录*/
	public int updateAllocSourceAccount(AllocSourceAccountBean updateBean)throws SQLException;
	/*更新目标账户设置界面记录*/
	public int updateAllocTargetAccount(AllocTargetAccountBean updateBean)throws SQLException;
	/*更新分摊(目标/抵消)配置界面记录*/
	public int updateAllocTarget(AllocTargetRecord allocTargetRecord)throws SQLException;
	/*获取分摊因子账户设置界面数据*/
	public List<AllocDriverAccountBean> findAllocDriverAccount(@Param("start")int start,@Param("driverId")int driverId, @Param("count")int count)throws SQLException;
	/*获取分摊因子账户设置界面数据总数*/
	public int getCountAllocDriverAccount()throws SQLException;
	/*获取来源账户设置界面数据*/
	public List<AllocSourceAccountBean> findAllocSourceAccount(@Param("start")int start,@Param("sourceId") int sourceId,@Param("count") int count)throws SQLException;
	/*获取目标账户设置界面数据*/
	public List<AllocTargetAccountBean> findAllocTargetAccount(@Param("start")int start,@Param("targetId") int targetId,@Param("count") int count)throws SQLException;
	/*获取分摊规则设置界面数据*/
	public List<AllocRuleRecord> getAllocRuleDataGrid(@Param("start")String start,@Param("ruleName") String ruleName,@Param("count") String count)throws SQLException;
	/*获取分摊因子配置界面数据*/
	public List<AllocDriverRecord> getAllocDriverDataGrid(@Param("start")String start,@Param("ruleId") String ruleId,@Param("count") String count)throws SQLException;
	/*获取分摊源配置界面数据*/
	public List<AllocSourceRecord> getAllocSourceDataGrid(@Param("start")String start,@Param("sourceType") String sourceType,@Param("ruleId") String ruleId,@Param("count") String count)throws SQLException;
	/*获取分摊(目标/抵消)配置界面数据*/
	public List<AllocTargetRecord> getAllocTargetDataGrid(@Param("start")String start,@Param("targetType") String targetType, @Param("ruleId")String ruleId,@Param("count")  String count)throws SQLException;
	/*根据RowId获取RuleRecord记录*/
	public AllocRuleRecord getAllocRuleByKey(long ruleId)throws SQLException;
	/*根据sourceId获取SourceRecord记录*/
	public AllocSourceRecord getAllocSourceByKey(long sourceId)throws SQLException;
	/*根据ruleId获取SourceRecord记录*/
	public AllocSourceRecord getAllocSourceByRuleId(long ruleId)throws SQLException;
	/*根据ruleId获取TargetRecord记录*/
	public AllocTargetRecord getAllocTargetByRuleId(long ruleId)throws SQLException;
	/*根据ruleId获取DriverRecord记录*/
	public AllocDriverRecord getAllocDriverByRuleId(long ruleId)throws SQLException;
	/*增加分摊因子配置界面记录*/
	public int addAllocDriver(AllocDriverRecord allocDriver)throws SQLException;
	/*增加分摊源配置界面记录*/
	public int addAllocSource(AllocSourceRecord allocSource)throws SQLException;
	/*增加分摊(目标/抵消)配置界面记录*/
	public int addAllocTarget(AllocTargetRecord allocTarget)throws SQLException;
	/*根据id获取分摊规则组设置记录数据*/
	public AllocRulesGroupHaeaderBean getAllocRulesGroupHaeaderById(int parentId)throws SQLException;
	/*根据id获取静态分摊因子头表设置记录数据*/
	public DriverStaticHeader getDriverStaticHeaderById(int parentId)throws SQLException;
	/*根据id获取维度筛选条件设置记录数据*/
	public DimFilterHeaderBean getDimFilterHeaderBeanById(int primaryKey)throws SQLException;
	/*获取动态分摊因子账户设置筛选条件参数*/
	public List<Map<String, Object>> getDimFilterListForCombo(@Param("filterHeaderId")String filterHeaderId,@Param("dimensionSegment") String dimensionSegment)throws SQLException;
	/*获取分摊源因子账户配置数据*/
	public List<AllocSourceBean> getAllocSourceById(int parentid)throws SQLException;
	/*获取分摊源账户配置数据*/
	public List<AllocRuleBean> getAllocRuleById(int parentid)throws SQLException;
	/*获取分摊目标账户配置数据*/
	public List<AllocDriverRecord> getAllocDriverById(int parentid)throws SQLException;
	/*获取分摊目标账户配置数据总数*/
	public int existvaild(@Param("tableName")String tableName,@Param("driverId")String driverId,@Param("sourceId")String sourceId,@Param("targetId")String targetId,@Param("dimensionSegment") String dimensionSegment)throws SQLException;
	/*判断没有分摊账户配置则新增*/
	public int createAllocDriverAccount(AllocDriverAccountBean createBean)throws SQLException;
	/*判断没有分摊源账户配置则新增*/
	public int createAllocSourceAccount(AllocSourceAccountBean createBean)throws SQLException;
	/*判断没有分摊目标账户配置则新增*/
	public int createNewAllocTargetAccount(AllocTargetAccountBean createBean)throws SQLException;
	/*获取分摊目标账户配置数据*/
	public List<AllocTargetRecord> getAllocTargetById(int parentid)throws SQLException;
	/*更新分摊源因子账户配置数据*/
	public int updateAllocSourceBean(AllocSourceBean updateBean)throws SQLException;
	/*获取所有类型数据*/
	public List<Map<String, Object>> findAllType(long allocRuleId)throws SQLException;
	/*根据sourceId判断数目对sourceaccount进行初始化操作 ： 1.判定是否已经初始化过 ，如果未初始化，进行初始化*/
	public int getCountAllocSourceAccountTrue(int sourceId)throws SQLException;
	/*根据drvierId判断数目对sourceaccount进行初始化操作 ： 1.判定是否已经初始化过 ，如果未初始化，进行初始化*/
	public int getCountAllocDriverAccountTrue(int drvierId)throws SQLException;
	/*根据targetId判断数目对sourceaccount进行初始化操作 ： 1.判定是否已经初始化过 ，如果未初始化，进行初始化*/
	public int getCountAllocTargetAccountTrue(int targetId)throws SQLException;
	/*获取分摊规则设置总数*/
	public int getAllocRuleDataGridCount(@Param("ruleName")String ruleName)throws SQLException;
	
	
	
	
	
	
	
	

	
}