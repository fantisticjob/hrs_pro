package com.hausontech.hrs.daoImpl.transactionProcess.mapper;

import java.util.List;

import com.hausontech.hrs.bean.transactionProcess.TransactionProcType;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessTypeBean;

public interface TransactionProcTypeMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(TransactionProcType record);

    int insertSelective(TransactionProcType record);

    TransactionProcType selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(TransactionProcType record);

    int updateByPrimaryKey(TransactionProcType record);
    
    List<TransactionProcType> selectAll();
    
	public List <TransactionProcessTypeBean> getTransationProcessTypeList();
}