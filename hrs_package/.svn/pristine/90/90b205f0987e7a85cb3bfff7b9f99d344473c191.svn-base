package com.hausontech.hrs.serviceImpl.reportSetting;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.dimensionManager.dao.IDimensionDao;
import com.hausontech.hrs.api.reportSetting.dao.IRptSettingDao;
import com.hausontech.hrs.api.reportSetting.service.IRptSettingService;
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
import com.hausontech.hrs.daoImpl.ReportSettingController;
import com.hausontech.hrs.exceptions.DataReferenceException;
import com.hausontech.hrs.utils.JsonUtil;

public class RptSettingServiceImpl implements IRptSettingService{
	/** 组件日志 */
	private static Logger logger = LoggerFactory.getLogger(ReportSettingController.class);

	private IRptSettingDao rptSettingDao;
	private IDimensionDao dimensionDao;

	public IDimensionDao getDimensionDao() {
		return dimensionDao;
	}

	public void setDimensionDao(IDimensionDao dimensionDao) {
		this.dimensionDao = dimensionDao;
	}

	public IRptSettingDao getRptSettingDao() {
		return rptSettingDao;
	}

	public void setRptSettingDao(IRptSettingDao rptSettingDao) {
		this.rptSettingDao = rptSettingDao;
	}

	@Override
	public List<Map<String, Object>> getRuleHeaderList(ItemGroupRuleHeaderBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getRuleHeaderList(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		int result = 0;
		try {
			result = rptSettingDao.saveRuleHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getRuleHeaderId() == 0) {
			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingDao.updateRuleHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteRuleHeader(ItemGroupRuleHeaderBean removedBean) throws DataReferenceException, Exception {
		if (removedBean == null || removedBean.getRuleHeaderId() == 0) {
			throw new Exception("The rule code cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {

			// get record from db
			ItemGroupRuleHeaderBean oriRuleHeaderBean = rptSettingDao
					.getRuleHeaderByByPrimaryKey(removedBean.getRuleHeaderId());
			if (oriRuleHeaderBean == null) {
				throw new Exception("No record found in db for key:" + removedBean.getRuleHeaderId());
			}
			// try to check whether there's any record referenced with this
			int referenceNum = rptSettingDao.getRuleHeaderReferencedNum(oriRuleHeaderBean);
			if (0 < referenceNum) {
				throw new DataReferenceException(
						"There are some items references to the rule header, it cannot be deleted");
			}
			// try to delete from rule lines
			rptSettingDao.deleteReferencedRuleLine(oriRuleHeaderBean);
			// try to delete rule header
			delCount = rptSettingDao.deleteRuleHeader(oriRuleHeaderBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public ItemGroupRuleHeaderBean getRuleHeaderByByPrimaryKey(int primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The Rule Header identifier is empty.");
		}
		ItemGroupRuleHeaderBean itemHeaderBean = null;
		try {
			itemHeaderBean = rptSettingDao.getRuleHeaderByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemHeaderBean;
	}

	@Override
	public List<Map<String, Object>> getRuleLineList(ItemGroupRuleLineBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getRuleLineList(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewRuleLine(ItemGroupRuleLineBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		int result = 0;
		try {
			// try to get dimension inforamtion
			DimensionBean dimBean = dimensionDao.getDimensionInfoBySegment(queryBean.getDimSegment());
			if (dimBean != null && StringUtils.isBlank(dimBean.getDimensionName())) {
				queryBean.setDimSegDescription(dimBean.getDimensionName());
			}
			result = rptSettingDao.saveRuleLineRecord(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateRuleLine(ItemGroupRuleLineBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getRuleLineId() == 0) {
			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
		}
		int result = 0;
		try {
			// try to get dimension inforamtion
			DimensionBean dimBean = dimensionDao.getDimensionInfoBySegment(queryBean.getDimSegment());
			if (dimBean != null && StringUtils.isBlank(dimBean.getDimensionName())) {
				queryBean.setDimSegDescription(dimBean.getDimensionName());
			}
			result = rptSettingDao.updateRuleLineRecord(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteRuleLine(ItemGroupRuleLineBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getRuleLineId() == 0) {
			throw new Exception("The rule code cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {
			// try to delete rule header
			delCount = rptSettingDao.deleteRuleLineRecord(removedBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public List<Map<String, Object>> getItmeHeaderList(ItemHeaderBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getItmeHeaderList(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public long createNewItemHeader(ItemHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		long result = 0;
		try {
			result = rptSettingDao.saveItemHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateItemHeader(ItemHeaderBean updateBean) throws DuplicateKeyException, Exception {
		if (updateBean == null || updateBean.getItemHeaderId() == 0) {
			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingDao.updateItemHeader(updateBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteItemHeader(ItemHeaderBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getItemHeaderId() == 0) {
			throw new Exception("The rule code cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {
			// try to delete rule header
			delCount = rptSettingDao.deleteItemHeader(removedBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@RequestMapping(value = "/getSignList")
	@ResponseBody
	public Object getSignList(HttpServletRequest request) {

		// Construct dimensionSegList
		List<Map<String, Object>> signList = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("signId", "+");
		map.put("signName", "+");
		signList.add(map);

		map = new HashMap<String, Object>();
		map.put("signId", "-");
		map.put("signName", "-");
		signList.add(map);

		return JsonUtil.fromObject(signList);
	}

	@Override
	public ItemHeaderBean getItemHeaderByByPrimaryKey(long primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The Item Header identifier is empty.");
		}
		ItemHeaderBean itemHeaderBean = null;
		try {
			itemHeaderBean = rptSettingDao.getItemHeaderByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemHeaderBean;
	}

	@Override
	public List<Map<String, Object>> getItemContentList(ItemContentBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getItemContentList(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getItemCalculationList(ItemCalBean queryBean) throws Exception {
		if (queryBean == null || queryBean.getItemHeaderId() == 0) {
			throw new Exception("The Item Header bean is null or Item header id is 0.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getItemCalculationList(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getCalItemList(ItemHeaderBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Header bean is null or Item header id is 0.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getCalItemList(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}
	
	@Override
	public int getItemCalculationCount(ItemCalBean queryBean) throws Exception {
		if (queryBean == null || queryBean.getItemHeaderId() == 0) {
			throw new Exception("The Item Header bean is null or Item header id is 0.");
		}
		int result = 0;
		try {
			result = rptSettingDao.getItemCalculationCount(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
	
	@Override
	public int createItemContentHeader(ItemContentBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item content header bean is null.");
		}
		int result = 0;
		try {
			// try to get dimension information
			result = rptSettingDao.saveItemContenHeader(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateItemContenHeader(ItemContentBean queryBean) throws Exception {
		if (queryBean == null || queryBean.getItemContentId() == 0) {
			throw new Exception("The Item content Header bean is null or content header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingDao.updateItemContenHeader(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteItemContentHeader(ItemContentBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getItemContentId() == 0) {
			throw new Exception("The rule code cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {
			// try to delete rule header
			delCount = rptSettingDao.deleteItemContentHeaderRecord(removedBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}
	
	
	@Override
	public int createItemCalculation(ItemCalBean calBean) throws Exception {
		if (calBean == null) {
			throw new Exception("The Item calculation bean is null.");
		}
		int result = 0;
		try {
			// try to get dimension information
			result = rptSettingDao.saveItemCalculation(calBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}
	
	@Override
	public int updateItemCalculation(ItemCalBean calBean) throws Exception {
		if (calBean == null || calBean.getItemCalId() == 0) {
			throw new Exception("The Item calculation bean is null or identifier is empty.");
		}
		int result = 0;
		try {
			// try to get dimension information
			result = rptSettingDao.updateItemCaculation(calBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}
	
	@Override
	public int deleteItemCalculation(ItemCalBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getItemCalId() == 0) {
			throw new Exception("The item calcualtion deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {
			// try to delete rule header
			delCount = rptSettingDao.deleteItemCalculation(removedBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public List<DimensionValueSetBean> getDimensionValueSetList() throws Exception {
		List<DimensionValueSetBean> result = null;
		try {
			result = rptSettingDao.getDimensionValueSetList();
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<DimensionBean> getConfigurableLowHighDimList() throws Exception {
		List<DimensionBean> result = null;
		try {
			result = rptSettingDao.getConfigurableLowHighDimList();
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
	
	@Override
	public ItemContentBean getItemContentByByPrimaryKey(int primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The Rule Header identifier is empty.");
		}
		ItemContentBean itemContentBean = null;
		try {
			itemContentBean = rptSettingDao.getItemContentByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemContentBean;
	}

	@Override
	public int updateItemContenLowHigh(ItemContentBean record) throws Exception {
		if (record == null || record.getItemContentId() == 0) {
			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingDao.updateItemContenLowHigh(record);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getExtHeaderList(ItemCodeExtHeaderBean codeExtHeaderBean) throws Exception {

		logger.error("进入 service rptset129行jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");

		if (codeExtHeaderBean == null) {
			throw new Exception("The Item EXT Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getExtHeaderList(codeExtHeaderBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;

	}

	@Override
	public int createNewCodeExtHeader(ItemCodeExtHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item CodeExt Header bean is null.");
		}
		int result = 0;
		try {
			result = rptSettingDao.saveCodeExtHeader(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateCodeExtHeader(ItemCodeExtHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getExtItemTypeId() == 0) {
			throw new Exception("The Item ext Header bean is null or ext header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingDao.updateCodeExtHeader(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteCodeExtHeader(ItemCodeExtHeaderBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getExtItemTypeId() == 0) {
			throw new Exception("The CodeExt code cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {

			// get record from db
			ItemCodeExtHeaderBean oriCodeExtHeaderBean = rptSettingDao
					.getCodeExtHeaderByByPrimaryKey(removedBean.getExtItemTypeId());

			if (oriCodeExtHeaderBean == null) {
				throw new Exception("No record found in db for key:" + removedBean.getExtItemTypeId());
			}
			// try to check whether there's any record referenced with this
			// int referenceNum =
			// rptSettingDao.getCodeExtHeaderReferencedNum(oriCodeExtHeaderBean);
			// if (0 < referenceNum) {
			// throw new DataReferenceException(
			// "There are some items references to the CodeExt header, it cannot
			// be deleted");
			// }
			// try to delete from CodeExt lines
			rptSettingDao.deleteReferencedCodeExtLine(oriCodeExtHeaderBean);
			// try to delete CodeExt header
			delCount = rptSettingDao.deleteCodeExtHeader(oriCodeExtHeaderBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public ItemCodeExtHeaderBean getCodeExtHeaderByByPrimaryKey(int primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The CodeExt Header identifier is empty.");
		}
		ItemCodeExtHeaderBean itemHeaderBean = null;
		try {
			itemHeaderBean = rptSettingDao.getCodeExtHeaderByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemHeaderBean;
	}

	@Override
	public List<Map<String, Object>> getLookUpHeaderList(ItemLookUpHeaderBean lookUpHeaderBean) throws Exception {
		if (lookUpHeaderBean == null) {
			throw new Exception("The Item lookUp Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getLookUpHeaderList(lookUpHeaderBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewLookUpHeader(ItemLookUpHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item LookUp Header bean is null.");
		}
		int result = 0;
		try {
			result = rptSettingDao.saveLookUpHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateLookUpHeader(ItemLookUpHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getLookUpTypeId() == 0) {
			throw new Exception("The Item LookUp Header bean is null or ext header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingDao.updateLookUpHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteLookUpHeader(ItemLookUpHeaderBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getLookUpTypeId() == 0) {
			throw new Exception("The LookUp code cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {

			// get record from db
			ItemLookUpHeaderBean oriLookUpHeaderBean = rptSettingDao
					.getLookUpHeaderByByPrimaryKey(removedBean.getLookUpTypeId());

			if (oriLookUpHeaderBean == null) {
				throw new Exception("No record found in db for key:" + removedBean.getLookUpTypeId());
			}
			// try to check whether there's any record referenced with this
			// int referenceNum =
			// rptSettingDao.getLookUpHeaderReferencedNum(oriLookUpHeaderBean);
			// if (0 < referenceNum) {
			// throw new DataReferenceException(
			// "There are some items references to the LookUp header, it cannot
			// be deleted");
			// }
			// try to delete from LookUp lines
			rptSettingDao.deleteReferencedLookUpLine(oriLookUpHeaderBean);
			// try to delete LookUp header
			delCount = rptSettingDao.deleteLookUpHeader(oriLookUpHeaderBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public ItemLookUpHeaderBean getLookUpHeaderByByPrimaryKey(int primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The LookUp Header identifier is empty.");
		}
		ItemLookUpHeaderBean itemHeaderBean = null;
		try {
			itemHeaderBean = rptSettingDao.getLookUpHeaderByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemHeaderBean;
	}

	@Override
	public List<Map<String, Object>> getCodeExtLineList(ItemCodeExtLineBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getCodeExtLineList(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewCodeExtLine(ItemCodeExtLineBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		int result = 0;
		try {
			// try to get dimension inforamtion
			result = rptSettingDao.saveCodeExtLineRecord(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateCodeExtLine(ItemCodeExtLineBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getExtItemValueId() == 0) {
			throw new Exception("The Item codeext Header bean is null or codeext header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingDao.updateCodeExtLineRecord(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getLookUpLineList(ItemLookUpLineBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getLookUpLineList(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewLookUpLine(ItemLookUpLineBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		int result = 0;
		try {
			// try to get dimension inforamtion
			result = rptSettingDao.saveLookUpLineRecord(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateLookUpLine(ItemLookUpLineBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getLookUpValueId() == 0) {
			throw new Exception("The Item LookUp Header bean is null or LookUp header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingDao.updateLookUpLineRecord(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getRowSetHeaderList(ItemRowSetHeaderBean rowSetHeaderBean) throws Exception {
		if (rowSetHeaderBean == null) {
			throw new Exception("The Item RowSet Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getRowSetHeaderList(rowSetHeaderBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewRowSetHeader(ItemRowSetHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item RowSet Header bean is null.");
		}
		int result = 0;
		try {
			result = rptSettingDao.saveRowSetHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateRowSetHeader(ItemRowSetHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getRowSetId() == 0) {
			throw new Exception("The Item RowSet Header bean is null or Rule header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingDao.updateRowSetHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}
	
	
	@Override
	public int deleteRowSetHeader(ItemRowSetHeaderBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getRowSetId() == 0) {
			throw new Exception("The Row Set cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {
			// get record from db
			ItemRowSetHeaderBean oriRowSetBean = 
					rptSettingDao.getRowSetHeaderByByPrimaryKey(removedBean.getRowSetId());
			if (oriRowSetBean == null) {
				throw new Exception("No record found in db for key:" + removedBean.getRowSetId());
			}
			
			//remove releated rows
			rptSettingDao.deleteRefRowSetLines(removedBean);
			// try to delete LookUp header
			delCount = rptSettingDao.deleteRowSetRecord(removedBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public ItemRowSetHeaderBean getRowSetHeaderByByPrimaryKey(int primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The RowSet Header identifier is empty.");
		}
		ItemRowSetHeaderBean itemHeaderBean = null;
		try {
			itemHeaderBean = rptSettingDao.getRowSetHeaderByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemHeaderBean;
	}

	@Override
	public List<Map<String, Object>> getRowSetLineList(ItemRowSetLineBean lineBean) throws Exception {
		if (lineBean == null) {
			throw new Exception("The Item RowSet line bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getRowSetLineList(lineBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewRowSetLine(ItemRowSetLineBean reqBean) throws Exception {
		if (reqBean == null) {
			throw new Exception("The Item RowSet line bean is null.");
		}
		int result = 0;
		try {
			// try to get dimension inforamtion
			// DimensionBean dimBean =
			// dimensionDao.getDimensionInfoBySegment(reqBean.getDimSegment());
			// if (dimBean != null &&
			// StringUtils.isBlank(dimBean.getDimensionName())) {
			// reqBean.setDimSegDescription(dimBean.getDimensionName());
			// }
			result = rptSettingDao.saveRowSetLineRecord(reqBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateRowSetLine(ItemRowSetLineBean reqBean) throws Exception {
		if (reqBean == null || reqBean.getRowId() == 0) {
			throw new Exception("The Item RowSet Line bean is null or RowSet Line id is empty.");
		}
		int result = 0;
		try {
			// try to get dimension inforamtion
			// DimensionBean dimBean =
			// dimensionDao.getDimensionInfoBySegment(reqBean.getDimSegment());
			// if (dimBean != null &&
			// StringUtils.isBlank(dimBean.getDimensionName())) {
			// reqBean.setDimSegDescription(dimBean.getDimensionName());
			// }
			result = rptSettingDao.updateRowSetLineRecord(reqBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public ItemRowSetLineBean getRowSetLineByByPrimaryKey(int primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The RowSet Line identifier is empty.");
		}
		ItemRowSetLineBean itemLineBean = null;
		try {
			itemLineBean = rptSettingDao.getRowSetLineByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemLineBean;
	}
	
	@Override
	public int deleteRowSetLine(ItemRowSetLineBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getRowId() == 0) {
			throw new Exception("The Row Set cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {
			// get record from db
			ItemRowSetLineBean oriRowSetLine = 
					rptSettingDao.getRowSetLineByByPrimaryKey(removedBean.getRowId());
			if (oriRowSetLine == null) {
				throw new Exception("No record found in db for key:" + removedBean.getRowId());
			}
			
			//remove releated rows
			rptSettingDao.deleteRefRowCalculations(removedBean);
			// try to delete Row Line
			delCount = rptSettingDao.deleteRowSetLineRecord(removedBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public List<Map<String, Object>> getRowCalculationList(ItemRowCalculationBean lineBean) throws Exception {
		if (lineBean == null) {
			throw new Exception("The Item RowSet  Cal bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = rptSettingDao.getRowCalculationList(lineBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewRowCalculation(ItemRowCalculationBean reqBean) throws Exception {
		if (reqBean == null) {
			throw new Exception("The Item RowSet Cal bean is null.");
		}
		int result = 0;
		try {
			// try to get dimension inforamtion
			// DimensionBean dimBean =
			// dimensionDao.getDimensionInfoBySegment(reqBean.getDimSegment());
			// if (dimBean != null &&
			// StringUtils.isBlank(dimBean.getDimensionName())) {
			// reqBean.setDimSegDescription(dimBean.getDimensionName());
			// }
			result = rptSettingDao.saveRowCalculationRecord(reqBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateRowCalculation(ItemRowCalculationBean reqBean) throws Exception {
		if (reqBean == null || reqBean.getRowCalId() == 0) {
			throw new Exception("The Item RowSet Cal bean is null or RowSet Cal id is empty.");
		}
		int result = 0;
		try {
			// try to get dimension inforamtion
			// DimensionBean dimBean =
			// dimensionDao.getDimensionInfoBySegment(reqBean.getDimSegment());
			// if (dimBean != null &&
			// StringUtils.isBlank(dimBean.getDimensionName())) {
			// reqBean.setDimSegDescription(dimBean.getDimensionName());
			// }
			result = rptSettingDao.updateRowCalculationRecord(reqBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteRowCalculation(ItemRowCalculationBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getRowCalId() == 0) {
			throw new Exception("The Row Set cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {
			// try to delete Row calculation
			delCount = rptSettingDao.deleteRowCalculationRecord(removedBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}
	
	@Override
	public int getCountByRowSetHeaderBean(ItemRowSetHeaderBean reqBean) throws Exception {
		return rptSettingDao.countByRowSetHeaderBean(reqBean);
	}

	@Override
	public int getCountByLookUpHeaderBean(ItemLookUpHeaderBean lookUpHeaderBean) throws Exception {
		return rptSettingDao.countByLookUpHeaderBean(lookUpHeaderBean);
	}

	@Override
	public int getCountByCodeExtHeaderBean(ItemCodeExtHeaderBean codeExtHeaderBean) throws Exception {
		return rptSettingDao.countByCodeExtHeaderBean(codeExtHeaderBean);
	}

	@Override
	public int getCountByLookUpLineBean(ItemLookUpLineBean lookUpLineBean) throws Exception {
		
		return rptSettingDao.countByLookUpLineBean(lookUpLineBean);
	}

	@Override
	public int getCountByRowSetLineBean(ItemRowSetLineBean lineBean) throws Exception {
		return rptSettingDao.countByRowSetLineBean(lineBean);
	}

	@Override
	public int getCountByRowCalculationBean(ItemRowCalculationBean lineBean) throws Exception {
		return rptSettingDao.countByRowCalculationBean(lineBean);
	}
	@Override
	public int getCountByCodeExtLineBean(ItemCodeExtLineBean codeExtLineBean)throws Exception {
		
		return rptSettingDao.countByCodeExtLineBean(codeExtLineBean);
	}

	@Override
	public int getCountByItemGroupRuleHeaderBean(ItemGroupRuleHeaderBean ruleHeaderBean) throws Exception {
		// TODO Auto-generated method stub
		return rptSettingDao.countByItemGroupRuleHeaderBean(ruleHeaderBean);
	}

	@Override
	public int getCountByItemGroupRuleLineBean(ItemGroupRuleLineBean ruleLineBean) throws Exception {
		// TODO Auto-generated method stub
		return rptSettingDao.countByItemGroupRuleLineBean(ruleLineBean);
	}

	@Override
	public int getCountByItemHeaderBean(ItemHeaderBean itemRuleBean) throws Exception {
		// TODO Auto-generated method stub
		return rptSettingDao.countByItemHeaderBean(itemRuleBean);
	}

	@Override
	public int getCountByItemContentBean(ItemContentBean contentBean) throws Exception {
		// TODO Auto-generated method stub
		return rptSettingDao.countByItemContentBean(contentBean);
	}

	@Override
	public String getLoopUpByRuleCode(String description) throws Exception{
		String ruleCode = rptSettingDao.getLoopUpByRuleCode(description);
		
		return ruleCode;
	}
}
