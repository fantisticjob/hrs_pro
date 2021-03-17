package com.hausontech.hrs.serviceImpl.allocationManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.api.allocationManager.dao.IAllocationRulesSettingDao;
import com.hausontech.hrs.api.allocationManager.service.IAllocationRulesSettingService;
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
import com.hausontech.hrs.daoImpl.IBaseDao2;
import com.hausontech.hrs.daoImpl.allocationManager.mapper.HaeDimFilterHeaderMapper;
import com.hausontech.hrs.utils.JsonUtil;

@Service
public class AllocationRulesSettingServiceImpl implements IAllocationRulesSettingService {

	@Autowired
	private HaeDimFilterHeaderMapper haeFilterHeaderMapper;

	@Autowired
	private IBaseDao2 baseDao2;

	public IBaseDao2 getBaseDao2() {
		return baseDao2;
	}

	public HaeDimFilterHeaderMapper getHaeFilterHeaderMapper() {
		return haeFilterHeaderMapper;
	}

	public void setHaeFilterHeaderMapper(HaeDimFilterHeaderMapper haeFilterHeaderMapper) {
		this.haeFilterHeaderMapper = haeFilterHeaderMapper;
	}

	public void setBaseDao2(IBaseDao2 baseDao2) {
		this.baseDao2 = baseDao2;
	}

	//private IAllocationRulesSettingDao allocationRulesSettingDao;

	/*public IAllocationRulesSettingDao getAllocationRulesSettingDao() {
		return this.allocationRulesSettingDao;
	}

	public void setAllocationRulesSettingDao(IAllocationRulesSettingDao allocationRulesSettingDao) {
		this.allocationRulesSettingDao = allocationRulesSettingDao;
	}*/

	public List<Map<String, Object>> findDimFilterHeader(DimFilterHeaderBean headerBean) {
		if (headerBean == null) {
			try {
				throw new Exception("The Reuqest bean is null.");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<DimFilterHeaderBean> result1 = null;
		String start = String.valueOf(headerBean.getRowStartIndex());
		String count = String.valueOf(headerBean.getRowCount());
		String filterHeaderName = headerBean.getFilterHeaderName();
		String dimensionSegment = headerBean.getDimensionSegment();
		try {
			result1 = haeFilterHeaderMapper.findDimFilterHeader(start, filterHeaderName, dimensionSegment, count);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("FILTER_HEADER_ID", result1.get(i).getFilterHeaderId());
			map.put("FILTER_HEADER_NAME", result1.get(i).getFilterHeaderName());
			map.put("DIMENSION_SEGMENT", result1.get(i).getDimensionSegment());
			map.put("TYPE", result1.get(i).getType());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("CREATED_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("UPDATED_DATE", result1.get(i).getLastUpdateDate());
			map.put("UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;
	}

	public int getCountFilterHeader(DimFilterHeaderBean headerBean) {
		String filterHeaderName = headerBean.getFilterHeaderName();
		String dimensionSegment = headerBean.getDimensionSegment();
		int result = 0;
		try {
			result = haeFilterHeaderMapper.getCountFilterHeader(filterHeaderName, dimensionSegment);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean filterHeaderIsSegmentExist(DimFilterHeaderBean dimFilterHeaderBean) {
		// this.allocationRulesSettingDao.filterHeaderIsSegmentExist(dimFilterHeaderBean.getDimensionSegment())
		// return false
		if (false) {
			return true;
		}
		return false;
	}

	public void createNewFilterHeader(DimFilterHeaderBean dimFilterHeaderBean) {
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_DIM_FILTER_HEADER_S");
		dimFilterHeaderBean.setFilterHeaderId(primaryKey);
		try {
			int result = haeFilterHeaderMapper.createNewFilterHeader(dimFilterHeaderBean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Map<String, Object>> getSegmentList(String condition) {
		List<Map<String, Object>> list = null;
		try {
			list = haeFilterHeaderMapper.getSegmentList(condition);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JsonUtil.fromObject(list);
	}

	@Override
	public List<Map<String, Object>> getRuleList() {
		List<Map<String, Object>> list = null;
		try {
			list = haeFilterHeaderMapper.getRuleList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JsonUtil.fromObject(list);
	}

	@Override
	public List<Map<String, Object>> getStaticList() {
		List<Map<String, Object>> list = null;
		try {
			list = haeFilterHeaderMapper.getStaticList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JsonUtil.fromObject(list);
	}

	public int updateDimFilterHeader(DimFilterHeaderBean updateBean) throws Exception {
		if ((updateBean == null) || (updateBean.getFilterHeaderId() == 0)) {
			throw new Exception("The FilterHeader bean is null or FilterHeaderId is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateDimFilterHeader(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	public DimFilterHeaderBean getDimFilterHeaderBeanById(int primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The Dim Filter Header identifier is empty.");
		}
		DimFilterHeaderBean dimFilterHeaderBean = null;
		dimFilterHeaderBean = haeFilterHeaderMapper.getDimFilterHeaderBeanById(primaryKey);
		return dimFilterHeaderBean;
	}

	public List<Map<String, Object>> getFilterLineValuesByCondition(DimFilterLineBean dimFilterLineBean)
			throws SQLException {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<DimFilterLineBean> result1 = null;
		String start = String.valueOf(dimFilterLineBean.getRowStartIndex());
		String count = String.valueOf(dimFilterLineBean.getRowCount());
		String filterHeaderId = String.valueOf(dimFilterLineBean.getFilterHeaderId());
		result1 = haeFilterHeaderMapper.getFilterLineValuesByCondition(start, null, null, filterHeaderId, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("FILTER_HEADER_ID", result1.get(i).getFilterHeaderId());
			map.put("FILTER_LINE_ID", result1.get(i).getFilterLineId());
			map.put("VALUE_LOW", result1.get(i).getValueLow());
			map.put("VALUE_HIGH", result1.get(i).getValueHigh());
			map.put("INC_EXC_INDICATOR", result1.get(i).getIncExcIndicator());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("CREATED_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATED_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;
	}

	public int getCountByFilterLineValuesByCondition(DimFilterLineBean dimFilterLineBean) throws Exception {
		int result = 0;
		try {
			result = haeFilterHeaderMapper.getCountByFilterLineValuesByCondition(dimFilterLineBean.getFilterHeaderId());
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	public List<DimensionValueBean> getFilterLineValueDetail(DimensionValueBean dimensionValueBean) throws Exception {
		if (dimensionValueBean == null) {
			throw new Exception("The dimensionValueBean query bean is empty.");
		}
		// return
		// this.allocationRulesSettingDao.getDimensionValueByCondition(dimensionValueBean);
		return null;
	}

	public int createFilterLineValues(DimFilterLineBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The FilterLineValues bean is null.");
		}
		int result = 0;
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_DIM_FILTER_LINE_S");
		queryBean.setFilterLineId(primaryKey);
		result = haeFilterHeaderMapper.createFilterLineValues(queryBean);
		return result;
	}

	@Override
	public int updateFilterLineValues(DimFilterLineBean updateBean) throws Exception {
		if ((updateBean == null) || (updateBean.getFilterHeaderId() == 0)) {
			throw new Exception("The FilterLine bean is null or FilterLineId is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateFilterLineValues(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int destroyFilterLineValues(DimFilterLineBean updateBean) throws Exception {
		if ((updateBean == null) || (updateBean.getFilterLineId() == 0)) {
			throw new Exception("The FilterLine bean is null or FilterLineId is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.destroyFilterLineValues(updateBean.getFilterLineId());
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;

	}

	// XXX DriverStaticHeader
	/* 静态因子头表 */
	@Override
	public List<Map<String, Object>> findDriverStaticHeader(DriverStaticHeader conditionBean) throws SQLException {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<DriverStaticHeader> result1 = null;
		String start = String.valueOf(conditionBean.getRowStartIndex());
		String count = String.valueOf(conditionBean.getRowCount());
		String driverCode = conditionBean.getDriverCode();
		String description = conditionBean.getDescription();
		result1 = haeFilterHeaderMapper.findDriverStaticHeader(start, driverCode, description, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("STATIC_HEADER_ID", result1.get(i).getStaticHeaderId());
			map.put("DRIVER_CODE", result1.get(i).getDriverCode());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("DIMENSION_SEGMENT", result1.get(i).getDimensionSegment());
			map.put("START_DATE_ACTIVE", result1.get(i).getStartDateActive());
			map.put("END_DATE_ACTIVE", result1.get(i).getEndDateActive());
			map.put("CREATED_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATED_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;
	}

	@Override
	public int getCountDriverStaticHeader(DriverStaticHeader conditionBean) throws Exception {
		int result = 0;
		String driverCode = conditionBean.getDriverCode();
		String description = conditionBean.getDescription();
		try {
			result = haeFilterHeaderMapper.getCountDriverStaticHeader(driverCode, description);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewDriverStaticHeader(DriverStaticHeader createBean) throws Exception {
		if ((createBean == null)) {
			throw new Exception("The DriverStaticHeader is empty.");
		}
		int result = 0;
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_DRIVER_STATIC_HEADER_S");
		createBean.setStaticHeaderId(primaryKey);
		try {
			result = haeFilterHeaderMapper.createNewDriverStaticHeader(createBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateDriverStaticHeader(DriverStaticHeader updateBean) throws Exception {
		if ((updateBean == null) || updateBean.getStaticHeaderId() == 0) {
			throw new Exception("The DriverStaticHeader is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateDriverStaticHeader(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteDriverStaticHeader(DriverStaticHeader deleteBean) throws Exception {
		if ((deleteBean == null) || deleteBean.getStaticHeaderId() == 0) {
			throw new Exception("The DriverStaticHeader is empty.");
		}
		int result = 0;
		int resultLine = 0;
		try {
			result = haeFilterHeaderMapper.deleteDriverStaticHeader(deleteBean.getStaticHeaderId());
			resultLine = haeFilterHeaderMapper.deleteDriverStaticHeaderLine(deleteBean.getStaticHeaderId(), 0);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result + resultLine;
	}

	// XXX DriverStaticLine
	@Override
	public DriverStaticHeader getDriverStaticHeaderById(int parentid) throws Exception {
		if (parentid == 0) {
			throw new Exception("The DriverStaticHeader identifier is empty.");
		}
		DriverStaticHeader driverStaticHeader = null;
		driverStaticHeader = haeFilterHeaderMapper.getDriverStaticHeaderById(parentid);
		return driverStaticHeader;
	}

	@Override
	public List<Map<String, Object>> findDriverStaticLine(DriverStaticLine conditionBean) throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<DriverStaticLine> result1 = null;
		String start = String.valueOf(conditionBean.getRowStartIndex());
		String count = String.valueOf(conditionBean.getRowCount());
		String dimValue = conditionBean.getDimValue();
		String driverCode = conditionBean.getDriverName();
		String headerId = String.valueOf(conditionBean.getStaticHeaderId());
		result1 = haeFilterHeaderMapper.getDriverStaticLine(start, dimValue, headerId, driverCode, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("STATIC_LINE_ID", result1.get(i).getStaticLineId());
			map.put("STATIC_HEADER_ID", result1.get(i).getStaticHeaderId());
			map.put("DIM_VALUE", result1.get(i).getDimValue());
			map.put("DRIVER_NAME", result1.get(i).getDriverName());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("PROPORTION", result1.get(i).getProportion());
			map.put("START_DATE_ACTIVE", result1.get(i).getStartDateActive());
			map.put("END_DATE_ACTIVE", result1.get(i).getEndDateActive());
			map.put("CREATED_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATED_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;
	}

	@Override
	public int getCountDriverStaticLine(DriverStaticLine conditionBean) throws Exception {
		int result = 0;
		String headerId = String.valueOf(conditionBean.getStaticHeaderId());
		String dimValue = conditionBean.getDimValue();
		String driverCode = conditionBean.getDriverName();
		try {
			result = haeFilterHeaderMapper.getCountDriverStaticLine(headerId, dimValue, driverCode);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewDriverStaticLine(DriverStaticLine createBean) throws Exception {
		if ((createBean == null)) {
			throw new Exception("The DriverStaticLine is empty.");
		}
		int result = 0;
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_DRIVER_STATIC_LINE_S");
		createBean.setStaticLineId(primaryKey);
		try {
			result = haeFilterHeaderMapper.createDriverStaticLine(createBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateDriverStaticLine(DriverStaticLine updateBean) throws Exception {
		if ((updateBean == null) || updateBean.getStaticLineId() == 0) {
			throw new Exception("The DriverStaticLine is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateDriverStaticLine(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteDriverStaticLine(DriverStaticLine deleteBean) throws Exception {
		if ((deleteBean == null) || deleteBean.getStaticLineId() == 0) {
			throw new Exception("The DriverStaticLine is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.deleteDriverStaticHeaderLine(0, deleteBean.getStaticLineId());
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	// XXX AllocRulesGroupHaeader
	@Override
	public List<Map<String, Object>> findAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean conditionBean)
			throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocRulesGroupHaeaderBean> result1 = null;
		String start = String.valueOf(conditionBean.getRowStartIndex());
		String count = String.valueOf(conditionBean.getRowCount());
		String groupName = conditionBean.getGroupName();
		result1 = haeFilterHeaderMapper.getAllocRequestInstance(start, groupName, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("GROUP_HEADER_ID", result1.get(i).getGroupHeaderId());
			map.put("GROUP_NUM", result1.get(i).getGroupNum());
			map.put("GROUP_NAME", result1.get(i).getGroupName());
			map.put("START_DATE_ACTIVE", result1.get(i).getStartDateActive());
			map.put("END_DATE_ACTIVE", result1.get(i).getEndDateActive());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("CREATED_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATED_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;
	}

	@Override
	public int getCountAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean conditionBean) throws Exception {
		int result = 0;
		String groupName = conditionBean.getGroupName();
		try {
			result = haeFilterHeaderMapper.getCountAllocRulesGroupHaeader(groupName);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean createBean) throws Exception {
		if ((createBean == null)) {
			throw new Exception("The AllocRulesGroupHaeader is empty.");
		}
		int result = 0;
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_RULES_GROUP_H_S");
		createBean.setGroupHeaderId(primaryKey);
		try {
			result = haeFilterHeaderMapper.createNewAllocRulesGroupHaeader(createBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean updateBean) throws Exception {
		if ((updateBean == null) || updateBean.getGroupHeaderId() == 0) {
			throw new Exception("The AllocRulesGroupHaeader is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateAllocRulesGroupHaeader(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteAllocRulesGroupHaeader(AllocRulesGroupHaeaderBean deleteBean) throws Exception {
		if ((deleteBean == null) || deleteBean.getGroupHeaderId() == 0) {
			throw new Exception("The AllocRulesGroupHaeader is empty.");
		}
		int result = 0;
		int resultLine = 0;
		try {
			result = haeFilterHeaderMapper.deleteAllocRulesGroupHaeader("HAE_ALLOC_RULES_GROUP_HAEADER",
					String.valueOf(deleteBean.getGroupHeaderId()));
			resultLine = haeFilterHeaderMapper.deleteAllocRulesGroupHaeader("HAE_ALLOC_RULES_GROUP_LINE",
					String.valueOf(deleteBean.getGroupHeaderId()));
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result + resultLine;
	}

	// XXX AllocRulesGroupLine
	@Override
	public AllocRulesGroupHaeaderBean getAllocRulesGroupHaeaderById(int parentid) throws Exception {
		if (parentid == 0) {
			throw new Exception("The AllocRulesGroupHaeaderBean identifier is empty.");
		}
		AllocRulesGroupHaeaderBean returnBean = null;
		returnBean = haeFilterHeaderMapper.getAllocRulesGroupHaeaderById(parentid);
		return returnBean;
	}

	@Override
	public List<Map<String, Object>> findAllocRulesGroupLine(AllocRulesGroupLineBean conditionBean) throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocRulesGroupLineBean> result1 = null;
		String start = String.valueOf(conditionBean.getRowStartIndex());
		String count = String.valueOf(conditionBean.getRowCount());
		String headerId = String.valueOf(conditionBean.getGroupHeaderId());
		result1 = haeFilterHeaderMapper.getAllocRequestLines(start, headerId, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("GROUP_LINE_ID", result1.get(i).getGroupLineId());
			map.put("GROUP_HEADER_ID", result1.get(i).getGroupHeaderId());
			map.put("LINE_NUM", result1.get(i).getLineNum());
			map.put("RULE_ID", result1.get(i).getRuleId());
			map.put("RULE_NAME", result1.get(i).getRuleName());
			map.put("DRIVER_TYPE", result1.get(i).getAlRuleType());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("START_DATE_ACTIVE", result1.get(i).getStartDateActive());
			map.put("END_DATE_ACTIVE", result1.get(i).getEndDateActive());

			result.add(map);
		}
		return result;
	}

	@Override
	public int getCountAllocRulesGroupLine(AllocRulesGroupLineBean conditionBean) throws Exception {
		int result = 0;
		try {
			result = haeFilterHeaderMapper.getCountAllocRulesGroupLine(conditionBean.getGroupHeaderId());
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewAllocRulesGroupLine(AllocRulesGroupLineBean createBean) throws Exception {
		if ((createBean == null)) {
			throw new Exception("The AllocRulesGroupLineBean is empty.");
		}
		int result = 0;
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_RULES_GROUP_L_S");
		createBean.setGroupLineId(primaryKey);
		try {
			result = haeFilterHeaderMapper.createAllocRulesGroupLine(createBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateAllocRulesGroupLine(AllocRulesGroupLineBean updateBean) throws Exception {
		if ((updateBean == null) || updateBean.getGroupLineId() == 0) {
			throw new Exception("The AllocRulesGroupLineBean is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateAllocRulesGroupLine(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteAllocRulesGroupLine(AllocRulesGroupLineBean deleteBean) throws Exception {
		if ((deleteBean == null) || deleteBean.getGroupLineId() == 0) {
			throw new Exception("The AllocRulesGroupLineBean is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.deleteAllocRulesGroupLine(deleteBean.getGroupLineId());
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	// XXX AllocRuleBean

	@Override
	public List<Map<String, Object>> findAllocRule(AllocRuleBean conditionBean) throws Exception {
		// return this.allocationRulesSettingDao.findAllocRule(conditionBean);
		return null;
	}

	@Override
	public int getCountAllocRule(AllocRuleBean conditionBean) throws Exception {
		/*
		 * int result = 0; try { result =
		 * this.allocationRulesSettingDao.getCountAllocRule(conditionBean); }
		 * catch (Exception se) { throw new Exception(se); } return result;
		 */
		return 0;
	}

	@Override
	public int createNewAllocRule(AllocRuleBean createBean) throws Exception {
		if ((createBean == null)) {
			throw new Exception("The AllocRuleBean is empty.");
		}
		int result = 0;
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_RULE_S");
		createBean.setRuleId(primaryKey);
		try {
			// result = haeFilterHeaderMapper.createNewAllocRule(createBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return primaryKey;
	}

	@Override
	public int updateAllocRule(AllocRuleBean updateBean) throws Exception {
		if ((updateBean == null) || updateBean.getRuleId() == 0) {
			throw new Exception("The AllocRulesGroupHaeader is empty.");
		}
		int result = 0;
		try {
			// result =
			// this.allocationRulesSettingDao.updateAllocRule(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteAllocRule(AllocRuleBean deleteBean) throws Exception {
		if ((deleteBean == null) || deleteBean.getRuleId() == 0) {
			throw new Exception("The AllocRulesGroupHaeader is empty.");
		}
		int result = 0;
		try {
			// result =
			// this.allocationRulesSettingDao.deleteAllocRule(deleteBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public AllocRuleBean getAllocRuleById(int parentid) throws Exception {
		if (parentid == 0) {
			throw new Exception("The getAllocRuleById identifier is empty.");
		}
		AllocRuleBean returnBean = null;
		try {
			returnBean = haeFilterHeaderMapper.getAllocRuleById(parentid).get(0);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return returnBean;
	}

	// XXX AllocDriverAccountBean
	@Override
	public AllocDriverRecord getAllocDriverById(int parentid) throws Exception {
		if (parentid == 0) {
			throw new Exception("The AllocDriverRecord identifier is empty.");
		}
		AllocDriverRecord returnBean = null;
		try {
			returnBean = haeFilterHeaderMapper.getAllocDriverById(parentid).get(0);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return returnBean;
	}

	@Override
	public List<Map<String, Object>> findAllocDriverAccount(AllocDriverAccountBean conditionBean) throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocDriverAccountBean> result1 = null;
		int start = conditionBean.getRowStartIndex();
		int count = conditionBean.getRowCount();
		int driverId = conditionBean.getDrvierId();
		int filterHeaderId;
		result1 = haeFilterHeaderMapper.findAllocDriverAccount(start, driverId, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			filterHeaderId = result1.get(i).getFilterHeaderId();
			if (filterHeaderId == 0) {
				map.put("FILTER_HEADER_ID", "");
			} else {
				map.put("FILTER_HEADER_ID", result1.get(i).getFilterHeaderId());
			}
			map.put("DRVIER_ACC_ID", result1.get(i).getDrvierAccId());
			map.put("DRVIER_ID", result1.get(i).getDrvierId());
			map.put("LEDGER_ID", result1.get(i).getLedgerId());
			map.put("FIN_ELEMENT", result1.get(i).getFinElement());
			map.put("DIMENSION_SEGMENT", result1.get(i).getDimensionSegment());
			map.put("DIMENSION_NAME", result1.get(i).getDimensionName());
			map.put("DIMENSION_VALUE", result1.get(i).getDimensionValue());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			result.add(map);
		}
		return result;
	}

	@Override
	public int getCountAllocDriverAccount(AllocDriverAccountBean conditionBean) throws Exception {
		int result = 0;
		try {
			result = haeFilterHeaderMapper
					.getCountAllocDriverAccount(/*
												 * conditionBean.getDrvierId()
												 */);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result + 2;
	}

	@Override
	public int createNewAllocDriverAccount(AllocDriverAccountBean createBean) throws Exception {
		if ((createBean == null)) {
			throw new Exception("The AllocDriverAccountBean is empty.");
		}
		int result = 0;
		try {
			String driverId = String.valueOf(createBean.getDrvierId());
			String dimensionSegment = createBean.getDimensionSegment();
			int count = haeFilterHeaderMapper.existvaild("HAE_ALLOC_DRIVER_ACCOUNT", driverId, String.valueOf(0),
					String.valueOf(0), dimensionSegment);
			boolean existvalid = count > 0;
			if (existvalid == false) {
				int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_DRIVER_ACCOUNT_S");
				createBean.setDrvierAccId(primaryKey);
				result = haeFilterHeaderMapper.createAllocDriverAccount(createBean);
			}
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateAllocDriverAccount(AllocDriverAccountBean updateBean) throws Exception {
		if ((updateBean == null) || updateBean.getDrvierAccId() == 0) {
			throw new Exception("The AllocDriverAccountBean is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateAllocDriverAccount(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteAllocDriverAccount(AllocDriverAccountBean deleteBean) throws Exception {
		if ((deleteBean == null) || deleteBean.getDrvierAccId() == 0) {
			throw new Exception("The AllocDriverAccountBean is empty.");
		}
		int result = 0;
		try {
			// result =
			// this.allocationRulesSettingDao.deleteAllocDriverAccount(deleteBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	// XXX AllocSourceBean
	@Override
	public List<Map<String, Object>> findAllocSource(AllocSourceBean conditionBean) throws Exception {
		// return this.allocationRulesSettingDao.findAllocSource(conditionBean);
		return null;
	}

	@Override
	public int getCountAllocSource(AllocSourceBean conditionBean) throws Exception {
		int result = 0;
		try {
			// result =
			// this.allocationRulesSettingDao.getCountAllocSource(conditionBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewAllocSource(AllocSourceBean createBean) throws Exception {
		if ((createBean == null)) {
			throw new Exception("The AllocSourceBean is empty.");
		}
		int result = 0;
		try {
			// result =
			// this.allocationRulesSettingDao.createAllocSource(createBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateAllocSource(AllocSourceBean updateBean) throws Exception {
		if ((updateBean == null) || updateBean.getSourceId() == 0) {
			throw new Exception("The AllocSourceBean is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateAllocSourceBean(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteAllocSource(AllocSourceBean deleteBean) throws Exception {
		if ((deleteBean == null) || deleteBean.getSourceId() == 0) {
			throw new Exception("The AllocSourceBean is empty.");
		}
		int result = 0;
		try {
			// result = this.allocationRulesSettingDao.deleteAllocSource
			// (deleteBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	// XXX AllocSourceAccountBean
	@Override
	public AllocSourceBean getAllocSourceById(int parentid) throws Exception {
		if (parentid == 0) {
			throw new Exception("The AllocSourceBean identifier is empty.");
		}
		AllocSourceBean returnBean = null;
		try {

			returnBean = haeFilterHeaderMapper.getAllocSourceById(parentid).get(0);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return returnBean;
	}

	@Override
	public List<Map<String, Object>> findAllocSourceAccount(AllocSourceAccountBean conditionBean) throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocSourceAccountBean> result1 = null;
		int start = conditionBean.getRowStartIndex();
		int count = conditionBean.getRowCount();
		int sourceId = conditionBean.getSourceId();
		int filterHeaderId;
		result1 = haeFilterHeaderMapper.findAllocSourceAccount(start, sourceId, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			filterHeaderId = result1.get(i).getFilterHeaderId();
			if (filterHeaderId == 0) {
				map.put("FILTER_HEADER_ID", "");
			} else {
				map.put("FILTER_HEADER_ID", result1.get(i).getFilterHeaderId());
			}
			map.put("SOURCE_ACC_ID", result1.get(i).getSourceAccId());
			map.put("SOURCE_ID", result1.get(i).getSourceId());
			map.put("LEDGER_ID", result1.get(i).getLedgerId());
			map.put("FIN_ELEMENT", result1.get(i).getFinElement());
			map.put("DIMENSION_SEGMENT", result1.get(i).getDimensionSegment());
			map.put("DIMENSION_NAME", result1.get(i).getDimensionName());
			map.put("DIMENSION_VALUE", result1.get(i).getDimensionValue());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			result.add(map);
		}
		return result;
	}

	@Override
	public int getCountAllocSourceAccount(AllocSourceAccountBean conditionBean) throws Exception {
		int result = 0;
		try {
			result = haeFilterHeaderMapper.getCountAllocDriverAccount();
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result + 2;
	}

	@Override
	public int createNewAllocSourceAccount(AllocSourceAccountBean createBean) throws Exception {
		if ((createBean == null)) {
			throw new Exception("The createNewAllocSourceAccount is empty.");
		}
		int result = 0;
		try {
			String sourceId = String.valueOf(createBean.getSourceId());
			String dimensionSegment = createBean.getDimensionSegment();
			int count = haeFilterHeaderMapper.existvaild("HAE_ALLOC_SOURCE_ACCOUNT", String.valueOf(0), sourceId,
					String.valueOf(0), dimensionSegment);
			boolean existvalid = count > 0;
			if (existvalid == false) {
				int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_SOURCE_ACCOUNT_S");
				createBean.setSourceAccId(primaryKey);
				result = haeFilterHeaderMapper.createAllocSourceAccount(createBean);
			}
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateAllocSourceAccount(AllocSourceAccountBean updateBean) throws Exception {
		if ((updateBean == null) || updateBean.getSourceId() == 0) {
			throw new Exception("The AllocSourceAccountBean is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateAllocSourceAccount(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	// XXX
	@Override
	public AllocTargetRecord getAllocTargetById(int parentid) throws Exception {
		if (parentid == 0) {
			throw new Exception("The AllocSourceBean identifier is empty.");
		}
		AllocTargetRecord returnBean = null;
		try {
			returnBean = haeFilterHeaderMapper.getAllocTargetById(parentid).get(0);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return returnBean;
	}

	@Override
	public List<Map<String, Object>> findAllocTargetAccount(AllocTargetAccountBean conditionBean) throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<AllocTargetAccountBean> result1 = null;
		int start = conditionBean.getRowStartIndex();
		int count = conditionBean.getRowCount();
		int targetId = conditionBean.getTargetId();
		result1 = haeFilterHeaderMapper.findAllocTargetAccount(start, targetId, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("TARGET_ACC_ID", result1.get(i).getTargetAccId());
			map.put("TARGET_ID", result1.get(i).getTargetId());
			map.put("LEDGER_ID", result1.get(i).getLedgerId());
			map.put("FIN_ELEMENT", result1.get(i).getFinElement());
			map.put("DIMENSION_SEGMENT", result1.get(i).getDimensionSegment());
			map.put("DIMENSION_NAME", result1.get(i).getDimensionName());
			map.put("DIMENSION_VALUE", result1.get(i).getDimensionValue());
			map.put("DIM_AllOC_TYPE", result1.get(i).getDimAllocType());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			result.add(map);
		}
		return result;
	}

	@Override
	public int getCountAllocTargetAccount(AllocTargetAccountBean conditionBean) throws Exception {
		int result = 0;
		try {
			result = haeFilterHeaderMapper.getCountAllocDriverAccount();
			// result =
			// this.allocationRulesSettingDao.getCountAllocTargetAccount(conditionBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result + 2;
	}

	@Override
	public int createNewAllocTargetAccount(AllocTargetAccountBean createBean) throws Exception {
		if ((createBean == null)) {
			throw new Exception("The createNewAllocTargetAccount is empty.");
		}
		int result = 0;
		try {
			String targetId = String.valueOf(createBean.getTargetId());
			String dimensionSegment = createBean.getDimensionSegment();
			int count = haeFilterHeaderMapper.existvaild("HAE_ALLOC_TARGET_ACCOUNT", String.valueOf(0),
					String.valueOf(0), targetId, dimensionSegment);
			boolean existvalid = count > 0;
			if (existvalid == false) {
				int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HAE_ALLOC_TARGET_ACCOUNT_S");
				createBean.setTargetAccId(primaryKey);
				result = haeFilterHeaderMapper.createNewAllocTargetAccount(createBean);
			}
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateAllocTargetAccount(AllocTargetAccountBean updateBean) throws Exception {
		if ((updateBean == null) || updateBean.getTargetAccId() == 0) {
			throw new Exception("The AllocSourceAccountBean is empty.");
		}
		int result = 0;
		try {
			result = haeFilterHeaderMapper.updateAllocTargetAccount(updateBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	public List<Map<String, Object>> getDimFilterListForCombo(DimFilterHeaderBean dfh) throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		String filterHeaderId = String.valueOf(dfh.getFilterHeaderId());
		String dimensionSegment = dfh.getDimensionSegment();
		result = haeFilterHeaderMapper.getDimFilterListForCombo(filterHeaderId, dimensionSegment);
		return result;
	}

	@Override
	public List<Map<String, Object>> findAllType(long allocRuleId) {
		List<Map<String, Object>> result = new ArrayList();
		try {
			result=haeFilterHeaderMapper.findAllType(allocRuleId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int getCountAllocSourceAccountTrue(AllocSourceAccountBean requestBean) throws Exception {
		int result = 0;
		try {
			result = haeFilterHeaderMapper.getCountAllocSourceAccountTrue(requestBean.getSourceId());
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int getCountAllocDriverAccountTrue(AllocDriverAccountBean requestBean) throws Exception {
		int result = 0;
		try {
			result = haeFilterHeaderMapper.getCountAllocDriverAccountTrue(requestBean.getDrvierId());
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int getCountAllocTargetAccountTrue(AllocTargetAccountBean requestBean) throws Exception {
		int result = 0;
		try {
			result =haeFilterHeaderMapper.getCountAllocTargetAccountTrue(requestBean.getTargetId());
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	// XXX RoleUserMap

	@Override
	public int getCountRoleUserMap(RoleUserMapRecord requestbean) throws Exception {
		int result = 0;
		try {
			//result = this.allocationRulesSettingDao.getCountRoleUserMap(requestbean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> findRoleUserMap(RoleUserMapRecord requestbean) throws Exception {
		//return this.allocationRulesSettingDao.findRoleUserMap(requestbean);
		return null;
	}

	@Override
	public int saveRoleUserMap(RoleUserMapRecord requestbean) throws Exception {
		if ((requestbean == null)) {
			throw new Exception("The RoleUserMapRecord is empty.");
		}
		int result = 0;
		try {
			//result = this.allocationRulesSettingDao.saveRoleUserMap(requestbean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateRoleUserMap(RoleUserMapRecord reqBean) throws Exception {
		if ((reqBean == null) || StringUtils.isBlank(reqBean.getId())) {
			throw new Exception("The RoleUserMapRecord is empty.");
		}
		int result = 0;
		try {
			//result = this.allocationRulesSettingDao.updateRoleUserMap(reqBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteRoleUserMap(RoleUserMapRecord reqBean) throws Exception {
		if ((reqBean == null) || StringUtils.isBlank(reqBean.getId())) {
			//throw new Exception("The RoleUserMapRecord is empty.");
		}
		int result = 0;
		try {
			//result = this.allocationRulesSettingDao.deleteRoleUserMap(reqBean);
		} catch (Exception se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getRoleList(RoleRecord requestbean) throws Exception {

		//return this.allocationRulesSettingDao.getRoleList(requestbean);
		return null;
	}

	@Override
	public int getCountRoleList(RoleRecord requestbean) throws Exception {
		//return this.allocationRulesSettingDao.getCountRoleList(requestbean);
		return 0;
	}

	// XXX other bean

}
