package com.hausontech.hrs.api.allocationManager.service;

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
/**
 * 维度筛选条件设置
 * @author Administrator
 *
 */
public abstract interface IAllocationRulesSettingService {
	/**
	 * 查询维度筛选条件
	 * @param paramDimFilterHeaderBean
	 * @return List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> findDimFilterHeader(DimFilterHeaderBean paramDimFilterHeaderBean);
	/**
	 * 查询记录条数
	 * @param paramDimFilterHeaderBean
	 * @return int
	 */
	public abstract int getCountFilterHeader(DimFilterHeaderBean paramDimFilterHeaderBean);
	/**
	 * 判断是否存在Segment，但DAO的impl返回false
	 * @param paramDimFilterHeaderBean
	 * @return boolean
	 */
	public abstract boolean filterHeaderIsSegmentExist(DimFilterHeaderBean paramDimFilterHeaderBean);
	/**
	 * 创建/插入新的维度筛选条件
	 * @param paramDimFilterHeaderBean
	 */
	public abstract void createNewFilterHeader(DimFilterHeaderBean paramDimFilterHeaderBean);
	/**
	 * 根据筛选条件筛选维度
	 * @param paramString paramString
	 * @return List<Map<String, Object>>
	 */
	public abstract List<Map<String, Object>> getSegmentList(String paramString);

	/**
	 * 更新维度筛选条件
	 * @param paramDimFilterHeaderBean
	 * @return int
	 * @throws Exception
	 */
	public abstract int updateDimFilterHeader(DimFilterHeaderBean paramDimFilterHeaderBean) throws Exception;

	/**
	 * 根据维度筛选条件的ID，查询维度筛选条件
	 * @param paramInt
	 * @return DimFilterHeaderBean
	 * @throws Exception
	 */
	public abstract DimFilterHeaderBean getDimFilterHeaderBeanById(int paramInt) throws Exception;

	/**
	 * 维度筛选行
	 * @param paramDimFilterLineBean
	 * @return List<Map<String, Object>>
	 * @throws SQLException
	 */
	public abstract List<Map<String, Object>> getFilterLineValuesByCondition(DimFilterLineBean paramDimFilterLineBean)
			throws SQLException;

	/**
	 * 维度筛选行记录条数
	 * @param paramDimFilterLineBean
	 * @return int
	 * @throws Exception
	 */
	public abstract int getCountByFilterLineValuesByCondition(DimFilterLineBean paramDimFilterLineBean)
			throws Exception;

	/**
	 * 维度详细信息
	 * @param paramDimensionValueBean
	 * @return List<DimensionValueBean>
	 * @throws Exception
	 */
	public abstract List<DimensionValueBean> getFilterLineValueDetail(DimensionValueBean paramDimensionValueBean)
			throws Exception;

	/**
	 * 新建维度行
	 * @param paramDimFilterLineBean
	 * @return int
	 * @throws Exception
	 */
	public abstract int createFilterLineValues(DimFilterLineBean paramDimFilterLineBean) throws Exception;

	/**
	 * 更新维度行
	 * @param reqBean
	 * @return int
	 * @throws Exception
	 */
	public abstract int updateFilterLineValues(DimFilterLineBean reqBean) throws Exception;

	/**
	 * 删除维度行
	 * @param reqBean
	 * @return int
	 * @throws Exception
	 */
	public abstract int destroyFilterLineValues(DimFilterLineBean reqBean) throws Exception;

	/* 静态因子头表 */
	/**
	 * 查询静态因子
	 * @param conditionBean
	 * @return List<Map<String, Object>>
	 * @throws Exception
	 */
	public abstract List<Map<String, Object>> findDriverStaticHeader(DriverStaticHeader conditionBean)
			throws Exception;

	/**
	 * 获得记录总数 
	 * @param conditionBean
	 * @return int
	 * @throws Exception
	 */
	public abstract int getCountDriverStaticHeader(DriverStaticHeader conditionBean) throws Exception;

	/**
	 * 创建静态因子
	 * @param createBean
	 * @return int
	 * @throws Exception
	 */
	public abstract int createNewDriverStaticHeader(DriverStaticHeader createBean) throws Exception;

	/**
	 * 更新静态因子
	 * @param updateBean
	 * @return int
	 * @throws Exception
	 */
	public abstract int updateDriverStaticHeader(DriverStaticHeader updateBean) throws Exception;

	/**
	 * 删除
	 * @param deleteBean
	 * @return int
	 * @throws Exception
	 */
	public abstract int deleteDriverStaticHeader(DriverStaticHeader deleteBean) throws Exception;

	/* 静态因子行表 */
	public abstract DriverStaticHeader getDriverStaticHeaderById(int parentid) throws Exception;
	
	public abstract List<Map<String, Object>> findDriverStaticLine(DriverStaticLine conditionBean)
			throws Exception;

	public abstract int getCountDriverStaticLine(DriverStaticLine conditionBean) throws Exception;

	public abstract int createNewDriverStaticLine(DriverStaticLine createBean) throws Exception;

	public abstract int updateDriverStaticLine(DriverStaticLine updateBean) throws Exception;

	public abstract int deleteDriverStaticLine(DriverStaticLine deleteBean) throws Exception;
	
	/* 分摊规则组 */
	public abstract List<Map<String, Object>> findAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean conditionBean)
			throws Exception;

	public abstract int getCountAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean conditionBean) throws Exception;

	public abstract int createNewAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean createBean) throws Exception;

	public abstract int updateAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean updateBean) throws Exception;

	public abstract int deleteAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean deleteBean) throws Exception;

	/* 分摊规则行 */
	public abstract AllocRulesGroupHaeaderBean getAllocRulesGroupHaeaderById(int parentid) throws Exception;
	
	public abstract List<Map<String, Object>> findAllocRulesGroupLine(AllocRulesGroupLineBean conditionBean)
			throws Exception;

	public abstract int getCountAllocRulesGroupLine(AllocRulesGroupLineBean conditionBean) throws Exception;

	public abstract int createNewAllocRulesGroupLine(AllocRulesGroupLineBean createBean) throws Exception;

	public abstract int updateAllocRulesGroupLine(AllocRulesGroupLineBean updateBean) throws Exception;

	public abstract int deleteAllocRulesGroupLine(AllocRulesGroupLineBean deleteBean) throws Exception;
	
	
	/*分摊规则 */
	public abstract List<Map<String, Object>> findAllocRule(AllocRuleBean conditionBean)
			throws Exception;

	public abstract int getCountAllocRule(AllocRuleBean conditionBean) throws Exception;

	public abstract int createNewAllocRule(AllocRuleBean createBean) throws Exception;

	public abstract int updateAllocRule(AllocRuleBean updateBean) throws Exception;

	public abstract int deleteAllocRule(AllocRuleBean deleteBean) throws Exception;

	public abstract List<Map<String, Object>> getRuleList();
	
	public abstract AllocRuleBean getAllocRuleById(int parentid) throws Exception;
	/* AllocDriverAccountBean */
	
	public abstract AllocDriverRecord getAllocDriverById(int parseInt) throws Exception;
	
	public abstract List<Map<String, Object>> findAllocDriverAccount(AllocDriverAccountBean conditionBean)
			throws Exception;

	public abstract int getCountAllocDriverAccount(AllocDriverAccountBean conditionBean) throws Exception;

	public abstract int createNewAllocDriverAccount(AllocDriverAccountBean createBean) throws Exception;

	public abstract int updateAllocDriverAccount(AllocDriverAccountBean updateBean) throws Exception;

	public abstract int deleteAllocDriverAccount(AllocDriverAccountBean deleteBean) throws Exception;
	
	/*AllocSourceBean*/
	public abstract List<Map<String, Object>> findAllocSource(AllocSourceBean conditionBean)
			throws Exception;

	public abstract int getCountAllocSource(AllocSourceBean conditionBean) throws Exception;

	public abstract int createNewAllocSource(AllocSourceBean createBean) throws Exception;

	public abstract int updateAllocSource(AllocSourceBean updateBean) throws Exception;

	public abstract int deleteAllocSource(AllocSourceBean deleteBean) throws Exception;
	
	/*AllocSourceAccountBean*/
	public abstract AllocSourceBean getAllocSourceById(int parentid) throws Exception;
	
	public abstract List<Map<String, Object>> findAllocSourceAccount(AllocSourceAccountBean conditionBean)
			throws Exception;

	public abstract int getCountAllocSourceAccount(AllocSourceAccountBean conditionBean) throws Exception;

	public abstract int createNewAllocSourceAccount(AllocSourceAccountBean createBean) throws Exception;

	public abstract int updateAllocSourceAccount(AllocSourceAccountBean updateBean) throws Exception;
	
	/*AllocTargetAccountBean*/

    public abstract AllocTargetRecord getAllocTargetById(int parentid) throws Exception;
	
	public abstract List<Map<String, Object>> findAllocTargetAccount(AllocTargetAccountBean conditionBean)
			throws Exception;

	public abstract int getCountAllocTargetAccount(AllocTargetAccountBean conditionBean) throws Exception;

	public abstract int createNewAllocTargetAccount(AllocTargetAccountBean createBean) throws Exception;

	public abstract int updateAllocTargetAccount(AllocTargetAccountBean updateBean) throws Exception;
	
	public List<Map<String, Object>> getDimFilterListForCombo(DimFilterHeaderBean dfh) throws Exception;

	public abstract List<Map<String, Object>> getStaticList();

	public abstract List<Map<String, Object>> findAllType(long allocRuleId);

	public abstract int getCountAllocSourceAccountTrue(AllocSourceAccountBean allocSourceAccountBean)throws Exception;

	public abstract int getCountAllocDriverAccountTrue(AllocDriverAccountBean requestBean)throws Exception;

	public abstract int getCountAllocTargetAccountTrue(AllocTargetAccountBean requestBean)throws Exception;

	public abstract int getCountRoleUserMap(RoleUserMapRecord requestbean)throws Exception;

	public abstract List<Map<String, Object>> findRoleUserMap(RoleUserMapRecord requestbean)throws Exception;

	public abstract int saveRoleUserMap(RoleUserMapRecord requestbean)throws Exception;

	public abstract int updateRoleUserMap(RoleUserMapRecord reqBean)throws Exception;

	public abstract int deleteRoleUserMap(RoleUserMapRecord reqBean)throws Exception;

	public abstract List<Map<String, Object>> getRoleList(RoleRecord requestbean) throws Exception;

	public abstract int getCountRoleList(RoleRecord requestbean) throws Exception;


	
}


