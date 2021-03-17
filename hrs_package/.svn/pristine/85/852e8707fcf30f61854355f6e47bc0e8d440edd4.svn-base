package com.hausontech.hrs.api.dimensionManager.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.dimensionManager.DimensionBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionSonValueBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionValueBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpHeaderBean;

public interface IDimensionDao {

	//插入维度段
	long insert(DimensionBean record) throws SQLException, DuplicateKeyException;
	
    List<Map<String, Object>> selectByCondition(DimensionBean condition) throws SQLException;
    
    DimensionBean selectByPrimaryKey(long key) throws SQLException;
       
    int updateByPrimaryKey(DimensionBean record) throws SQLException;
    
    int deleteByPrimaryKey(long key) throws SQLException;
    
    public int countByCondition(DimensionBean condition) throws SQLException;
    
    public List<Map<String, Object>> getDimensionValueListByCondition(DimensionValueBean condition) throws SQLException;
   
    public DimensionBean getDimensionInfoBySegment(String segment) throws SQLException;
    
    public int saveDimensionValueRecord(DimensionValueBean dimensionValueRecord) throws DuplicateKeyException, SQLException;
    
    public int updateDimensionValueRecord(DimensionValueBean record) throws DuplicateKeyException, SQLException;
    
    public int deleteDimensionValueRecord(DimensionValueBean record) throws SQLException;

	DimensionValueBean selectDimensionValueBeanByPrimaryKey(long parseLong, long parseLong2) throws SQLException;

	List<Map<String, Object>> getDimensionSonValueListByCondition(DimensionSonValueBean dimValueSonBean)throws SQLException;

	int countByDimensionSonValueBean(DimensionSonValueBean dimValueSonBean)throws SQLException;

	int saveDimensionSonValueRecord(DimensionSonValueBean reqBean)throws SQLException;

	int updateDimensionSonValueRecord(DimensionSonValueBean reqBean)throws SQLException;

	int countByCondition(DimensionValueBean condition)throws SQLException;
	
	public DimensionBean getDimentsionBySegmentCode(String segmentCode) throws SQLException;
	
	public List<DimensionValueBean> getDimensionValueByCondition(DimensionValueBean queryBean) throws SQLException;
	
	public List<Map<String, Object>> getLookUpValueListAsDimension(ItemLookUpHeaderBean lookUpHeaderBean) throws SQLException;
	
	public int countLookUpValueListByCondition(ItemLookUpHeaderBean lookUpHeaderBean) throws SQLException;
}