package com.hausontech.hrs.api.reportSetting.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

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
import com.hausontech.hrs.exceptions.DataReferenceException;

public interface IRptSettingService {
	
	public List<Map<String, Object>> getRuleHeaderList(ItemGroupRuleHeaderBean queryBean) throws Exception;
	
	public int createNewRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, Exception;
	
	public int updateRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, Exception;
	
	public int deleteRuleHeader(ItemGroupRuleHeaderBean removedBean) throws DataReferenceException, Exception;
	
	public ItemGroupRuleHeaderBean getRuleHeaderByByPrimaryKey(int primaryKey) throws Exception;
	
	public List<Map<String, Object>> getRuleLineList(ItemGroupRuleLineBean queryBean) throws Exception;
	
	public int createNewRuleLine(ItemGroupRuleLineBean queryBean) throws DuplicateKeyException, Exception;
	
	public int updateRuleLine(ItemGroupRuleLineBean queryBean) throws DuplicateKeyException, Exception;
	
	public int deleteRuleLine(ItemGroupRuleLineBean removedBean) throws Exception;
	
	public List<Map<String, Object>> getItmeHeaderList(ItemHeaderBean queryBean) throws Exception;
	
	public long createNewItemHeader(ItemHeaderBean queryBean) throws DuplicateKeyException, Exception;
	
	public int updateItemHeader(ItemHeaderBean updateBean) throws DuplicateKeyException, Exception;
	
	public int deleteItemHeader(ItemHeaderBean removedBean) throws Exception;
	
	public ItemHeaderBean getItemHeaderByByPrimaryKey(long primaryKey) throws Exception;
	
	public List<Map<String, Object>> getItemContentList(ItemContentBean2 queryBean) throws Exception;
	
	public int createItemContentHeader(ItemContentBean2 queryBean) throws Exception;
	
	public int updateItemContenHeader(ItemContentBean2 queryBean) throws Exception;
	
	public int deleteItemContentHeader(ItemContentBean2 removedBean) throws Exception;
	
	public List<DimensionValueSetBean> getDimensionValueSetList() throws Exception;
	
	public ItemContentBean2 getItemContentByByPrimaryKey(int primaryKey) throws Exception;
	
	public int updateItemContenLowHigh(ItemContentBean2 record) throws Exception;
	
	
	public List<Map<String, Object>> getExtHeaderList(ItemCodeExtHeaderBean codeExtHeaderBean) throws Exception;

	public int createNewCodeExtHeader(ItemCodeExtHeaderBean queryBean) throws DuplicateKeyException, Exception;

	public int updateCodeExtHeader(ItemCodeExtHeaderBean queryBean) throws DuplicateKeyException, Exception;

	public int deleteCodeExtHeader(ItemCodeExtHeaderBean removedBean)   throws Exception;

	public ItemCodeExtHeaderBean getCodeExtHeaderByByPrimaryKey(int primaryKey)   throws Exception ;
	
	

	public List<Map<String, Object>> getLookUpHeaderList(ItemLookUpHeaderBean lookUpHeaderBean) throws Exception;

	public int createNewLookUpHeader(ItemLookUpHeaderBean queryBean) throws DuplicateKeyException, Exception;

	public int updateLookUpHeader(ItemLookUpHeaderBean queryBean) throws DuplicateKeyException, Exception ;
	
	public int deleteLookUpHeader(ItemLookUpHeaderBean removedBean)   throws Exception;

	public ItemLookUpHeaderBean getLookUpHeaderByByPrimaryKey(int primaryKey)   throws Exception ;

	public List<Map<String, Object>> getCodeExtLineList(ItemCodeExtLineBean codeExtLineBean)   throws Exception;

	public int createNewCodeExtLine(ItemCodeExtLineBean queryBean) throws DuplicateKeyException, Exception;

	public int updateCodeExtLine(ItemCodeExtLineBean queryBean) throws DuplicateKeyException, Exception;
	

	public List<Map<String, Object>> getLookUpLineList(ItemLookUpLineBean lookUpLineBean) throws Exception;

	public int createNewLookUpLine(ItemLookUpLineBean queryBean) throws DuplicateKeyException, Exception;

	public int updateLookUpLine(ItemLookUpLineBean queryBean) throws DuplicateKeyException, Exception;
	

	public List<Map<String, Object>> getRowSetHeaderList(ItemRowSetHeaderBean rowSetHeaderBean)throws Exception;

	public int createNewRowSetHeader(ItemRowSetHeaderBean queryBean)throws DuplicateKeyException, Exception;
	
	public int updateRowSetHeader(ItemRowSetHeaderBean queryBean)throws DuplicateKeyException, Exception;

	public ItemRowSetHeaderBean getRowSetHeaderByByPrimaryKey(int primaryKey)throws Exception ;


	public List<Map<String, Object>> getRowSetLineList(ItemRowSetLineBean lineBean)throws Exception ;
	
	public int createNewRowSetLine(ItemRowSetLineBean reqBean)throws Exception ;

	public int updateRowSetLine(ItemRowSetLineBean reqBean)throws Exception ;

	public ItemRowSetLineBean getRowSetLineByByPrimaryKey(int primaryKey)throws Exception ;

	public List<Map<String, Object>> getRowCalculationList(ItemRowCalculationBean lineBean)throws Exception ;

	public int createNewRowCalculation(ItemRowCalculationBean reqBean)throws Exception ;

	public int updateRowCalculation(ItemRowCalculationBean reqBean)throws Exception ;

	public int getCountByRowSetHeaderBean(ItemRowSetHeaderBean reqBean)throws Exception ;

	public int getCountByLookUpHeaderBean(ItemLookUpHeaderBean lookUpHeaderBean)throws Exception ;

	public int getCountByCodeExtHeaderBean(ItemCodeExtHeaderBean codeExtHeaderBean)throws Exception ;

	public int getCountByLookUpLineBean(ItemLookUpLineBean lookUpLineBean)throws Exception ;

	public int getCountByRowSetLineBean(ItemRowSetLineBean lineBean)throws Exception ;

	public int getCountByRowCalculationBean(ItemRowCalculationBean lineBean)throws Exception ;

	public int getCountByCodeExtLineBean(ItemCodeExtLineBean codeExtLineBean)throws Exception ;

	public int getCountByItemGroupRuleHeaderBean(ItemGroupRuleHeaderBean ruleHeaderBean)throws Exception ;

	public int getCountByItemGroupRuleLineBean(ItemGroupRuleLineBean ruleLineBean)throws Exception ;

	public int getCountByItemHeaderBean(ItemHeaderBean itemRuleBean)throws Exception ;

	public int getCountByItemContentBean(ItemContentBean2 contentBean)throws Exception ;

	public String getLoopUpByRuleCode(String description)throws Exception;
	
	public List<Map<String, Object>> getItemCalculationList(ItemCalBean queryBean) throws Exception;
	
	public int getItemCalculationCount(ItemCalBean queryBean) throws Exception;
	
	public List<Map<String, Object>> getCalItemList(ItemHeaderBean queryBean) throws Exception;
	
	public int createItemCalculation(ItemCalBean calBean) throws Exception;
	
	public int updateItemCalculation(ItemCalBean calBean) throws Exception;
	
	public int deleteItemCalculation(ItemCalBean removedBean) throws Exception;
	
	public List<DimensionBean> getConfigurableLowHighDimList() throws Exception;
	public int deleteRowSetHeader(ItemRowSetHeaderBean removedBean) throws Exception;
	
	public int deleteRowSetLine(ItemRowSetLineBean removedBean) throws Exception;
	
	public int deleteRowCalculation(ItemRowCalculationBean removedBean) throws Exception;

	public int copyRowSet(ItemRowSetHeaderBean headerBean)throws Exception;

	public List<Map<String, Object>> getRuleHeaderListForSet(ItemGroupRuleHeaderBean ruleHeaderBean)throws Exception;

}
