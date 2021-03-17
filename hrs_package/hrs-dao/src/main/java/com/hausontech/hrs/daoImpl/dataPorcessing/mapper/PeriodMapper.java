package com.hausontech.hrs.daoImpl.dataPorcessing.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hausontech.hrs.bean.dataProcess.PeriodBean;
@Repository
public interface PeriodMapper {
   
    List<PeriodBean>getPeriodList();//查所有
    
    List<PeriodBean>getPeriodListMapForDisplay();
    List<PeriodBean>getAvailablePeriodListMapForDisplay();
    List<PeriodBean>getAvailablePeriodList();
    
}