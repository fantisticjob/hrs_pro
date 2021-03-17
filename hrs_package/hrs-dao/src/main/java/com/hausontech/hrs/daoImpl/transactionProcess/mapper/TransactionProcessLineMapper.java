package com.hausontech.hrs.daoImpl.transactionProcess.mapper;

import java.util.List;
import java.util.Map;

import com.hausontech.hrs.bean.transactionProcess.TransactionProcessLine;

public interface TransactionProcessLineMapper {
    /**
     * 删除数据
     * @param txnLineId
     * @return
     */
    int deleteByPrimaryKey(Long txnLineId);

    /**
     * 插入数据
     * @param tranProcLine对象
     * @return int
     */
    int insert(TransactionProcessLine record);

    /**
     * 插入不为空的数据
     * @param tranProcLine对象
     * @return int
     */
    int insertSelective(TransactionProcessLine record);

    /**
     * （未用）通过主键找到tranProcLine数据
     * @param txnLineId
     * @return
     */
    TransactionProcessLine selectByPrimaryKey(Integer txnLineId);
    
    /**
     * 通过map中含有的headerId和排序信息得到排序后的tranProcLine数据
     * @param map
     * @return list
     */
    List<TransactionProcessLine> selectByHeaderID(Map<String,Object> map);
    
    /**
     * 通过map中含有的headerId得出记录数量
     * @param map
     * @return
     */
    Long selectCountByHeaderId(Map<String,Object> map);

    int updateByPrimaryKeySelective(TransactionProcessLine record);

    int updateByPrimaryKey(TransactionProcessLine record);
}