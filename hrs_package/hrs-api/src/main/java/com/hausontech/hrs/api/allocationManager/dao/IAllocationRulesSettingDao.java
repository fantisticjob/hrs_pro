package com.hausontech.hrs.api.allocationManager.dao;

import com.hausontech.hrs.bean.AuditBean2;
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
import com.hausontech.hrs.bean.allocationManager.model.AllocTargetRecord;
import com.hausontech.hrs.bean.allocationManager.model.RoleRecord;
import com.hausontech.hrs.bean.dimensionManager.DimensionValueBean;
import com.hausontech.hrs.bean.userManager.RoleUserMapRecord;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract interface IAllocationRulesSettingDao {
	public abstract List<Map<String, Object>> findDimFilterHeader(DimFilterHeaderBean paramDimFilterHeaderBean);

	public abstract int getCountFilterHeader(DimFilterHeaderBean paramDimFilterHeaderBean);

	public abstract boolean filterHeaderIsSegmentExist(String paramString);

	public abstract void createNewFilterHeader(DimFilterHeaderBean paramDimFilterHeaderBean);

	public abstract List<Map<String, Object>> getSegmentList(String paramString);

	public abstract int updateDimFilterHeader(DimFilterHeaderBean paramDimFilterHeaderBean) throws SQLException;

	public abstract DimFilterHeaderBean getDimFilterHeaderBeanById(int paramInt) throws SQLException;

	public abstract List<Map<String, Object>> getFilterLineValuesByCondition(DimFilterLineBean paramDimFilterLineBean)
			throws SQLException;

	public abstract int getCountByFilterLineValuesByCondition(DimFilterLineBean paramDimFilterLineBean)
			throws SQLException;

	public abstract List<DimensionValueBean> getDimensionValueByCondition(DimensionValueBean paramDimensionValueBean);

	public abstract int createFilterLineValues(DimFilterLineBean paramDimFilterLineBean);

	public abstract int updateFilterLineValues(DimFilterLineBean updateBean) throws SQLException;

	public abstract int destroyFilterLineValues(DimFilterLineBean updateBean) throws SQLException;
   
	/* 静态因子头表 */
	public abstract List<Map<String, Object>> findDriverStaticHeader(DriverStaticHeader conditionBean)
			throws SQLException;

	public abstract int getCountDriverStaticHeader(DriverStaticHeader conditionBean) throws SQLException;

	public abstract int createNewDriverStaticHeader(DriverStaticHeader createBean) throws SQLException;

	public abstract int updateDriverStaticHeader(DriverStaticHeader updateBean) throws SQLException;

	public abstract int deleteDriverStaticHeader(DriverStaticHeader deleteBean) throws SQLException;

	/* 静态因子行表 */
	public abstract DriverStaticHeader getDriverStaticHeaderById(int parentid) throws SQLException;

	public abstract List<Map<String, Object>> getDriverStaticLine(DriverStaticLine conditionBean)
			throws SQLException;

	public abstract int getCountDriverStaticLine(DriverStaticLine conditionBean)
			throws SQLException;

	public abstract int createDriverStaticLine(DriverStaticLine createBean) throws SQLException;

	public abstract int updateDriverStaticLine(DriverStaticLine updateBean) throws SQLException;

	public abstract int deleteDriverStaticLine(DriverStaticLine destroyBean) throws SQLException;
	
	/* 分摊规则组*/
	public abstract List<Map<String, Object>> findAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean conditionBean)
			throws SQLException;

	public abstract int getCountAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean conditionBean) throws SQLException;

	public abstract int createNewAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean createBean) throws SQLException;

	public abstract int updateAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean updateBean) throws SQLException;

	public abstract int deleteAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean deleteBean) throws SQLException;
	
   /* 分摊规则行*/
	public abstract AllocRulesGroupHaeaderBean getAllocRulesGroupHaeaderById(int parentid) throws SQLException;

	public abstract List<Map<String, Object>> findAllocRulesGroupLine(AllocRulesGroupLineBean conditionBean)
			throws SQLException;

	public abstract int getCountAllocRulesGroupLine(AllocRulesGroupLineBean conditionBean)
			throws SQLException;

	public abstract int createAllocRulesGroupLine(AllocRulesGroupLineBean createBean) throws SQLException;

	public abstract int updateAllocRulesGroupLine(AllocRulesGroupLineBean updateBean) throws SQLException;

	public abstract int deleteAllocRulesGroupLine(AllocRulesGroupLineBean destroyBean) throws SQLException;
	
	/* 分摊规则*/
	public abstract List<Map<String, Object>> findAllocRule(AllocRuleBean conditionBean)
			throws SQLException;

	public abstract int getCountAllocRule(AllocRuleBean conditionBean) throws SQLException;

	public abstract int createNewAllocRule(AllocRuleBean createBean) throws SQLException;

	public abstract int updateAllocRule(AllocRuleBean updateBean) throws SQLException;

	public abstract int deleteAllocRule(AllocRuleBean deleteBean) throws SQLException;

	public abstract List<Map<String, Object>> getRuleList();
	
	public abstract List<Map<String, Object>> getStaticList();
	
	/*分摊来源账户*/
	public abstract AllocSourceBean getAllocSourceById(int parentid) throws SQLException;
	
	public abstract List<Map<String, Object>> findAllocSourceAccount(AllocSourceAccountBean conditionBean)
			throws SQLException;

	public abstract int getCountAllocSourceAccount(AllocSourceAccountBean conditionBean)
			throws SQLException;

	public abstract int createAllocSourceAccount(AllocSourceAccountBean createBean) throws SQLException;

	public abstract int updateAllocSourceAccount(AllocSourceAccountBean updateBean) throws SQLException;

	//public abstract int deleteAllocSourceAccount(AllocSourceAccountBean destroyBean) throws SQLException;
	
	/*分摊来源*/
	public abstract List<Map<String, Object>> findAllocSource(AllocSourceBean conditionBean)
			throws SQLException;

	public abstract int getCountAllocSource(AllocSourceBean conditionBean)
			throws SQLException;

	public abstract int createAllocSource(AllocSourceBean createBean) throws SQLException;

	public abstract int updateAllocSource(AllocSourceBean updateBean) throws SQLException;

	public abstract int deleteAllocSource(AllocSourceBean destroyBean) throws SQLException;
	
	
	/*分摊目标账户*/
	public abstract AllocTargetRecord getAllocTargetById(int parentid) throws SQLException;
	
	public abstract List<Map<String, Object>> findAllocTargetAccount(AllocTargetAccountBean conditionBean)
			throws SQLException;

	public abstract int getCountAllocTargetAccount(AllocTargetAccountBean conditionBean)
			throws SQLException;

	public abstract int createNewAllocTargetAccount(AllocTargetAccountBean createBean) throws SQLException;

	public abstract int updateAllocTargetAccount(AllocTargetAccountBean updateBean) throws SQLException;
	
	

	
	public abstract AllocRuleBean getAllocRuleById(int parentid) throws SQLException;
	
	
	//XXX  AllocDriverRecord 
	public abstract AllocDriverRecord getAllocDriverById(int parentid) throws SQLException;
	
	public abstract List<Map<String, Object>> findAllocDriverAccount(AllocDriverAccountBean conditionBean)
			throws SQLException;

	public abstract int getCountAllocDriverAccount(AllocDriverAccountBean conditionBean)
			throws SQLException;

	public abstract int createAllocDriverAccount(AllocDriverAccountBean createBean) throws SQLException;

	public abstract int updateAllocDriverAccount(AllocDriverAccountBean updateBean) throws SQLException;

	public abstract int deleteAllocDriverAccount(AllocDriverAccountBean destroyBean) throws SQLException;
	
	public List<Map<String, Object>> getDimFilterListForCombo(DimFilterHeaderBean dfh) throws SQLException;

	public abstract List<Map<String, Object>> findAllType(long allocRuleId);

	public abstract boolean existvaild(AuditBean2 createBean) throws SQLException ;

	public abstract int getCountAllocSourceAccountTrue(AllocSourceAccountBean requestBean) throws SQLException ;

	public abstract int getCountAllocDriverAccountTrue(AllocDriverAccountBean requestBean) throws SQLException ;

	public abstract int getCountAllocTargetAccountTrue(AllocTargetAccountBean requestBean) throws SQLException ;

	public abstract int getCountRoleUserMap(RoleUserMapRecord requestbean)throws SQLException ;

	public abstract List<Map<String, Object>> findRoleUserMap(RoleUserMapRecord requestbean)throws SQLException ;

	public abstract int saveRoleUserMap(RoleUserMapRecord requestbean)throws SQLException ;

	public abstract int updateRoleUserMap(RoleUserMapRecord reqBean)throws SQLException ;

	public abstract int deleteRoleUserMap(RoleUserMapRecord reqBean)throws SQLException ;

	public abstract List<Map<String, Object>> getRoleList(RoleRecord requestbean) throws SQLException;

	public abstract int getCountRoleList(RoleRecord requestbean) throws SQLException;


}


