package com.hausontech.hrs.api.reportSetting.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

import com.hausontech.hrs.bean.dimensionManager.DimensionBean;
import com.hausontech.hrs.bean.reportSetting.DimensionValueSetBean;
import com.hausontech.hrs.bean.reportSetting.ItemCalBean;
import com.hausontech.hrs.bean.reportSetting.ItemCodeExtHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemCodeExtLineBean;
import com.hausontech.hrs.bean.reportSetting.ItemContentBean;
import com.hausontech.hrs.bean.reportSetting.ItemGroupRuleHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemGroupRuleLineBean;
import com.hausontech.hrs.bean.reportSetting.ItemHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpLineBean;
import com.hausontech.hrs.bean.reportSetting.ItemRowCalculationBean;
import com.hausontech.hrs.bean.reportSetting.ItemRowSetHeaderBean;
import com.hausontech.hrs.bean.reportSetting.ItemRowSetLineBean;

public interface IRptSettingDao {

	public List<Map<String, Object>> getRuleHeaderList(ItemGroupRuleHeaderBean queryBean) throws SQLException;

	public int saveRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, SQLException;

	public int updateRuleHeader(ItemGroupRuleHeaderBean record) throws DuplicateKeyException, SQLException;

	public int getRuleHeaderReferencedNum(ItemGroupRuleHeaderBean record) throws SQLException;

	public int deleteRuleHeader(ItemGroupRuleHeaderBean record) throws SQLException;

	public int deleteReferencedRuleLine(ItemGroupRuleHeaderBean record) throws SQLException;

	public ItemGroupRuleHeaderBean getRuleHeaderByByPrimaryKey(int primaryKey) throws SQLException;

	public List<Map<String, Object>> getRuleLineList(ItemGroupRuleLineBean queryBean) throws SQLException;

	public int saveRuleLineRecord(ItemGroupRuleLineBean ruleLineRecord) throws DuplicateKeyException, SQLException;

	public int updateRuleLineRecord(ItemGroupRuleLineBean record) throws DuplicateKeyException, SQLException;

	public int deleteRuleLineRecord(ItemGroupRuleLineBean record) throws SQLException;

	public List<Map<String, Object>> getItmeHeaderList(ItemHeaderBean queryBean) throws SQLException;

	public ItemHeaderBean getItemHeaderByByPrimaryKey(long primaryKey) throws SQLException;

	public long saveItemHeader(ItemHeaderBean newItemHeader) throws DuplicateKeyException, SQLException;

	public int updateItemHeader(ItemHeaderBean record) throws DuplicateKeyException, SQLException;

	public int deleteItemHeader(ItemHeaderBean record) throws SQLException;

	public List<Map<String, Object>> getItemContentList(ItemContentBean queryBean) throws SQLException;

	public int saveItemContenHeader(ItemContentBean itemContentHeader) throws SQLException;

	public int updateItemContenHeader(ItemContentBean record) throws SQLException;

	public int deleteItemContentHeaderRecord(ItemContentBean record) throws SQLException;

	public List<DimensionValueSetBean> getDimensionValueSetList() throws SQLException;

	public ItemContentBean getItemContentByByPrimaryKey(int primaryKey) throws SQLException;

	public int updateItemContenLowHigh(ItemContentBean record) throws SQLException;

	public List<Map<String, Object>> getExtHeaderList(ItemCodeExtHeaderBean codeExtHeaderBean) throws SQLException;

	public int saveCodeExtHeader(ItemCodeExtHeaderBean queryBean) throws DuplicateKeyException, SQLException;

	public int updateCodeExtHeader(ItemCodeExtHeaderBean record) throws DuplicateKeyException, SQLException;

	public int getCodeExtHeaderReferencedNum(ItemCodeExtHeaderBean record) throws SQLException;

	public int deleteCodeExtHeader(ItemCodeExtHeaderBean record) throws SQLException;

	public int deleteReferencedCodeExtLine(ItemCodeExtHeaderBean record) throws SQLException;

	public ItemCodeExtHeaderBean getCodeExtHeaderByByPrimaryKey(int primaryKey) throws SQLException;

	public List<Map<String, Object>> getLookUpHeaderList(ItemLookUpHeaderBean lookUpHeaderBean) throws SQLException;

	public int saveLookUpHeader(ItemLookUpHeaderBean queryBean) throws DuplicateKeyException, SQLException;

	public int updateLookUpHeader(ItemLookUpHeaderBean record) throws DuplicateKeyException, SQLException;

	public int deleteLookUpHeader(ItemLookUpHeaderBean record) throws SQLException;

	public int deleteReferencedLookUpLine(ItemLookUpHeaderBean record) throws SQLException;

	public ItemLookUpHeaderBean getLookUpHeaderByByPrimaryKey(int primaryKey) throws SQLException;

	public List<Map<String, Object>> getCodeExtLineList(ItemCodeExtLineBean queryBean) throws SQLException;

	public int saveCodeExtLineRecord(ItemCodeExtLineBean queryBean) throws DuplicateKeyException, SQLException;

	public int updateCodeExtLineRecord(ItemCodeExtLineBean record) throws DuplicateKeyException, SQLException;

	public List<Map<String, Object>> getLookUpLineList(ItemLookUpLineBean queryBean) throws SQLException;

	public int saveLookUpLineRecord(ItemLookUpLineBean queryBean) throws DuplicateKeyException, SQLException;

	public int updateLookUpLineRecord(ItemLookUpLineBean queryBean) throws DuplicateKeyException, SQLException;

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

	public int countByLookUpHeaderBean(ItemLookUpHeaderBean reqBean) throws SQLException;

	public int countByCodeExtHeaderBean(ItemCodeExtHeaderBean reqBean) throws SQLException;

	public int countByLookUpLineBean(ItemLookUpLineBean reqBean) throws SQLException;

	public int countByRowSetLineBean(ItemRowSetLineBean reqBean) throws SQLException;

	public int countByRowCalculationBean(ItemRowCalculationBean reqBean) throws SQLException;

	public int countByCodeExtLineBean(ItemCodeExtLineBean reqBean) throws SQLException;

	public int countByItemGroupRuleHeaderBean(ItemGroupRuleHeaderBean ruleHeaderBean) throws SQLException;

	public int countByItemGroupRuleLineBean(ItemGroupRuleLineBean ruleLineBean) throws SQLException;

	public int countByItemHeaderBean(ItemHeaderBean itemRuleBean) throws SQLException;

	public int countByItemContentBean(ItemContentBean contentBean) throws SQLException;

	public String getLoopUpByRuleCode(String ruleCode) throws SQLException;

	public List<Map<String, Object>> getItemCalculationList(ItemCalBean queryBean) throws SQLException;

	public int getItemCalculationCount(ItemCalBean reqBean) throws SQLException;

	public List<Map<String, Object>> getCalItemList(ItemHeaderBean queryBean) throws SQLException;

	public int saveItemCalculation(ItemCalBean newItemCalBean) throws SQLException;

	public int updateItemCaculation(ItemCalBean updItemCalBean) throws SQLException;

	public int deleteItemCalculation(ItemCalBean record) throws SQLException;
	
	public List<DimensionBean> getConfigurableLowHighDimList() throws SQLException;
	
	public int deleteRefRowSetLines(ItemRowSetHeaderBean reqBean) throws SQLException;
	
	public int deleteRowSetRecord(ItemRowSetHeaderBean reqBean) throws SQLException;
	
	public int deleteRefRowCalculations(ItemRowSetLineBean reqBean) throws SQLException;
	
	public int deleteRowSetLineRecord(ItemRowSetLineBean reqBean) throws SQLException;
	
	public int deleteRowCalculationRecord(ItemRowCalculationBean reqBean) throws SQLException;
}
