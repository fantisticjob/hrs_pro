package com.hausontech.hrs.serviceImpl.reportSetting;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.reportSetting.dao.IRptSettingDao;
import com.hausontech.hrs.api.reportSetting.service.IRptSettingService;
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
import com.hausontech.hrs.daoImpl.IBaseDao2;
import com.hausontech.hrs.daoImpl.dimensionManager.mapper.DimensionMapper;
import com.hausontech.hrs.daoImpl.reportSetting.mapper.RptSettingMapper;
import com.hausontech.hrs.exceptions.DataReferenceException;
import com.hausontech.hrs.utils.JsonUtil;

public class RptSettingServiceImpl implements IRptSettingService {
	/** 组件日志 */
	// private static Logger logger =
	// LoggerFactory.getLogger(ReportSettingController2.class);

	@Autowired
	private RptSettingMapper rptSettingMapper;

	//private IRptSettingDao rptSettingDao;
	@Autowired
	private DimensionMapper dimensionDao;
	@Autowired
	private IBaseDao2 baseDao2;

	public IBaseDao2 getBaseDao2() {
		return baseDao2;
	}

	public void setBaseDao2(IBaseDao2 baseDao2) {
		this.baseDao2 = baseDao2;
	}

	public RptSettingMapper getRptSettingMapper() {
		return rptSettingMapper;
	}

	public void setRptSettingMapper(RptSettingMapper rptSettingMapper) {
		this.rptSettingMapper = rptSettingMapper;
	}

	public DimensionMapper getDimensionDao() {
		return dimensionDao;
	}

	public void setDimensionDao(DimensionMapper dimensionDao) {
		this.dimensionDao = dimensionDao;
	}

//	public IRptSettingDao getRptSettingDao() {
//		return rptSettingDao;
//	}
//
//	public void setRptSettingDao(IRptSettingDao rptSettingDao) {
//		this.rptSettingDao = rptSettingDao;
//	}

	@Override
	public List<Map<String, Object>> getRuleHeaderList(ItemGroupRuleHeaderBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> map=null;
		try {
			List<ItemGroupRuleHeaderBean> result1 = null;
			result1 = rptSettingMapper.getRuleHeaderList(queryBean);
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				map.put("RULE_HEADER_ID",result1.get(i).getRuleHeaderId());
				map.put("RULE_CODE",result1.get(i).getRuleCode());
				map.put("DESCRIPTION",result1.get(i).getDescription());
				map.put("CREATION_DATE",result1.get(i).getCreationDate());
				map.put("CREATED_BY",result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY",result1.get(i).getLastUpdatedBy());
				result.add(map);
			}
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
	
	@Override
	public List<Map<String, Object>> getRuleHeaderListForSet(ItemGroupRuleHeaderBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		result= rptSettingMapper.getRuleHeaderListForSet(queryBean);
		return result;
	}

//	@Override
//	public int createNewRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, Exception {
//		if (queryBean == null) {
//			throw new Exception("The Item Rule Header bean is null.");
//		}
//		int result = 0;
//		try {
//			result = rptSettingDao.saveRuleHeader(queryBean);
//		} catch (SQLException se) {
//			throw new Exception(se);
//		}
//		return result;
//	}
//	
	
	@Override
	public int createNewRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		int result = 0;		
		int primaryKey = 0;
		//long result = 0;
		try {
			// get primary key from sequence
			primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_RULE_HEADER_S");
			queryBean.setRuleHeaderId(primaryKey);
			result = rptSettingMapper.saveRuleHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return primaryKey;
	}
	
	
//	@Override
//	public int updateRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, Exception {
//		if (queryBean == null || queryBean.getRuleHeaderId() == 0) {
//			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
//		}
//		int result = 0;
//		try {
//			result = rptSettingDao.updateRuleHeader(queryBean);
//		} catch (SQLException se) {
//			throw new Exception(se);
//		}
//		return result;
//	}
	
	@Override
	public int updateRuleHeader(ItemGroupRuleHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getRuleHeaderId() == 0) {
			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingMapper.updateRuleHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

//	@Override
//	public int deleteRuleHeader(ItemGroupRuleHeaderBean removedBean) throws DataReferenceException, Exception {
//		if (removedBean == null || removedBean.getRuleHeaderId() == 0) {
//			throw new Exception("The rule code cannot be deleted due to miss mandatory data in the bean object.");
//		}
//		int delCount = 0;
//		try {
//
//			// get record from db
//			ItemGroupRuleHeaderBean oriRuleHeaderBean = rptSettingDao
//					.getRuleHeaderByByPrimaryKey(removedBean.getRuleHeaderId());
//			if (oriRuleHeaderBean == null) {
//				throw new Exception("No record found in db for key:" + removedBean.getRuleHeaderId());
//			}
//			// try to check whether there's any record referenced with this
//			int referenceNum = rptSettingDao.getRuleHeaderReferencedNum(oriRuleHeaderBean);
//			if (0 < referenceNum) {
//				throw new DataReferenceException(
//						"There are some items references to the rule header, it cannot be deleted");
//			}
//			// try to delete from rule lines
//			rptSettingDao.deleteReferencedRuleLine(oriRuleHeaderBean);
//			// try to delete rule header
//			delCount = rptSettingDao.deleteRuleHeader(oriRuleHeaderBean);
//		} catch (SQLException se) {
//			System.out.println(se.toString());
//			throw new Exception(se);
//		}
//		return delCount;
//	}
	
	@Override
	public int deleteRuleHeader(ItemGroupRuleHeaderBean removedBean) throws DataReferenceException, Exception {
		if (removedBean == null || removedBean.getRuleHeaderId() == 0) {
			throw new Exception("The rule code cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {

			// get record from db
			ItemGroupRuleHeaderBean oriRuleHeaderBean = rptSettingMapper
					.getRuleHeaderByByPrimaryKey(removedBean.getRuleHeaderId());
			if (oriRuleHeaderBean == null) {
				throw new Exception("No record found in db for key:" + removedBean.getRuleHeaderId());
			}
			// try to check whether there's any record referenced with this
			int referenceNum = rptSettingMapper.getRuleHeaderReferencedNum(oriRuleHeaderBean);
			if (0 < referenceNum) {
				throw new DataReferenceException(
						"There are some items references to the rule header, it cannot be deleted");
			}
			// try to delete from rule lines
			rptSettingMapper.deleteReferencedRuleLine(oriRuleHeaderBean);
			// try to delete rule header
			delCount = rptSettingMapper.deleteRuleHeader(oriRuleHeaderBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}


//	@Override
//	public ItemGroupRuleHeaderBean getRuleHeaderByByPrimaryKey(int primaryKey) throws Exception {
//		if (primaryKey == 0) {
//			throw new Exception("The Rule Header identifier is empty.");
//		}
//		ItemGroupRuleHeaderBean itemHeaderBean = null;
//		try {
//			itemHeaderBean = rptSettingDao.getRuleHeaderByByPrimaryKey(primaryKey);
//		} catch (SQLException se) {
//			System.out.println(se.toString());
//			throw new Exception(se);
//		}
//		return itemHeaderBean;
//	}
	
	@Override
	public ItemGroupRuleHeaderBean getRuleHeaderByByPrimaryKey(int primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The Rule Header identifier is empty.");
		}
		ItemGroupRuleHeaderBean itemHeaderBean = null;
		try {
			itemHeaderBean = rptSettingMapper.getRuleHeaderByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemHeaderBean;
	}

//	@Override
//	public List<Map<String, Object>> getRuleLineList(ItemGroupRuleLineBean queryBean) throws Exception {
//		if (queryBean == null) {
//			throw new Exception("The Item Rule Header bean is null.");
//		}
//		List<Map<String, Object>> result = null;
//		try {
//			result = rptSettingDao.getRuleLineList(queryBean);
//		} catch (SQLException se) {
//			System.out.println(se.toString());
//			throw new Exception(se);
//		}
//		return result;
//	}

	
	@Override
	public List<Map<String, Object>> getRuleLineList(ItemGroupRuleLineBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> map=null;
		try {
			List<ItemGroupRuleLineBean> result1 = null;
			result1 = rptSettingMapper.getRuleLineList(queryBean);
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				map.put("RULE_LINE_ID",result1.get(i).getRuleLineId());
				map.put("RULE_HEADER_ID",result1.get(i).getRuleHeaderId());
				map.put("RULE_LINE_SEQ", result1.get(i).getRuleLineSeq());
				map.put("DIM_SEGMENT",result1.get(i).getDimSegment());
				map.put("DESCRIPTION",result1.get(i).getDimSegDescription());
				map.put("CREATION_DATE",result1.get(i).getCreationDate());
				map.put("CREATED_BY",result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY",result1.get(i).getLastUpdatedBy());
				result.add(map);
			}
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
//	@Override
//	public int createNewRuleLine(ItemGroupRuleLineBean queryBean) throws DuplicateKeyException, Exception {
//		if (queryBean == null) {
//			throw new Exception("The Item Rule Header bean is null.");
//		}
//		int result = 0;
//		try {
//			// try to get dimension inforamtion
//			DimensionBean dimBean = dimensionDao.getDimensionInfoBySegment(queryBean.getDimSegment());
//			if (dimBean != null && StringUtils.isBlank(dimBean.getDimensionName())) {
//				queryBean.setDimSegDescription(dimBean.getDimensionName());
//			}
//			result = rptSettingDao.saveRuleLineRecord(queryBean);
//		} catch (SQLException se) {
//			System.out.println(se.toString());
//			throw new Exception(se);
//		}
//		return result;
//	}
	
	
	@Override
	public int createNewRuleLine(ItemGroupRuleLineBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		int result = 0;
		int primaryKey = 0;
		try {
			// try to get dimension inforamtion  (已经替换成Mybatis)
			DimensionBean dimBean = dimensionDao.getDimensionInfoBySegment(queryBean.getDimSegment());
			if (dimBean != null && StringUtils.isBlank(dimBean.getDimensionName())) {
				queryBean.setDimSegDescription(dimBean.getDimensionName());
			}
			// get primary key from sequence
			primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_RULE_LINE_S");
			queryBean.setRuleLineId(primaryKey);
			result = rptSettingMapper.saveRuleLineRecord(queryBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return primaryKey;
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
			result = rptSettingMapper.updateRuleLineRecord(queryBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
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
			delCount = rptSettingMapper.deleteRuleLineRecord(removedBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public List<Map<String, Object>> getItmeHeaderList(ItemHeaderBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> map=null;
		try {
			List<ItemHeaderBean> result1 = null;
			result1 = rptSettingMapper.getItemHeaderList(queryBean);
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				map.put("ITEM_HEADER_ID",result1.get(i).getItemHeaderId());
				map.put("ITEM_CODE",result1.get(i).getItemCode());
				map.put("DESCRIPTION", result1.get(i).getItemDescription());
				map.put("RULE_CODE",result1.get(i).getRuleCode());
				map.put("IS_CONTENT",result1.get(i).getIsContent());
				map.put("IS_CALCULATION",result1.get(i).getIsCalculation());
				map.put("START_DATE",result1.get(i).getStartDate());
				map.put("END_DATE", result1.get(i).getEndDate());
				map.put("CREATION_DATE",result1.get(i).getCreationDate());
				map.put("CREATED_BY",result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY",result1.get(i).getLastUpdatedBy());
				result.add(map);
			}
		} catch (SQLException se) {
			//System.out.println(se.toString());
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
		long primaryKey = 0;
		try {
			// get primary key from sequence
			primaryKey = baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_ITEM_HEADER_S");
			queryBean.setItemHeaderId(primaryKey);
			result = rptSettingMapper.saveItemHeader(queryBean);
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
			result = rptSettingMapper.updateItemHeader(updateBean);
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
			delCount = rptSettingMapper.deleteItemHeader(removedBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
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
			itemHeaderBean = rptSettingMapper.getItemHeaderByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemHeaderBean;
	}

	@Override
	public List<Map<String, Object>> getItemContentList(ItemContentBean2 queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> map=null;
		try {
			List<ItemContentBean2> result1 = null;
			result1 = rptSettingMapper.getItemContentList(queryBean);
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				map.put("ITEM_CONTENT_ID",result1.get(i).getItemContentId());
				map.put("ITEM_HEADER_ID",result1.get(i).getItemHeaderId());
				map.put("ITEM_CODE", result1.get(i).getItemCode());
				map.put("LINE_NUM",result1.get(i).getLineNum());
				map.put("SIGN",result1.get(i).getSign());
				map.put("CONTENT_LOW",result1.get(i).getContentLow());
				map.put("CONTENT_HIGH",result1.get(i).getContentHigh());
				map.put("SEGMENT1_LOW", result1.get(i).getSegment1Low());
				map.put("SEGMENT1_HIGH",result1.get(i).getSegment1Hign());
				map.put("SEGMENT1_TYPE",result1.get(i).getSegment1Type());
				map.put("SEGMENT2_LOW", result1.get(i).getSegment2Low());
				map.put("SEGMENT2_HIGH",result1.get(i).getSegment2Hign());
				map.put("SEGMENT2_TYPE",result1.get(i).getSegment2Type());
				map.put("SEGMENT3_LOW", result1.get(i).getSegment3Low());
				map.put("SEGMENT3_HIGH",result1.get(i).getSegment3Hign());
				map.put("SEGMENT3_TYPE",result1.get(i).getSegment3Type());
				map.put("SEGMENT4_LOW", result1.get(i).getSegment4Low());
				map.put("SEGMENT4_HIGH",result1.get(i).getSegment4Hign());
				map.put("SEGMENT4_TYPE",result1.get(i).getSegment4Type());
				map.put("SEGMENT5_LOW", result1.get(i).getSegment5Low());
				map.put("SEGMENT5_HIGH",result1.get(i).getSegment5Hign());
				map.put("SEGMENT5_TYPE",result1.get(i).getSegment5Type());
				map.put("SEGMENT6_TYPE",result1.get(i).getSegment6Type());
				map.put("SEGMENT6_LOW", result1.get(i).getSegment6Low());
				map.put("SEGMENT6_HIGH",result1.get(i).getSegment6Hign());
				map.put("SEGMENT7_TYPE",result1.get(i).getSegment7Type());
				map.put("SEGMENT7_LOW", result1.get(i).getSegment7Low());
				map.put("SEGMENT7_HIGH",result1.get(i).getSegment7Hign());
				map.put("SEGMENT8_TYPE",result1.get(i).getSegment8Type());
				map.put("SEGMENT8_LOW", result1.get(i).getSegment8Low());
				map.put("SEGMENT8_HIGH",result1.get(i).getSegment8Hign());
				map.put("SEGMENT9_TYPE",result1.get(i).getSegment9Type());
				map.put("SEGMENT9_LOW", result1.get(i).getSegment9Low());
				map.put("SEGMENT9_HIGH",result1.get(i).getSegment9Hign());
				map.put("SEGMENT10_TYPE",result1.get(i).getSegment10Type());
				map.put("SEGMENT10_LOW", result1.get(i).getSegment10Low());
				map.put("SEGMENT10_HIGH",result1.get(i).getSegment10Hign());
				map.put("SEGMENT11_LOW", result1.get(i).getSegment11Low());
				map.put("SEGMENT11_HIGH",result1.get(i).getSegment11Hign());
				map.put("SEGMENT11_TYPE",result1.get(i).getSegment11Type());
				map.put("SEGMENT12_LOW", result1.get(i).getSegment12Low());
				map.put("SEGMENT12_HIGH",result1.get(i).getSegment12Hign());
				map.put("SEGMENT12_TYPE",result1.get(i).getSegment12Type());
				map.put("SEGMENT13_LOW", result1.get(i).getSegment13Low());
				map.put("SEGMENT13_HIGH",result1.get(i).getSegment13Hign());
				map.put("SEGMENT13_TYPE",result1.get(i).getSegment13Type());
				map.put("SEGMENT14_LOW", result1.get(i).getSegment14Low());
				map.put("SEGMENT14_HIGH",result1.get(i).getSegment14Hign());
				map.put("SEGMENT14_TYPE",result1.get(i).getSegment14Type());
				map.put("SEGMENT15_LOW", result1.get(i).getSegment15Low());
				map.put("SEGMENT15_HIGH",result1.get(i).getSegment15Hign());
				map.put("SEGMENT15_TYPE",result1.get(i).getSegment15Type());
				map.put("SEGMENT16_TYPE",result1.get(i).getSegment16Type());
				map.put("SEGMENT16_LOW", result1.get(i).getSegment16Low());
				map.put("SEGMENT16_HIGH",result1.get(i).getSegment16Hign());
				map.put("SEGMENT17_TYPE",result1.get(i).getSegment17Type());
				map.put("SEGMENT17_LOW", result1.get(i).getSegment17Low());
				map.put("SEGMENT17_HIGH",result1.get(i).getSegment17Hign());
				map.put("SEGMENT18_TYPE",result1.get(i).getSegment18Type());
				map.put("SEGMENT18_LOW", result1.get(i).getSegment18Low());
				map.put("SEGMENT18_HIGH",result1.get(i).getSegment18Hign());
				map.put("SEGMENT19_TYPE",result1.get(i).getSegment19Type());
				map.put("SEGMENT19_LOW", result1.get(i).getSegment19Low());
				map.put("SEGMENT19_HIGH",result1.get(i).getSegment19Hign());
				map.put("SEGMENT20_TYPE",result1.get(i).getSegment20Type());
				map.put("SEGMENT20_LOW", result1.get(i).getSegment20Low());
				map.put("SEGMENT20_HIGH",result1.get(i).getSegment20Hign());
				map.put("CREATION_DATE",result1.get(i).getCreationDate());
				map.put("CREATED_BY",result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY",result1.get(i).getLastUpdatedBy());
				result.add(map);
			}
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getItemCalculationList(ItemCalBean queryBean) throws Exception {
		if (queryBean == null || queryBean.getItemHeaderId() == 0) {
			throw new Exception("The Item Header bean is null or Item header id is 0.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> map=null;
		try {
			List<ItemCalBean> result1 = null;
			result1 = rptSettingMapper.getItemCalculationList(queryBean);
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				map.put("ITEM_CAL_ID",result1.get(i).getItemCalId());
				map.put("ITEM_HEADER_ID",result1.get(i).getItemHeaderId());
				map.put("ITEM_CODE", result1.get(i).getItemCode());
				map.put("LINE_NUM",result1.get(i).getLineNum());
				map.put("SIGN",result1.get(i).getSign());
				map.put("CAL_ITEM_CODE",result1.get(i).getCalItemCode());
				map.put("CONSTANT",result1.get(i).getConstant());
				map.put("CREATION_DATE",result1.get(i).getCreationDate());
				map.put("CREATED_BY",result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY",result1.get(i).getLastUpdatedBy());
				result.add(map);
			}
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getCalItemList(ItemHeaderBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Header bean is null or Item header id is 0.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> map=null;
		try {
			List<ItemHeaderBean> result1 = null;
			result1 = rptSettingMapper.getCalItemList(queryBean);
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				map.put("ITEM_HEADER_ID",result1.get(i).getItemHeaderId());
				map.put("ITEM_CODE",result1.get(i).getItemCode());
				map.put("DESCRIPTION", result1.get(i).getItemDescription());
				map.put("RULE_CODE",result1.get(i).getRuleCode());
				map.put("IS_CONTENT",result1.get(i).getIsContent());
				map.put("IS_CALCULATION",result1.get(i).getIsCalculation());
				map.put("START_DATE",result1.get(i).getStartDate());
				map.put("END_DATE", result1.get(i).getEndDate());
				map.put("CREATION_DATE",result1.get(i).getCreationDate());
				map.put("CREATED_BY",result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY",result1.get(i).getLastUpdatedBy());
				result.add(map);
			}
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
			result = rptSettingMapper.getItemCalculationCount(queryBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createItemContentHeader(ItemContentBean2 queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item content header bean is null.");
		}
		int result = 0;
		int primaryKey = 0;
		try {
			// get primary key from sequence
			primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_ITEM_CONTENT_S");
			queryBean.setItemContentId(primaryKey);
			result = rptSettingMapper.saveItemContenHeader(queryBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return primaryKey;
	}

	@Override
	public int updateItemContenHeader(ItemContentBean2 queryBean) throws Exception {
		if (queryBean == null || queryBean.getItemContentId() == 0) {
			throw new Exception("The Item content Header bean is null or content header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingMapper.updateItemContenHeader(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int deleteItemContentHeader(ItemContentBean2 removedBean) throws Exception {
		if (removedBean == null || removedBean.getItemContentId() == 0) {
			throw new Exception("The rule code cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {
			// try to delete rule header
			delCount = rptSettingMapper.deleteItemContentHeaderRecord(removedBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
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
		int primaryKey = 0;
		try {
			// get primary key from sequence
			primaryKey = (int)baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_ITEM_CALCULATION_S");
			calBean.setItemCalId(primaryKey);
			result = rptSettingMapper.saveItemCalculation(calBean);
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
			result = rptSettingMapper.updateItemCaculation(calBean);
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
			delCount = rptSettingMapper.deleteItemCalculation(removedBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public List<DimensionValueSetBean> getDimensionValueSetList() throws Exception {
		List<DimensionValueSetBean> result = null;
		try {
			result = rptSettingMapper.getDimensionValueSetList();
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<DimensionBean> getConfigurableLowHighDimList() throws Exception {
		List<DimensionBean> result = null;
		try {
			result = rptSettingMapper.getConfigurableLowHighDimList();
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public ItemContentBean2 getItemContentByByPrimaryKey(int primaryKey) throws Exception {
		if (primaryKey == 0) {
			throw new Exception("The Rule Header identifier is empty.");
		}
		ItemContentBean2 itemContentBean = null;
		try {
			itemContentBean = rptSettingMapper.getItemContentByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemContentBean;
	}

	@Override
	public int updateItemContenLowHigh(ItemContentBean2 record) throws Exception {
		if (record == null || record.getItemContentId() == 0) {
			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingMapper.updateItemContenLowHigh(record);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> getExtHeaderList(ItemCodeExtHeaderBean codeExtHeaderBean) throws Exception {

		// logger.error("进入 service rptset129行jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");

		if (codeExtHeaderBean == null) {
			throw new Exception("The Item EXT Header bean is null.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			List<ItemCodeExtHeaderBean> result1 = null;
			result1 = rptSettingMapper.getExtHeaderList(codeExtHeaderBean);
			for (int i = 0; i < result1.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("ITEM_TYPE_ID", result1.get(i).getExtItemTypeId());
				map.put("ITEM_TYPE", result1.get(i).getExtItemType());
				map.put("DESCRIPTION", result1.get(i).getDescription());
				map.put("CREATION_DATE", result1.get(i).getCreationDate());
				map.put("CREATED_BY", result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
				result.add(map);
			}
		} catch (SQLException se) {
			// System.out.println(se.toString());
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
		int primaryKey = 0;
		try {
			// get primary key from sequence
			primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_CORE_EXTERNAL_ITEM_TYPE_S");
			queryBean.setExtItemTypeId(primaryKey);
			result = rptSettingMapper.saveCodeExtHeader(queryBean);
		} catch (SQLException se) {
			// System.out.println(se.toString());
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
			result = rptSettingMapper.updateCodeExtHeader(queryBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
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
			ItemCodeExtHeaderBean oriCodeExtHeaderBean = rptSettingMapper
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
			rptSettingMapper.deleteReferencedCodeExtLine(oriCodeExtHeaderBean);
			// try to delete CodeExt header
			delCount = rptSettingMapper.deleteCodeExtHeader(oriCodeExtHeaderBean);
		} catch (SQLException se) {
			//System.out.println(se.toString());
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
			itemHeaderBean = rptSettingMapper.getCodeExtHeaderByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			//System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemHeaderBean;
	}

	@Override
	public List<Map<String, Object>> getLookUpHeaderList(ItemLookUpHeaderBean lookUpHeaderBean) throws Exception {
		if (lookUpHeaderBean == null) {
			throw new Exception("The Item lookUp Header bean is null.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			List<ItemLookUpHeaderBean> result1 = null;
			result1 = rptSettingMapper.getLookUpHeaderList(lookUpHeaderBean);
			for (int i = 0; i < result1.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("LOOKUP_TYPE_ID", result1.get(i).getLookUpTypeId());
				map.put("LOOKUP_TYPE", result1.get(i).getLookUpType());
				map.put("DESCRIPTION", result1.get(i).getDescription());
				map.put("CREATION_DATE", result1.get(i).getCreationDate());
				map.put("CREATED_BY", result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
				result.add(map);
			}

		} catch (SQLException se) {
			// System.out.println(se.toString());
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
		int primaryKey = 0;
		try {
			// get primary key from sequence
			primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_CORE_LOOKUP_TYPE_S");
			queryBean.setLookUpTypeId(primaryKey);
			result = rptSettingMapper.saveLookUpHeader(queryBean);
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
			result = rptSettingMapper.updateLookUpHeader(queryBean);
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
			ItemLookUpHeaderBean oriLookUpHeaderBean = rptSettingMapper
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
			rptSettingMapper.deleteReferencedLookUpLine(oriLookUpHeaderBean);
			// try to delete LookUp header
			delCount = rptSettingMapper.deleteLookUpHeader(oriLookUpHeaderBean);
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
			itemHeaderBean = rptSettingMapper.getLookUpHeaderByByPrimaryKey(primaryKey);
		} catch (SQLException se) {
			// System.out.println(se.toString());
			throw new Exception(se);
		}
		return itemHeaderBean;
	}

	@Override
	public List<Map<String, Object>> getCodeExtLineList(ItemCodeExtLineBean queryBean) throws Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> map=null;
		try {
			List<ItemCodeExtLineBean> result1 = null;
			result1 = rptSettingMapper.getCodeExtLineList(queryBean);
			for (int i = 0; i < result1.size(); i++) {
				map=new HashMap<String, Object>();
				map.put("ITEM_VALUE_ID",result1.get(i).getExtItemValueId());
				map.put("ITEM_TYPE_ID",result1.get(i).getExtItemTypeId());
				map.put("ITEM_VALUE", result1.get(i).getExtItemValue());
				map.put("DESCRIPTION",result1.get(i).getDescription());
				map.put("START_DATE",result1.get(i).getStartDate());
				map.put("END_DATE", result1.get(i).getEndDate());
				map.put("ATTRIBUTE1",result1.get(i).getAttribute1());
				map.put("ATTRIBUTE2",result1.get(i).getAttribute2());
				map.put("ATTRIBUTE3",result1.get(i).getAttribute3());
				map.put("ATTRIBUTE4", result1.get(i).getAttribute4());
				map.put("ATTRIBUTE5",result1.get(i).getAttribute5());
				map.put("ATTRIBUTE6",result1.get(i).getAttribute6());
				map.put("ATTRIBUTE7",result1.get(i).getAttribute7());
				map.put("ATTRIBUTE8", result1.get(i).getAttribute8());
				map.put("ATTRIBUTE9",result1.get(i).getAttribute9());
				map.put("ATTRIBUTE10",result1.get(i).getAttribute10());
				map.put("CREATION_DATE",result1.get(i).getCreationDate());
				map.put("CREATED_BY",result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY",result1.get(i).getLastUpdatedBy());
				result.add(map);
			}
		} catch (SQLException se) {
//			System.out.println(se.toString());
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
		int primaryKey = 0;
		try {
			// get primary key from sequence
			primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_CORE_EXTERNAL_ITEM_VALUE_S");
			queryBean.setExtItemValueId(primaryKey);
			// try to get dimension inforamtion
			result = rptSettingMapper.saveCodeExtLineRecord(queryBean);
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
			result = rptSettingMapper.updateCodeExtLineRecord(queryBean);
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
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		try {
			List<ItemLookUpLineBean> result1 = null;
			result1 = rptSettingMapper.getLookUpLineList(queryBean);
			for (int i = 0; i < result1.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("LOOKUP_VALUE_ID", result1.get(i).getLookUpValueId());
				map.put("LOOKUP_TYPE_ID", result1.get(i).getLookUpTypeId());
				map.put("LOOKUP_VALUE", result1.get(i).getLookUpValue());
				map.put("DESCRIPTION", result1.get(i).getDescription());
				map.put("START_DATE", result1.get(i).getStartDate());
				map.put("END_DATE", result1.get(i).getEndDate());
				map.put("ATTRIBUTE1", result1.get(i).getAttribute1());
				map.put("ATTRIBUTE2", result1.get(i).getAttribute2());
				map.put("ATTRIBUTE3", result1.get(i).getAttribute3());
				map.put("ATTRIBUTE4", result1.get(i).getAttribute4());
				map.put("ATTRIBUTE5", result1.get(i).getAttribute5());
				map.put("ATTRIBUTE6", result1.get(i).getAttribute6());
				map.put("ATTRIBUTE7", result1.get(i).getAttribute7());
				map.put("ATTRIBUTE8", result1.get(i).getAttribute8());
				map.put("ATTRIBUTE9", result1.get(i).getAttribute9());
				map.put("ATTRIBUTE10", result1.get(i).getAttribute10());
				map.put("CREATION_DATE", result1.get(i).getCreationDate());
				map.put("CREATED_BY", result1.get(i).getCreatedBy());
				map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
				map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
				result.add(map);
			}
		} catch (SQLException se) {
			// System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewLookUpLine(ItemLookUpLineBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item Rule Header bean is null.");
		}
		int primaryKey = 0;
		try {
			// get primary key from sequence
			primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_CORE_LOOKUP_VALUE_S");
			queryBean.setLookUpValueId(primaryKey);
			rptSettingMapper.saveLookUpLineRecord(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return primaryKey;
	}

	@Override
	public int updateLookUpLine(ItemLookUpLineBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getLookUpValueId() == 0) {
			throw new Exception("The Item LookUp Header bean is null or LookUp header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingMapper.updateLookUpLineRecord(queryBean);
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
		/*
		 * List<Map<String, Object>> result = null; try { result =
		 * rptSettingDao.getRowSetHeaderList(rowSetHeaderBean); } catch
		 * (SQLException se) { // System.out.println(se.toString()); throw new
		 * Exception(se); } return result;
		 */
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<ItemRowSetHeaderBean> result1 = null;
		String start = String.valueOf(rowSetHeaderBean.getRowStartIndex());
		String count = String.valueOf(rowSetHeaderBean.getRowCount());
		String rowSetName = rowSetHeaderBean.getRowSetName();
		String description = rowSetHeaderBean.getDescription();
		result1 = rptSettingMapper.getRowSetHeaderList(start, rowSetName, description, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("ROW_SET_ID", result1.get(i).getRowSetId());
			map.put("ROW_SET_NAME", result1.get(i).getRowSetName());
			map.put("RULE_CODE", result1.get(i).getRuleCode());
			map.put("EXT_ITEM_TYPE", result1.get(i).getExtItemType());
			map.put("DESCRIPTION", result1.get(i).getDescription());
			map.put("CREATION_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;
	}

	@Override
	public int createNewRowSetHeader(ItemRowSetHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Item RowSet Header bean is null.");
		}
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_ROW_SET_S");
		queryBean.setRowSetId(primaryKey);
		try {
			rptSettingMapper.saveRowSetHeader(queryBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return primaryKey;
	}

	@Override
	public int updateRowSetHeader(ItemRowSetHeaderBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getRowSetId() == 0) {
			throw new Exception("The Item RowSet Header bean is null or Rule header id is empty.");
		}
		int result = 0;
		try {
			result = rptSettingMapper.updateRowSetHeader(queryBean);
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
			List<ItemRowSetHeaderBean> result = null;
			ItemRowSetHeaderBean oriRowSetBean = null;
			try {
				result = rptSettingMapper.getRowSetHeaderByByPrimaryKey(removedBean.getRowSetId());
				oriRowSetBean=result.get(0);
			} catch (SQLException se) {
				System.out.println(se.toString());
				throw new Exception(se);
			}
			// remove releated rows
			rptSettingMapper.deleteRefRowSetLines(removedBean.getRowSetId());
			// try to delete LookUp header
			delCount = rptSettingMapper.deleteRowSetRecord(removedBean.getRowSetId());
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
		List<ItemRowSetHeaderBean> result = null;
		ItemRowSetHeaderBean itemHeaderBean = null;
		try {
			result = rptSettingMapper.getRowSetHeaderByByPrimaryKey(primaryKey);
			itemHeaderBean=result.get(0);
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
		Map<String, Object> map = null;
		List<Map<String, Object>> result = new ArrayList();
		List<ItemRowSetLineBean> result1 = null;
		String start = String.valueOf(lineBean.getRowStartIndex());
		String count = String.valueOf(lineBean.getRowCount());
		String rowName = lineBean.getRowName();
		String rowSetHeaderId = String.valueOf(lineBean.getRowSetId());
		result1 = rptSettingMapper.getRowSetLineList(start, rowName, rowSetHeaderId, count);
		for (int i = 0; i < result1.size(); i++) {
			map = new HashMap<String, Object>();
			map.put("ROW_ID", result1.get(i).getRowId());
			map.put("ROW_SET_ID", result1.get(i).getRowSetId());
			map.put("ROW_NUM", result1.get(i).getRowNum());
			map.put("LINE_NUM", result1.get(i).getLineNum());
			map.put("ROW_NAME", result1.get(i).getRowName());
			map.put("CHANGE_SIGN", result1.get(i).getChangeSign());
			map.put("DISPLAY_FLAG", result1.get(i).getDisplayFlag());
			map.put("EXTERNAL_CODE", result1.get(i).getExternalCode());
			map.put("ATTRIBUTE1", result1.get(i).getAttribute1());
			map.put("ATTRIBUTE2", result1.get(i).getAttribute2());
			map.put("ATTRIBUTE3", result1.get(i).getAttribute3());
			map.put("ATTRIBUTE4", result1.get(i).getAttribute4());
			map.put("ATTRIBUTE5", result1.get(i).getAttribute5());
			map.put("ATTRIBUTE6", result1.get(i).getAttribute6());
			map.put("ATTRIBUTE7", result1.get(i).getAttribute7());
			map.put("ATTRIBUTE8", result1.get(i).getAttribute8());
			map.put("ATTRIBUTE9", result1.get(i).getAttribute9());
			map.put("ATTRIBUTE10", result1.get(i).getAttribute10());
			map.put("CREATION_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
		}
		return result;

	}

	@Override
	public int createNewRowSetLine(ItemRowSetLineBean reqBean) throws Exception {
		if (reqBean == null) {
			throw new Exception("The Item RowSet line bean is null.");
		}
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_ROW_S");
		reqBean.setRowId(primaryKey);
		try {
			// try to get dimension inforamtion
			// DimensionBean dimBean =
			// dimensionDao.getDimensionInfoBySegment(reqBean.getDimSegment());
			// if (dimBean != null &&
			// StringUtils.isBlank(dimBean.getDimensionName())) {
			// reqBean.setDimSegDescription(dimBean.getDimensionName());
			// }
			rptSettingMapper.saveRowSetLineRecord(reqBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return primaryKey;
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
			result = rptSettingMapper.updateRowSetLineRecord(reqBean);
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
		ItemRowSetLineBean result = null;
		ItemRowSetLineBean itemLineBean = null;
		result = rptSettingMapper.getRowSetLineByByPrimaryKey(primaryKey);
		return result;
	}

	@Override
	public int deleteRowSetLine(ItemRowSetLineBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getRowId() == 0) {
			throw new Exception("The Row Set cannot be deleted due to miss mandatory data in the bean object.");
		}
		int delCount = 0;
		try {
			// get record from db
			ItemRowSetLineBean oriRowSetLine = rptSettingMapper.getRowSetLineByByPrimaryKey(removedBean.getRowId());
			if (oriRowSetLine == null) {
				throw new Exception("No record found in db for key:" + removedBean.getRowId());
			}

			// remove releated rows
			rptSettingMapper.deleteRefRowCalculations(removedBean.getRowId());
			// try to delete Row Line
			delCount = rptSettingMapper.deleteRowSetLineRecord(removedBean.getRowId());
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
		Map<String, Object> map=null;
		List<Map<String, Object>> result = new ArrayList();
		List<ItemRowCalculationBean> result1 = null;
		String start=String.valueOf(lineBean.getRowStartIndex());
		String count=String.valueOf(lineBean.getRowCount());
		String calItemCode=lineBean.getCalItemCode();
		String rowSetLineId=String.valueOf(lineBean.getRowId());
		result1 = rptSettingMapper.getRowCalculationList(start,calItemCode,rowSetLineId,count);
		for (int i = 0; i < result1.size(); i++) {
			map=new HashMap<String, Object>();
			map.put("ROW_CAL_ID",result1.get(i).getRowCalId());
			map.put("ROW_ID",result1.get(i).getRowId());
			map.put("ROW_CAL_NUM", result1.get(i).getRowCalNum());
			map.put("SIGN", result1.get(i).getSign());
			map.put("CAL_ITEM_CODE", result1.get(i).getCalItemCode());
			map.put("CREATION_DATE", result1.get(i).getCreationDate());
			map.put("CREATED_BY", result1.get(i).getCreatedBy());
			map.put("LAST_UPDATE_DATE", result1.get(i).getLastUpdateDate());
			map.put("LAST_UPDATED_BY", result1.get(i).getLastUpdatedBy());
			result.add(map);
			}
		return result;
	}

	@Override
	public int createNewRowCalculation(ItemRowCalculationBean reqBean) throws Exception {
		if (reqBean == null) {
			throw new Exception("The Item RowSet Cal bean is null.");
		}
		int primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_ROW_CALCULATION_S");
		reqBean.setRowCalId(primaryKey);
		try {
			// try to get dimension inforamtion
			// DimensionBean dimBean =
			// dimensionDao.getDimensionInfoBySegment(reqBean.getDimSegment());
			// if (dimBean != null &&
			// StringUtils.isBlank(dimBean.getDimensionName())) {
			// reqBean.setDimSegDescription(dimBean.getDimensionName());
			// }
			 rptSettingMapper.saveRowCalculationRecord(reqBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return primaryKey;
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
			result = rptSettingMapper.updateRowCalculationRecord(reqBean);
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
			delCount = rptSettingMapper.deleteRowCalculationRecord(removedBean.getRowCalId());
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}

	@Override
	public int getCountByRowSetHeaderBean(ItemRowSetHeaderBean reqBean) throws Exception {
		int result = 0;
		String rowSetName = reqBean.getRowSetName();
		String description = reqBean.getDescription();
		result = rptSettingMapper.countByRowSetHeaderBean(rowSetName, description);
		return result;
	}

	@Override
	public int getCountByLookUpHeaderBean(ItemLookUpHeaderBean lookUpHeaderBean) throws Exception {
		return rptSettingMapper.countByLookUpHeaderBean(lookUpHeaderBean);
	}

	@Override
	public int getCountByCodeExtHeaderBean(ItemCodeExtHeaderBean codeExtHeaderBean) throws Exception {
		return rptSettingMapper.countByCodeExtHeaderBean(codeExtHeaderBean);
	}

	@Override
	public int getCountByLookUpLineBean(ItemLookUpLineBean lookUpLineBean) throws Exception {

		return rptSettingMapper.countByLookUpLineBean(lookUpLineBean);
	}

	@Override
	public int getCountByRowSetLineBean(ItemRowSetLineBean lineBean) throws Exception {
		int result = 0;
		String rowName = lineBean.getRowName();
		String rowSetHeaderId = String.valueOf(lineBean.getRowSetId());
		result = rptSettingMapper.countByRowSetLineBean(rowName,rowSetHeaderId);
		return result;
	}

	@Override
	public int getCountByRowCalculationBean(ItemRowCalculationBean lineBean) throws Exception {
		int result=0;
		String calItemCode=lineBean.getCalItemCode();
		String rowSetLineId=String.valueOf(lineBean.getRowId());
		result= rptSettingMapper.countByRowCalculationBean(calItemCode,rowSetLineId);
		return result;
	}

	@Override
	public int getCountByCodeExtLineBean(ItemCodeExtLineBean codeExtLineBean) throws Exception {

		return rptSettingMapper.countByCodeExtLineBean(codeExtLineBean);
	}

	@Override
	public int getCountByItemGroupRuleHeaderBean(ItemGroupRuleHeaderBean ruleHeaderBean) throws Exception {
		// TODO Auto-generated method stub
		return rptSettingMapper.countByItemGroupRuleHeaderBean(ruleHeaderBean);
	}

//	@Override
//	public int getCountByItemGroupRuleLineBean(ItemGroupRuleLineBean ruleLineBean) throws Exception {
//		// TODO Auto-generated method stub
//		return rptSettingDao.countByItemGroupRuleLineBean(ruleLineBean);
//	}
	
	@Override
	public int getCountByItemGroupRuleLineBean(ItemGroupRuleLineBean ruleLineBean) throws Exception {
		// TODO Auto-generated method stub
		return rptSettingMapper.countByItemGroupRuleLineBean(ruleLineBean);
	}


	@Override
	public int getCountByItemHeaderBean(ItemHeaderBean itemRuleBean) throws Exception {
		// TODO Auto-generated method stub
		return rptSettingMapper.countByItemHeaderBean(itemRuleBean);
	}

	@Override
	public int getCountByItemContentBean(ItemContentBean2 contentBean) throws Exception {
		// TODO Auto-generated method stub
		return rptSettingMapper.countByItemContentBean(contentBean);
	}

	@Override
	public String getLoopUpByRuleCode(String description) throws Exception {
		String ruleCode = rptSettingMapper.getLoopUpByRuleCode(description);

		return ruleCode;
	}

	@Override
	public int copyRowSet(ItemRowSetHeaderBean headerBean) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = null;
		List<Map<String, Object>> listCal = null;
		ItemRowSetLineBean reqBean = null;
		ItemRowCalculationBean calBean = null;
		int flag = 0;
		int rowSet = 0;
		int rowCal = 0;
		int primaryKey=0;
		int primaryKey1=0;
		int primaryKey2=0;
		String append = "";
		String userName="";
		String lastString = "";
		int rowId = 0;
		try {
			String rowSetName = headerBean.getRowSetName();
			int length = rowSetName.length();
			int number = rptSettingMapper.getCountByCopyTableName(String.valueOf(length),rowSetName);
			rowSetName = rowSetName + "_" + number;
			headerBean.setRowSetName(rowSetName);
			list = rptSettingMapper.getRowIdByRowSetId(headerBean.getRowSetId());
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext() .getAuthentication() .getPrincipal();
			userName=userDetails.getUsername();
			headerBean.setLastUpdatedBy(userName);
			headerBean.setLastUpdateDate(new Date());
			primaryKey = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_ROW_SET_S");
			headerBean.setRowSetId(primaryKey);
			int rowSetheader = rptSettingMapper.saveRowSetHeader(headerBean);
			if (list != null && list.size() > 0) {
				String[] arr = new String[list.size()];
				for (int z = 0; z < arr.length; z++) {
					arr[z] = list.get(z).get("ROW_ID").toString();
					// list[i].
					reqBean = rptSettingMapper.getRowSetLineByByPrimaryKey(Integer.parseInt(arr[z]));
					reqBean.setRowSetId(primaryKey);
					rowId = (int) reqBean.getRowId();
					reqBean.setCreatedBy(userName);
					reqBean.setCreationDate(new Date());
					primaryKey1 = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_ROW_S");
					reqBean.setRowId(primaryKey1);
					rowSet = rptSettingMapper.saveRowSetLineRecord(reqBean);
					listCal = rptSettingMapper.getRowCalIdByRowId(rowId);
					if (listCal != null && listCal.size() > 0) {
						String[] calArr = new String[listCal.size()];
						for (int i = 0; i < calArr.length; i++) {
							calArr[i] = listCal.get(i).get("ROW_CAL_ID").toString();
							calBean = rptSettingMapper.getRowCalculationRecordByPrimaryKey(Integer.parseInt(calArr[i])).get(0);
							calBean.setRowId(primaryKey1);
							calBean.setCreatedBy(reqBean.getCreatedBy());
							calBean.setCreationDate(new Date());
							calBean.setLastUpdatedBy(userName);
							calBean.setLastUpdateDate(new Date());
							primaryKey2 = (int) baseDao2.getAutoGeneratedPrimaryKey("HRS_DEF_ROW_CALCULATION_S");
							calBean.setRowCalId(primaryKey2);
							rowCal = rptSettingMapper.saveRowCalculationRecord(calBean);
						}
					}
				}
			}
			flag = primaryKey1 + primaryKey;
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return flag;

	}
}
