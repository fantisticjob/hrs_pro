package com.hausontech.hrs.daoImpl.dimensionManager.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.dimensionManager.DimensionBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionSonValueBean;
import com.hausontech.hrs.bean.dimensionManager.DimensionValueBean;
import com.hausontech.hrs.bean.reportSetting.ItemLookUpHeaderBean;
@Repository
public interface DimensionMapper {

	//插入维度段
	int insert(DimensionBean record) throws SQLException, DuplicateKeyException;
	
	//修改维度段
	int updateByPrimaryKey(DimensionBean record) throws SQLException;
	
	//根据dimensionCode和dimensionName查询
    List<DimensionBean> selectByCondition(DimensionBean condition) throws SQLException;
    
    //根据dimensionCode和dimensionName查询符合条件的记录总条数
    public int countByCondition(DimensionBean condition) throws SQLException;
    
    //根据dimensionId查询维度段
    DimensionBean selectByPrimaryKey(long key) throws SQLException;
    
    //录入(新增)维值
    public int saveDimensionValueRecord(DimensionValueBean dimensionValueRecord) throws DuplicateKeyException, SQLException;
    
    //更新维值
    public int updateDimensionValueRecord(DimensionValueBean record) throws DuplicateKeyException, SQLException;
    
    //删除维值
    public int deleteDimensionValueRecord(DimensionValueBean record) throws SQLException;

    //获取维值列表
    public List<DimensionValueBean> getDimensionValueListByCondition(DimensionValueBean condition) throws SQLException;
    
    //查询维值记录条数
	int countByConditionDimensionValue(DimensionValueBean condition)throws SQLException;
	
    //根据维度ID和维值ID查询维值的子值("头行"+headkey "父级"+parentkey)
	DimensionValueBean selectDimensionValueBeanByPrimaryKey(@Param("headkey")long headkey, @Param("parentkey")long parentkey) throws SQLException;

	//新增子值
	int saveDimensionSonValueRecord(DimensionSonValueBean reqBean)throws SQLException;

	//更新、修改子值
	int updateDimensionSonValueRecord(DimensionSonValueBean reqBean)throws SQLException;

	//查询维值的子值
	List<DimensionSonValueBean> getDimensionSonValueListByCondition(DimensionSonValueBean dimValueSonBean)throws SQLException;

	//查询子值记录条数
	int countByDimensionSonValueBean(DimensionSonValueBean dimValueSonBean)throws SQLException;
	
	//查询检查头表
	public List<Map<String, Object>> getLookUpValueListAsDimension(ItemLookUpHeaderBean lookUpHeaderBean) throws SQLException;
	
	//通过维度信息查询维度信息
	public DimensionBean getDimensionInfoBySegment(@Param("segment")String segment) throws SQLException;

	//查询检查头表记录条数
	public int countLookUpValueListByCondition(ItemLookUpHeaderBean lookUpHeaderBean) throws SQLException;  
	
	//根据条件查询维值
	public List<DimensionValueBean> getDimensionValueByCondition(DimensionValueBean queryBean) throws SQLException;
	
    /*
    int deleteByPrimaryKey(long key) throws SQLException;
    
	public DimensionBean getDimentsionBySegmentCode(String segmentCode) throws SQLException;
	*/


}