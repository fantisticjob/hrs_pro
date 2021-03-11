package com.hausontech.hrs.api.dimensionManager.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

import com.hausontech.hrs.bean.dimensionManager.DimensionBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionSonValueBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionValueBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpHeaderBean;

public interface IRptGeneratorConfigService {
	
	List<Map<String, Object>> getByCondition(DimensionBean condition) throws Exception;
	
	public int getCountByCondition(DimensionBean condition) throws Exception;
	
	public long createNewDimension(DimensionBean newRecord) throws DuplicateKeyException, Exception;
	
	public DimensionBean getDimensionByPrimaryKey(long key) throws Exception;
	
	public DimensionBean getDimensionByDimSegment(String segmentCode) throws Exception;
	
	List<Map<String, Object>> getDimensionValueListByCondition(DimensionValueBean condition) throws Exception;
	
	public int updateDimensionHeader(DimensionBean record) throws Exception;
	
	public int createNewDimensionValueRecord(DimensionValueBean queryBean) throws DuplicateKeyException, Exception;
	
	public int updateDimensionValueRecord(DimensionValueBean queryBean) throws DuplicateKeyException, Exception;
	
	public int deleteDimensionValueRecord(DimensionValueBean removedBean) throws Exception;

	public DimensionValueBean getDimensionValueByPrimaryKey(long parseLong, long parseLong2)throws Exception;

	List<Map<String, Object>> getDimensionSonValueListByCondition(DimensionSonValueBean dimValueSonBean)throws Exception;

	int getCountByDimensionSonValueBean(DimensionSonValueBean dimValueSonBean)throws Exception;

	int createNewDimensionSonValueRecord(DimensionSonValueBean reqBean)throws Exception;

	int updateDimensionSonValueRecord(DimensionSonValueBean reqBean)throws Exception;

	int getCountByCondition(DimensionValueBean dimValueBean)throws Exception;

	public List<DimensionValueBean> getDimensionValueByCondition(DimensionValueBean queryBean) throws Exception;
	
	public List<Map<String, Object>> getLookUpValueListAsDimension(ItemLookUpHeaderBean lookUpHeaderBean) throws Exception;
	
	public int countLookUpValueListByCondition(ItemLookUpHeaderBean lookUpHeaderBean) throws Exception;
	
}
