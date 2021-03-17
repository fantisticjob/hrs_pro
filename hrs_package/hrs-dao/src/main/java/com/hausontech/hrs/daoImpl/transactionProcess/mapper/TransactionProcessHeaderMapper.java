package com.hausontech.hrs.daoImpl.transactionProcess.mapper;

import java.util.List;

import com.hausontech.hrs.bean.transactionProcess.TransactionProcessHeader;

public interface TransactionProcessHeaderMapper {
    int deleteByPrimaryKey(Long txnHeaderId);

    int insert(TransactionProcessHeader record);

    int insertSelective(TransactionProcessHeader record);

    TransactionProcessHeader selectByPrimaryKey(Long txnHeaderId);
    
    List<TransactionProcessHeader> selectAll();
    
    Long selectCount();

    int updateByPrimaryKeySelective(TransactionProcessHeader record);

    int updateByPrimaryKey(TransactionProcessHeader record);
}