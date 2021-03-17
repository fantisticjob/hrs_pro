package com.hausontech.hrs.daoImpl.engine.core;

import java.util.Collections;
import java.util.List;

import com.hausontech.hrs.bean.engine.CalculationProcessorBean;

public class HrsCalProcessorBatchProxy extends HrsCalProcessorProxy implements Runnable {

	private List<CalculationProcessorBean> calProcessList = null;
	private CalculationProcessorBean calculateResult = null;
	
	public HrsCalProcessorBatchProxy(){}
	
	
	public HrsCalProcessorBatchProxy(List<CalculationProcessorBean> processList, CalculationProcessorBean groupCalResult) {
		calProcessList = processList;
		calculateResult = groupCalResult;
	}
	public List<CalculationProcessorBean> getCalProcessList() {
		return calProcessList;
	}

	public void setCalProcessList(List<CalculationProcessorBean> calProcessList) {
		this.calProcessList = calProcessList;
	}

	@Override
	public void run() {
		if (calProcessList != null && 0 < calProcessList.size()) {
			//order the record according to sequence number
			Collections.sort(calProcessList, new ComparatorSeqNumImpl());
			int counter = 0;
			for (CalculationProcessorBean calBean : calProcessList) {
				if (counter == 0) {
					this.initializeCalBean(calculateResult, calBean);
					String operator = calBean.getSign();
					if ("ABS".equals(operator)) {
						this.procssSingleAbsOperation(calculateResult);
					} else if ("-".equals(operator)) {
						this.procssSingleMinsOperation(calculateResult);
					}
					counter++;
					continue;
				}
				
				//process constant record
				if ("CONSTANT".equals(calBean.getCalItemCode())) {
					processConstantOperation(calculateResult, calBean);
				} else { //process index record
					processIndexerOperation(calculateResult, calBean);

				}
			}
			if (calculateResult.getItemCode().equals("A100-10134")) {
				System.out.println("found item in thread");
			}
		}
	}
	
	
	private void initializeCalBean(CalculationProcessorBean source, CalculationProcessorBean target) {
		
		source.setActualFlag(target.getActualFlag());
		source.setBeginBalanceCR(target.getBeginBalanceCR());
		source.setBeginBalanceDR(target.getBeginBalanceDR());
		source.setCurrencyCode(target.getCurrencyCode());
		source.setCurrencyType(target.getCurrencyType());
		source.setEndBalanceCR(target.getEndBalanceCR());
		source.setEndBalanceDR(target.getEndBalanceDR());
		
		source.setFinElement(target.getFinElement());
		source.setItemCode(target.getItemCode());
		source.setRuleCode(target.getRuleCode());
		source.setLedgerId(target.getLedgerId());
		source.setSign(target.getSign());
		source.setCalItemCode(target.getCalItemCode());
		source.setPeriodName(target.getPeriodName());
		source.setPeriodCR(target.getPeriodCR());
		source.setPeriodDR(target.getPeriodDR());
		source.setPeriodNumber(target.getPeriodNumber());
		source.setPeriodYear(target.getPeriodYear());
		source.setLineNum(target.getLineNum());
		source.setSegment1(target.getSegment1());
		source.setSegment2(target.getSegment2());
		source.setSegment3(target.getSegment3());
		source.setSegment4(target.getSegment4());
		source.setSegment5(target.getSegment5());
		source.setSegment6(target.getSegment6());
		source.setSegment7(target.getSegment7());
		source.setSegment8(target.getSegment8());
		source.setSegment9(target.getSegment9());
		source.setSegment10(target.getSegment10());
		source.setSegment11(target.getSegment11());
		source.setSegment12(target.getSegment12());
		source.setSegment13(target.getSegment13());
		source.setSegment14(target.getSegment14());
		source.setSegment15(target.getSegment15());
		source.setSegment16(target.getSegment16());
		source.setSegment17(target.getSegment17());
		source.setSegment18(target.getSegment18());
		source.setSegment19(target.getSegment19());
		source.setSegment20(target.getSegment20());
	}
	
	//process constant record
	private void processConstantOperation(CalculationProcessorBean result, CalculationProcessorBean target) {
		String operator = target.getSign();
		if ("/".equals(operator)) {
			//set begin balance dr
			result.setBeginBalanceDR(result.getBeginBalanceDR()/target.getBeginBalanceDR());
			//set begin balance cr
			result.setBeginBalanceCR(result.getBeginBalanceCR()/target.getBeginBalanceCR());
			//set period net dr
			result.setPeriodDR(result.getPeriodDR()/target.getPeriodDR());
			//set period net cr
			result.setPeriodCR(result.getPeriodCR()/target.getPeriodCR());
			//set end balance dr
			result.setEndBalanceDR(result.getEndBalanceDR()/target.getEndBalanceDR());
			//set end balance cr
			result.setEndBalanceCR(result.getEndBalanceCR()/target.getEndBalanceCR());
		} else if ("*".equals(operator)) {
			//set begin balance dr
			result.setBeginBalanceDR(target.getBeginBalanceDR()*result.getBeginBalanceDR());
			//set begin balance cr
			result.setBeginBalanceCR(target.getBeginBalanceCR()*result.getBeginBalanceCR());
			//set period net dr
			result.setPeriodDR(target.getPeriodDR()*result.getPeriodDR());
			//set period net cr
			result.setPeriodCR(target.getPeriodCR()*result.getPeriodCR());
			//set end balance dr
			result.setEndBalanceDR(target.getEndBalanceDR()*result.getEndBalanceDR());
			//set end balance cr
			result.setEndBalanceCR(target.getEndBalanceCR()*result.getEndBalanceCR());
		}
	}
	
	//process index record operation
	private void processIndexerOperation(CalculationProcessorBean result, CalculationProcessorBean target) {
		String operator = target.getSign();
		if ("ABS".equals(operator)) {
			this.procssSingleAbsOperation(target);
		} else if ("-".equals(operator)) {
			this.procssSingleMinsOperation(target);
		}
		
		//set begin balance dr
		result.setBeginBalanceDR(target.getBeginBalanceDR() + result.getBeginBalanceDR());
		//set begin balance cr
		result.setBeginBalanceCR(target.getBeginBalanceCR() + result.getBeginBalanceCR());
		//set period net dr
		result.setPeriodDR(target.getPeriodDR() + result.getPeriodDR());
		//set period net cr
		result.setPeriodCR(target.getPeriodCR() + result.getPeriodCR());
		//set end balance dr
		result.setEndBalanceDR(target.getEndBalanceDR() + result.getEndBalanceDR());
		//set end balance cr
		result.setEndBalanceCR(target.getEndBalanceCR() + result.getEndBalanceCR());
	}
	
	private void procssSingleAbsOperation(CalculationProcessorBean result) {
		//set begin balance dr
		result.setBeginBalanceDR(Math.abs(result.getBeginBalanceDR()));
		//set begin balance cr
		result.setBeginBalanceCR(Math.abs(result.getBeginBalanceCR()));
		//set period net dr
		result.setPeriodDR(Math.abs(result.getPeriodDR()));
		//set period net cr
		result.setPeriodCR(Math.abs(result.getPeriodCR()));
		//set end balance dr
		result.setEndBalanceDR(Math.abs(result.getEndBalanceDR()));
		//set end balance cr
		result.setEndBalanceCR(Math.abs(result.getEndBalanceCR()));
	}
	
	private void procssSingleMinsOperation(CalculationProcessorBean result) {
		//set begin balance dr
		result.setBeginBalanceDR(-1 * result.getBeginBalanceDR());
		//set begin balance cr
		result.setBeginBalanceCR(-1 * result.getBeginBalanceCR());
		//set period net dr
		result.setPeriodDR(-1 * result.getPeriodDR());
		//set period net cr
		result.setPeriodCR(-1 * result.getPeriodCR());
		//set end balance dr
		result.setEndBalanceDR(-1 * result.getEndBalanceDR());
		//set end balance cr
		result.setEndBalanceCR(-1 * result.getEndBalanceCR());
	} 

}
