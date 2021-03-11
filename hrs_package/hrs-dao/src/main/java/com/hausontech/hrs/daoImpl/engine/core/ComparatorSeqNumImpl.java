package com.hausontech.hrs.daoImpl.engine.core;

import java.util.Comparator;

import com.hausontech.hrs.bean.engine.CalculationProcessorBean;

public class ComparatorSeqNumImpl implements Comparator<CalculationProcessorBean> {

	@Override
	public int compare(CalculationProcessorBean calBeanOne, CalculationProcessorBean calBeanTwo) {
		int seqNum1 = calBeanOne.getLineNum();
		int seqNum2 = calBeanTwo.getLineNum();
		if(seqNum1 > seqNum2){
			return 1;
		} else if(seqNum1 < seqNum2){
			return -1;
		} else{
			return 0;
		}
	}
}
