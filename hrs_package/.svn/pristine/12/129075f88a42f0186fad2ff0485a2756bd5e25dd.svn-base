package com.hausontech.hrs.daoImpl.transactionProcess.mapper;

import java.util.List;
import java.util.Map;

import com.hausontech.hrs.bean.transactionProcess.model.MrdmExcel;

public interface MrdmExcelMapper {
    int deleteByPrimaryKey(Integer id);

    boolean insert(MrdmExcel record);

    int insertSelective(MrdmExcel record);

    MrdmExcel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MrdmExcel record);

    int updateByPrimaryKey(MrdmExcel record);
    
    /**
     * 查找模糊查询excel条数
     * @param record
     * @return
     */
    long selectCount(Map record);
    
    /**
     * 查找模糊查询后所有Excel
     * @param record
     * @return
     */
    List<MrdmExcel> selectAll(Map record);
}