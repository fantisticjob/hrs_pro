package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.dataProcess.AllocRequestInstanceBean;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;

@Repository
public interface AllocRequestInstanceMapper {
	public List <AllocRequestInstanceBean> getAllocRequestInstanceList(AllocRequestInstanceBean allocRequestInstanceBean);
	public int  getAllocBatchInstanceNumber(AllocRequestInstanceBean allocRequestInstanceBean);
	public int  saveAllocBatchJobRecord(AllocRequestInstanceBean allocRequestInstanceBean);
	public int updateAllocBatchJobRecord(AllocRequestInstanceBean allocRequestInstanceBean);
	public int deleteAllocBatchJobRecord(AllocRequestInstanceBean allocRequestInstanceBean);
	public AllocRequestInstanceBean getAllocRequestInstanceByPrimaryKey(AllocRequestInstanceBean allocRequestInstanceBean);
}