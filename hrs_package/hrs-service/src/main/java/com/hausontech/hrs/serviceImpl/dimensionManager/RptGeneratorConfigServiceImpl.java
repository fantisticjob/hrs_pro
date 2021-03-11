package com.hausontech.hrs.serviceImpl.dimensionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DuplicateKeyException;

import com.hausontech.hrs.api.dimensionManager.dao.IDimensionDao;
import com.hausontech.hrs.api.dimensionManager.service.IRptGeneratorConfigService;
import com.hausontech.hrs.bean.dimensionManager.DimensionBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionSonValueBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionValueBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpHeaderBean;

public class RptGeneratorConfigServiceImpl implements IRptGeneratorConfigService {

	private IDimensionDao dimensionDao;
	
	public IDimensionDao getDimensionDao() {
		return dimensionDao;
	}

	public void setDimensionDao(IDimensionDao dimensionDao) {
		this.dimensionDao = dimensionDao;
	}


	@Override
	public List<Map<String, Object>> getByCondition(DimensionBean condition) throws SQLException {
		// TODO Auto-generated method stub
		try {
			return dimensionDao.selectByCondition(condition);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}

	@Override
	public int getCountByCondition(DimensionBean condition) throws SQLException {
		// TODO Auto-generated method stub
		return dimensionDao.countByCondition(condition);
	}
	
	@Override
	public long createNewDimension(DimensionBean newRecord) throws DuplicateKeyException, Exception {
	    if (newRecord == null) {
	    	throw new Exception("New dimension bean is null");
	    }
	    long result = 0;
	    try {
	    	result = dimensionDao.insert(newRecord);
	    } catch (SQLException se) {
	    	throw new Exception(se);
	    }
	    return result;
	}

	@Override
	public DimensionBean getDimensionByPrimaryKey(long key) throws Exception {
		if (key == 0) {
			throw new Exception("The dimension id is empty.");
		}
		try {
			return dimensionDao.selectByPrimaryKey(key);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}
	
	@Override
	public DimensionBean getDimensionByDimSegment(String segmentCode) throws Exception {
		if (StringUtils.isBlank(segmentCode)) {
			throw new Exception("The segment code is empty.");
		}
		try {
			return dimensionDao.getDimensionInfoBySegment(segmentCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}

	@Override
	public List<Map<String, Object>> getDimensionValueListByCondition(DimensionValueBean condition) throws Exception {
		if (condition == null) {
			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
		}
		try {
			return dimensionDao.getDimensionValueListByCondition(condition);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}
	
	
	@Override
	public int updateDimensionHeader(DimensionBean record) throws Exception {
		if (record == null || record.getDimensionId() == 0) {
			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
		}
		int result = 0;
		try {
			result = dimensionDao.updateByPrimaryKey(record);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int createNewDimensionValueRecord(DimensionValueBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null) {
			throw new Exception("The Dimension Value bean is null.");
		}
		int result = 0;
		try {
			result = dimensionDao.saveDimensionValueRecord(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
	

	@Override
	public int updateDimensionValueRecord(DimensionValueBean queryBean) throws DuplicateKeyException, Exception {
		if (queryBean == null || queryBean.getDimensionValueId() == 0) {
			throw new Exception("The Dimension value bean is null or dimension id is empty.");
		}
		int result = 0;
		try {
			//try to get dimension inforamtion 
			result = dimensionDao.updateDimensionValueRecord(queryBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}
	
	@Override
	public int deleteDimensionValueRecord(DimensionValueBean removedBean) throws Exception {
		if (removedBean == null || removedBean.getDimensionValueId() == 0) {
			throw new Exception("The Dimension value bean is null or dimension id is empty.");
		}
		int delCount = 0;
		try {
			//try to delete rule header
			delCount = dimensionDao.deleteDimensionValueRecord(removedBean);		
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return delCount;
	}


	@Override
	public List<Map<String, Object>> getDimensionSonValueListByCondition(DimensionSonValueBean dimValueSonBean)
			throws Exception {
		if (dimValueSonBean == null) {
			throw new Exception("The Item Rule Header bean is null or Rule header id is empty.");
		}
		try {
			return dimensionDao.getDimensionSonValueListByCondition(dimValueSonBean);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		}
	}

	@Override
	public int getCountByDimensionSonValueBean(DimensionSonValueBean dimValueSonBean) throws Exception {
		// TODO Auto-generated method stub
		return dimensionDao.countByDimensionSonValueBean(dimValueSonBean);
	}

	@Override
	public int createNewDimensionSonValueRecord(DimensionSonValueBean reqBean) throws Exception {
		if (reqBean == null) {
			throw new Exception("The Dimension Value bean is null.");
		}
		int result = 0;
		try {
			result = dimensionDao.saveDimensionSonValueRecord(reqBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int updateDimensionSonValueRecord(DimensionSonValueBean reqBean) throws Exception {
		if (reqBean == null || reqBean.getHierarchyId() == 0) {
			throw new Exception("The Dimension value bean is null or dimension id is empty.");
		}
		int result = 0;
		try {
			//try to get dimension inforamtion 
			result = dimensionDao.updateDimensionSonValueRecord(reqBean);
		} catch (SQLException se) {
			System.out.println(se.toString());
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public DimensionValueBean getDimensionValueByPrimaryKey(long parseLong, long parseLong2) throws Exception {
		if(parseLong == 0||parseLong2==0){
			throw new Exception("The dimension id is empty.");
		}
		try {
			return dimensionDao.selectDimensionValueBeanByPrimaryKey(parseLong,parseLong2);
		} catch (SQLException e) {
			
			throw new Exception(e);
		}
	}

	
	@Override
	public List<DimensionValueBean> getDimensionValueByCondition(DimensionValueBean queryBean) throws Exception {
		if(queryBean == null){
			throw new Exception("The dimension value query bean is empty.");
		}
		try {
			return dimensionDao.getDimensionValueByCondition(queryBean);
		} catch (SQLException e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public int getCountByCondition(DimensionValueBean condition) throws Exception {
		return dimensionDao.countByCondition(condition);
	}


	@Override
	public List<Map<String, Object>> getLookUpValueListAsDimension(ItemLookUpHeaderBean lookUpHeaderBean) throws Exception {
		if (lookUpHeaderBean == null) {
			throw new Exception("The Item lookUp Header bean is null.");
		}
		List<Map<String, Object>> result = null;
		try {
			result = dimensionDao.getLookUpValueListAsDimension(lookUpHeaderBean);
		} catch (SQLException se) {
			throw new Exception(se);
		}
		return result;
	}

	@Override
	public int countLookUpValueListByCondition(ItemLookUpHeaderBean lookUpHeaderBean) throws Exception {
		// TODO Auto-generated method stub
		return dimensionDao.countLookUpValueListByCondition(lookUpHeaderBean);
	}
}
