package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceBean;

@Repository
public interface RequestInstanceMapper {
	public List <RequestInstanceBean> getRequestInstanceList(RequestInstanceBean requestInstanceBean);
	public String getStatus(@Param("statusId")String statusId,@Param("statusType")String statusType);
	public int  getBatchInstanceNumber(RequestInstanceBean requestInstanceBean);
	public int  saveBatchJobRecord(RequestInstanceBean requestInstanceBean);
	public int updateBatchJobRecord(RequestInstanceBean requestInstanceBean);
	public int deleteBatchJobRecord(RequestInstanceBean requestInstanceBean);
	public RequestInstanceBean getRequestInstanceByPrimaryKey(RequestInstanceBean requestInstanceBean);
}