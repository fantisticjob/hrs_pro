package com.hausontech.hrs.daoImpl.reportSetting.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionBean;
import com.hausontech.hrs.bean.reportSetting.DimensionValueSetBean;
import com.hausontech.hrs.bean.reportSetting.ItemCalBean;
import com.hausontech.hrs.bean.reportSetting.ItemCodeExtHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemCodeExtLineBean;
import com.hausontech.hrs.bean.reportSetting.ItemContentBean2;
import com.hausontech.hrs.bean.reportSetting.ItemGroupRuleHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemGroupRuleLineBean;
import com.hausontech.hrs.bean.reportSetting.ItemHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpLineBean;
import com.hausontech.hrs.bean.reportSetting.ItemRowCalculationBean;
import com.hausontech.hrs.bean.reportSetting.ItemRowSetHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemRowSetLineBean;
@Repository
public interface RptSettingMapper {
	
	//新增值集
	public int saveLookUpHeader(ItemLookUpHeaderBean queryBean) throws DuplicateKeyException, SQLException;

	//修改值集
	public int updateLookUpHeader(ItemLookUpHeaderBean record) throws DuplicateKeyException, SQLException;

	//根据主键查询值集
	public ItemLookUpHeaderBean getLookUpHeaderByByPrimaryKey(@Param("primaryKey")int primaryKey) throws SQLException;

	//删除值集
	public int deleteReferencedLookUpLine(ItemLookUpHeaderBean record) throws SQLException;

	//删除值集
	public int deleteLookUpHeader(ItemLookUpHeaderBean record) throws SQLException;

	//值集列表查询
	public List<ItemLookUpHeaderBean> getLookUpHeaderList(ItemLookUpHeaderBean lookUpHeaderBean) throws SQLException;

	//查询值集记录条数
	public int countByLookUpHeaderBean(ItemLookUpHeaderBean reqBean) throws SQLException;

	//插入值集行记录
	public int saveLookUpLineRecord(ItemLookUpLineBean queryBean) throws DuplicateKeyException, SQLException;

	//更新、修改行记录
	public int updateLookUpLineRecord(ItemLookUpLineBean queryBean) throws DuplicateKeyException, SQLException;
	
	//查询值集行记录
	public List<ItemLookUpLineBean> getLookUpLineList(ItemLookUpLineBean queryBean) throws SQLException;

	//查询值集行记录条数
	public int countByLookUpLineBean(ItemLookUpLineBean reqBean) throws SQLException;

	//插入、添加外部代码记录
	public int saveCodeExtHeader(ItemCodeExtHeaderBean queryBean) throws DuplicateKeyException, SQLException;
	
	//更新、修改外部代码记录
	public int updateCodeExtHeader(ItemCodeExtHeaderBean record) throws DuplicateKeyException, SQLException;

	//查询外部代码定义
	public List<ItemCodeExtHeaderBean> getExtHeaderList(ItemCodeExtHeaderBean codeExtHeaderBean) throws SQLException;

	//查询外部代码定义记录条数
	public int countByCodeExtHeaderBean(ItemCodeExtHeaderBean reqBean) throws SQLException;
	/*获取报表行集定义界面数据*/
	public List<ItemRowSetHeaderBean> getRowSetHeaderList(@Param("start")String start,@Param("rowSetName") String rowSetName,@Param("description") String description,@Param("count") String count)throws SQLException;
	/*获取报表行集定义界面数据总数*/
	public int countByRowSetHeaderBean(@Param("rowSetName")String rowSetName,@Param("description") String description)throws SQLException;
	/*增加报表行集定义记录*/
	public int saveRowSetHeader(ItemRowSetHeaderBean queryBean)throws SQLException;
	/*更新报表行集定义记录*/
	public int updateRowSetHeader(ItemRowSetHeaderBean queryBean)throws SQLException;
	/*根据rowSetId删除报表行定义记录*/
	public int deleteRefRowSetLines(int rowSetId)throws SQLException;
	/*根据rowSetId删除报表行集定义记录*/
	public int deleteRowSetRecord(int rowSetId)throws SQLException;
	/*获取报表行定义界面数据*/
	public List<ItemRowSetLineBean> getRowSetLineList(@Param("start")String start,@Param("rowName") String rowName,@Param("rowSetHeaderId") String rowSetHeaderId,
			@Param("count")String count)throws SQLException;
	/*获取报表行定义界面数据总数*/
	public int countByRowSetLineBean(@Param("rowName")String rowName,@Param("rowSetHeaderId") String rowSetHeaderId)throws SQLException;
	/*增加报表行定义记录*/
	public int saveRowSetLineRecord(ItemRowSetLineBean reqBean)throws SQLException;
	/*根据rowId删除报表行定义记录*/
	public void deleteRefRowCalculations(long rowId)throws SQLException;
	/*根据rowId删除报表行计算定义记录*/
	public int deleteRowSetLineRecord(long rowId)throws SQLException;
	/*更新报表行定义记录*/
	public int updateRowSetLineRecord(ItemRowSetLineBean reqBean)throws SQLException;
	/*获取报表行计算定义界面数据*/
	public List<ItemRowCalculationBean> getRowCalculationList(@Param("start")String start,@Param("calItemCode") String calItemCode,@Param("rowSetLineId") String rowSetLineId,
			@Param("count")String count)throws SQLException;
	/*获取报表行计算定义界面数据总数*/
	public int countByRowCalculationBean(@Param("calItemCode")String calItemCode, @Param("rowSetLineId")String rowSetLineId)throws SQLException;
	/*增加报表行计算定义界面记录*/
	public int saveRowCalculationRecord(ItemRowCalculationBean reqBean)throws SQLException;
	/*删除报表行计算定义界面记录*/
	public int deleteRowCalculationRecord(long rowCalId)throws SQLException;
	/*更新报表行计算定义界面记录*/
	public int updateRowCalculationRecord(ItemRowCalculationBean reqBean)throws SQLException;
	/*获取计算行项目数据*/
	public List<Map<String, Object>> getCalItemListForCal(@Param("ruleCode")String ruleCode)throws SQLException;
	/*根据id获取行计算loopDimForm记录*/
	public ItemRowSetLineBean getRowSetLineByByPrimaryKey(long primaryKey)throws SQLException;
	/*根据id获取行loopDimForm记录*/
	public List<ItemRowSetHeaderBean> getRowSetHeaderByByPrimaryKey(int primaryKey)throws SQLException;
	/*获取循环组数据*/
	public List<Map<String, Object>> getRuleHeaderListForSet(ItemGroupRuleHeaderBean queryBean)throws SQLException;
	/*获取复制表名数量*/
	public int getCountByCopyTableName(@Param("length")String length,@Param("rowSetName")String rowSetName)throws SQLException;
	/*根据rowSetId获取行记录*/
	public List<Map<String, Object>> getRowIdByRowSetId(int rowSetId)throws SQLException;
	/*根据rowId获取行计算记录*/
	public List<Map<String, Object>> getRowCalIdByRowId(int rowId);
	/*根据行计算id获取行计算记录*/
	public List<ItemRowCalculationBean> getRowCalculationRecordByPrimaryKey(int parseInt);

	//查询根据外部代码主键查询代码
	public ItemCodeExtHeaderBean getCodeExtHeaderByByPrimaryKey(@Param("primaryKey")int primaryKey) throws SQLException;

	//删除外部代码记录
	public int deleteReferencedCodeExtLine(ItemCodeExtHeaderBean record) throws SQLException;

	//删除外部代码
	public int deleteCodeExtHeader(ItemCodeExtHeaderBean record) throws SQLException;

	//新增外部代码行
	public int saveCodeExtLineRecord(ItemCodeExtLineBean queryBean) throws DuplicateKeyException, SQLException;

	//更新外部代码行
	public int updateCodeExtLineRecord(ItemCodeExtLineBean record) throws DuplicateKeyException, SQLException;

	//查询外部代码行定义
	public List<ItemCodeExtLineBean> getCodeExtLineList(ItemCodeExtLineBean queryBean) throws SQLException;

	//查询外部代码行定义记录条数
	public int countByCodeExtLineBean(ItemCodeExtLineBean reqBean) throws SQLException;

	//获取循环组下拉列表
	public List<ItemGroupRuleHeaderBean> getRuleHeaderList(ItemGroupRuleHeaderBean queryBean) throws SQLException;

	//根据ruleCode查询表项
	public String getLoopUpByRuleCode(@Param("ruleCode")String ruleCode) throws SQLException;

	//查询表项记录
	public List<ItemHeaderBean> getItemHeaderList(ItemHeaderBean queryBean) throws SQLException;

	//表项记录条数
	public int countByItemHeaderBean(ItemHeaderBean itemRuleBean) throws SQLException;

	//插入表项记录
	public long saveItemHeader(ItemHeaderBean newItemHeader) throws DuplicateKeyException, SQLException;
	
	//插入循环组记录
	//public int saveRuleHeader(ItemGroupRuleHeaderBean newItemHeader) throws SQLException;

	//循环组记录条数
	//public int countByItemGroupRuleHeaderBean(ItemGroupRuleHeaderBean ruleHeaderBean) throws SQLException;
	
	
	//更新循环组记录
	//public int updateRuleHeader(ItemGroupRuleHeaderBean newItemHeader) throws SQLException;

	//更新、修改表项
	public int updateItemHeader(ItemHeaderBean record) throws DuplicateKeyException, SQLException;

	//删除表项
	public int deleteItemHeader(ItemHeaderBean record) throws SQLException;

	//根据表项ID查询表项
	public ItemHeaderBean getItemHeaderByByPrimaryKey(@Param("primaryKey")long primaryKey) throws SQLException;

	//新增表项账户配置
	public int saveItemContenHeader(ItemContentBean2 itemContentHeader) throws SQLException;

	//更新、修改账户配置
	public int updateItemContenHeader(ItemContentBean2 record) throws SQLException;

	//删除账户配置
	public int deleteItemContentHeaderRecord(ItemContentBean2 record) throws SQLException;

	//查询表项账户配置
	public List<ItemContentBean2> getItemContentList(ItemContentBean2 queryBean) throws SQLException;

	//查询表项账户配置的记录条数
	public int countByItemContentBean(ItemContentBean2 contentBean) throws SQLException;

	//账户上下限配置
	public ItemContentBean2 getItemContentByByPrimaryKey(@Param("primaryKey")int primaryKey) throws SQLException;
	//根据RuleHeaderId获取循环组记录
	//public ItemGroupRuleHeaderBean getRuleHeaderByByPrimaryKey(int primaryKey) throws SQLException;
	
	//根据RULE_CODE查询循环组数量
	//public int getRuleHeaderReferencedNum(ItemGroupRuleHeaderBean record) throws SQLException;
	
	//删除循环组数据
	public int deleteReferencedRuleLine(ItemGroupRuleHeaderBean record) throws SQLException;
	
	//删除循环组数据（头表）
	//public int deleteRuleHeader(ItemGroupRuleHeaderBean record) throws SQLException;
	
	//查询循环组表项记录
	//public List<Map<String, Object>> getRuleLineList(ItemGroupRuleLineBean queryBean) throws SQLException;
	
	//获取维度上下限
	public List<DimensionBean> getConfigurableLowHighDimList() throws SQLException;
	//循环组记录条数
	//public int countByItemGroupRuleLineBean(ItemGroupRuleLineBean ruleLineBean) throws SQLException;
	//循环组记录条数
	//public int countByItemGroupRuleLineBean(ItemGroupRuleLineBean ruleLineBean) throws SQLException;
	
	//增加循环组表项
	//public int saveRuleLineRecord(ItemGroupRuleLineBean ruleLineRecord) throws DuplicateKeyException, SQLException;
	//获取计算配置列表
	public List<ItemCalBean> getItemCalculationList(ItemCalBean queryBean) throws SQLException;

	//获取表项计算配置记录条数
	public int getItemCalculationCount(ItemCalBean reqBean) throws SQLException;

	//查询计算表项
	public List<ItemHeaderBean> getCalItemList(ItemHeaderBean queryBean) throws SQLException;

	//新增表项计算项
	public int saveItemCalculation(ItemCalBean newItemCalBean) throws SQLException;

	//更新、修改表项计算项
	public int updateItemCaculation(ItemCalBean updItemCalBean) throws SQLException;

	//删除表项计算项
	public int deleteItemCalculation(ItemCalBean record) throws SQLException;

	//查询维值定义列表
	public List<DimensionValueSetBean> getDimensionValueSetList() throws SQLException;

	//更新修改表项上下限
	public int updateItemContenLowHigh(ItemContentBean2 record) throws SQLException;

	//查询表项循环组定义条数
	public int countByItemGroupRuleHeaderBean(ItemGroupRuleHeaderBean ruleHeaderBean) throws SQLException;

	//新增表项循环组定义
	public int saveRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, SQLException;

	//更新、修改表项循环组定义
	public int updateRuleHeader(ItemGroupRuleHeaderBean record) throws DuplicateKeyException, SQLException;

	//查询循环组定义
	public ItemGroupRuleHeaderBean getRuleHeaderByByPrimaryKey(@Param("primaryKey")int primaryKey) throws SQLException;

	//查询循环组条数
	public int getRuleHeaderReferencedNum(ItemGroupRuleHeaderBean record) throws SQLException;

	//删除循环组行
	//public int deleteReferencedRuleLine(ItemGroupRuleHeaderBean record) throws SQLException;

	//删除循环组
	public int deleteRuleHeader(ItemGroupRuleHeaderBean record) throws SQLException;

	//查询维度定义描述记录
	public List<ItemGroupRuleLineBean> getRuleLineList(ItemGroupRuleLineBean queryBean) throws SQLException;

	//查询维度定义描述记录条数
	public int countByItemGroupRuleLineBean(ItemGroupRuleLineBean ruleLineBean) throws SQLException;

	//新增维度定义描述记录
	public int saveRuleLineRecord(ItemGroupRuleLineBean ruleLineRecord) throws DuplicateKeyException, SQLException;

	//更新、修改维度定义描述记录
	public int updateRuleLineRecord(ItemGroupRuleLineBean record) throws DuplicateKeyException, SQLException;

	//删除维度定义描述记录
	public int deleteRuleLineRecord(ItemGroupRuleLineBean record) throws SQLException;

	
/*
	public int getCodeExtHeaderReferencedNum(ItemCodeExtHeaderBean record) throws SQLException;

	public List<Map<String, Object>> getRowSetHeaderList(ItemRowSetHeaderBean rowSetHeaderBean) throws SQLException;

	public int saveRowSetHeader(ItemRowSetHeaderBean queryBean) throws DuplicateKeyException, SQLException;

	public int updateRowSetHeader(ItemRowSetHeaderBean queryBean) throws DuplicateKeyException, SQLException;

	public ItemRowSetHeaderBean getRowSetHeaderByByPrimaryKey(int primaryKey) throws SQLException;

	public List<Map<String, Object>> getRowSetLineList(ItemRowSetLineBean lineBean) throws SQLException;

	public int saveRowSetLineRecord(ItemRowSetLineBean reqBean) throws SQLException;

	public int updateRowSetLineRecord(ItemRowSetLineBean reqBean) throws SQLException;

	public ItemRowSetLineBean getRowSetLineByByPrimaryKey(long primaryKey) throws SQLException;

	public List<Map<String, Object>> getRowCalculationList(ItemRowCalculationBean lineBean) throws SQLException;

	public int saveRowCalculationRecord(ItemRowCalculationBean reqBean) throws SQLException;

	public int updateRowCalculationRecord(ItemRowCalculationBean reqBean) throws SQLException;

	public int countByRowSetHeaderBean(ItemRowSetHeaderBean reqBean) throws SQLException;

	public int countByRowSetLineBean(ItemRowSetLineBean reqBean) throws SQLException;

	public int countByRowCalculationBean(ItemRowCalculationBean reqBean) throws SQLException;

	public int deleteRefRowSetLines(ItemRowSetHeaderBean reqBean) throws SQLException;
	
	public int deleteRowSetRecord(ItemRowSetHeaderBean reqBean) throws SQLException;
	
	public int deleteRefRowCalculations(ItemRowSetLineBean reqBean) throws SQLException;
	
	public int deleteRowSetLineRecord(ItemRowSetLineBean reqBean) throws SQLException;
	
	public int deleteRowCalculationRecord(ItemRowCalculationBean reqBean) throws SQLException;

	public List<Map<String, Object>> getRowIdByRowSetId(int id)throws SQLException;

	public ItemRowCalculationBean getRowCalculationRecordByPrimaryKey(int parseInt)throws SQLException;

	public List<Map<String, Object>> getRowCalIdByRowId(int rowSet)throws SQLException;

	public int getCountByCopyTableName(String rowSetName, int length)throws SQLException;*/
}
