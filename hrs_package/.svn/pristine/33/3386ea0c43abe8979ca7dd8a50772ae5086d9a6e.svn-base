package com.hausontech.hrs.serviceImpl.transactionProcess;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hausontech.hrs.api.transactionProcess.dao.IMrdmExcelIDao;
import com.hausontech.hrs.api.transactionProcess.dao.ITransactionProcessDao;
import com.hausontech.hrs.api.transactionProcess.service.IMrdmExcelService;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessHeader;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessLine;
import com.hausontech.hrs.bean.transactionProcess.model.MrdmExcel;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.daoImpl.transactionProcess.mapper.MrdmExcelMapper;
import com.hausontech.hrs.daoImpl.transactionProcess.mapper.TransactionProcessHeaderMapper;
import com.hausontech.hrs.daoImpl.transactionProcess.mapper.TransactionProcessLineMapper;
import com.hausontech.hrs.utils.DateUtil;
import com.hausontech.hrs.utils.StringUtil;

@Service("mrdmExcelService")
public class MrdmExcelServiceImpl implements IMrdmExcelService {

//	@Autowired
//	private IMrdmExcelIDao mrdmExcelIDao;
	
	@Autowired
	private MrdmExcelMapper mrdmExcelMapper;
	
	@Autowired
	private TransactionProcessHeaderMapper txnProcessHeaderMapper;
	
	@Autowired
	private TransactionProcessLineMapper txnProcessLineMapper;

//	private ITransactionProcessDao txnProcessDao;

//	@Autowired
//	public void setTxnProcessDao(ITransactionProcessDao txnProcessDao) {
//		this.txnProcessDao = txnProcessDao;
//	}

	@Override
	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, MrdmExcel mrdmExcel) {
		EasyuiDataGridJson json = new EasyuiDataGridJson();
		Map<String, Serializable> record = new HashMap<String, Serializable>();
		record.put("order", dg);
		record.put("record", mrdmExcel);
		json.setRows(mrdmExcelMapper.selectAll(record));
		json.setTotal(mrdmExcelMapper.selectCount(record));
		return json;
	}

	public boolean saveExcel(String excelFile, List<Map<String, Object>> list, String operator) throws Exception {
		if (list != null && 0 < list.size()) {
			// loop process excel content
			TransactionProcessHeader transactionHeader = null;
			for (Map<String, Object> excelLine : list) {
				if (excelLine.get("0") == null || StringUtil.isEmptyTrim(excelLine.get("0").toString())) {
					continue;
				}
				String type = excelLine.get("0").toString().trim();
				// process transacction header
				if ("Header".equals(type)) {
					transactionHeader = validateTransactionHeader(excelLine);
					if (transactionHeader == null) {
						throw new Exception("事务处理头包含非法数据！");
					}
					transactionHeader.setTransactionDate(new Date());
					transactionHeader.setCreatedBy(operator);
					transactionHeader.setLastUpdatedBy(operator);
					transactionHeader.setCreationDate(new Date());
					transactionHeader.setLastUpdateDate(new Date());
					transactionHeader.setAuditFlag("N");
					transactionHeader.setTransferFlag("N");
					// save transaction header
					txnProcessHeaderMapper.insert(transactionHeader);
					if (transactionHeader == null || transactionHeader.getTxnHeaderId() == 0) {
						throw new Exception("事务头保存失败！");
					}
				} else if ("Line".equals(type)) { // process line
					TransactionProcessLine transactionLine = this.validateTransactionLine(excelLine);
					if (transactionLine == null) {
						throw new Exception("事务处理行包含非法数据！");
					} else {
						transactionLine.setTxnHeaderId(transactionHeader.getTxnHeaderId());
						transactionLine.setCreatedBy(operator);
						transactionLine.setLastUpdatedBy(operator);
						transactionLine.setCreationDate(new Date());
						transactionLine.setLastUpdateDate(new Date());
						
						if (transactionLine.getCreationDate() == null) {
							transactionLine.setCreationDate(new Date());
						}
						if (transactionLine.getLastUpdateDate() == null) {
							transactionLine.setLastUpdateDate(new Date());
						}
						txnProcessLineMapper.insert(transactionLine);
						
					//	txnProcessDao.createTransactionProcLine(transactionLine);
					}
				}
			}
		}
		MrdmExcel mrdmExcel = new MrdmExcel();
		mrdmExcel.setDataNum(String.valueOf(list.size()));
		mrdmExcel.setFileName(excelFile);
		mrdmExcel.setDataStatus("1");
		mrdmExcel.setCreatedatetime(new Date());
		mrdmExcel.setCreateBy(operator);
		
		// TODO Auto-generated method stub
		return mrdmExcelMapper.insert(mrdmExcel);
	}

	private TransactionProcessHeader validateTransactionHeader(Map<String, Object> excelLin) {
		TransactionProcessHeader transactionHeader = null;
		String transactionType = null;
		String legerId = null;
		String segment1 = null;
		String currencyCode = null;
		String periodName = null;
		String headerDesc = null;
		if (excelLin.get("1") != null) {
			transactionType = excelLin.get("1").toString();
		}
		if (excelLin.get("2") != null) {
			legerId = excelLin.get("2").toString();
		}
		if (excelLin.get("3") != null) {
			segment1 = excelLin.get("3").toString();
		}
		if (excelLin.get("4") != null) {
			currencyCode = excelLin.get("4").toString();
		}
		if (excelLin.get("5") != null) {
			periodName = excelLin.get("5").toString();
		}
		if (excelLin.get("6") != null) {
			headerDesc = excelLin.get("6").toString();
		}
		boolean isValidHeader = true;
		if (StringUtil.isEmptyTrim(transactionType) || StringUtil.isEmptyTrim(legerId)
				|| StringUtil.isEmptyTrim(currencyCode) || StringUtil.isEmptyTrim(periodName)
				|| StringUtil.isEmptyTrim(segment1)) {
			isValidHeader = false;
		}
		// check period format
		if (DateUtil.parseDate(periodName) == null) {
			isValidHeader = false;
		}
		// check ledgerId

		try {
			Integer.parseInt(legerId);
		} catch (NumberFormatException ne) {
			isValidHeader = false;
		}
		if (isValidHeader) {
			transactionHeader = new TransactionProcessHeader();
			transactionHeader.setTypeCode(transactionType);
			transactionHeader.setLedgerId(Integer.parseInt(legerId));
			transactionHeader.setCurrencyCode(currencyCode);
			transactionHeader.setPeriodName(periodName);
			transactionHeader.setDescription(headerDesc);
			transactionHeader.setCompanySegValue(segment1);
		}
		return transactionHeader;
	}

	private TransactionProcessLine validateTransactionLine(Map<String, Object> excelLin) {
		TransactionProcessLine transactionLine = null;
		String seqNumber = null;
		String legerId = null;
		String finElement = null;
		String segment1 = null;
		String segment2 = null;
		String segment3 = null;
		String segment4 = null;
		String segment5 = null;
		String segment6 = null;
		String segment7 = null;
		String segment8 = null;
		String segment9 = null;
		String segment10 = null;
		String segment11 = null;
		String amountDR = null;
		String amountCR = null;
		String lineDescription = null;

		if (excelLin.get("1") != null) {
			seqNumber = excelLin.get("1").toString();
		}
		if (excelLin.get("2") != null) {
			legerId = excelLin.get("2").toString();
		}
		if (excelLin.get("3") != null) {
			finElement = excelLin.get("3").toString();
		}
		if (excelLin.get("4") != null) {
			segment1 = excelLin.get("4").toString();
		}
		if (excelLin.get("5") != null) {
			segment2 = excelLin.get("5").toString();
		}
		if (excelLin.get("6") != null) {
			segment3 = excelLin.get("6").toString();
		}
		if (excelLin.get("7") != null) {
			segment4 = excelLin.get("7").toString();
		}
		if (excelLin.get("8") != null) {
			segment5 = excelLin.get("8").toString();
		}
		if (excelLin.get("9") != null) {
			segment6 = excelLin.get("9").toString();
		}
		if (excelLin.get("10") != null) {
			segment7 = excelLin.get("10").toString();
		}
		if (excelLin.get("11") != null) {
			segment8 = excelLin.get("11").toString();
		}
		if (excelLin.get("12") != null) {
			segment9 = excelLin.get("12").toString();
		}
		if (excelLin.get("13") != null) {
			segment10 = excelLin.get("13").toString();
		}
		if (excelLin.get("14") != null) {
			segment11 = excelLin.get("14").toString();
		}
		if (excelLin.get("15") != null) {
			amountDR = excelLin.get("15").toString();
		}
		if (excelLin.get("16") != null) {
			amountCR = excelLin.get("16").toString();
		}
		if (excelLin.get("17") != null) {
			lineDescription = excelLin.get("17").toString();
		}

		boolean isValidHeader = true;
		if (StringUtil.isEmptyTrim(legerId) || StringUtil.isEmptyTrim(finElement)
				|| StringUtil.isEmptyTrim(seqNumber)) {
			isValidHeader = false;
		}
		// check ledgerId

		try {
			Integer.parseInt(seqNumber);
			Integer.parseInt(legerId);
			// Double.parseDouble(amountDR);
			// Double.parseDouble(amountCR);
		} catch (NumberFormatException ne) {
			isValidHeader = false;
		}
		if (isValidHeader) {
			transactionLine = new TransactionProcessLine();
			transactionLine.setSeqNumber(Integer.parseInt(seqNumber));
			transactionLine.setLedgerId(Integer.parseInt(legerId));
			transactionLine.setFinElement(finElement);
			transactionLine.setSegment1(segment1);
			transactionLine.setSegment2(segment2);
			transactionLine.setSegment3(segment3);
			transactionLine.setSegment4(segment4);
			transactionLine.setSegment5(segment5);
			transactionLine.setSegment6(segment6);
			transactionLine.setSegment7(segment7);
			transactionLine.setSegment8(segment8);
			transactionLine.setSegment9(segment9);
			transactionLine.setSegment10(segment10);
			transactionLine.setSegment11(segment11);
			double amo = 0;
			if (!StringUtils.isBlank(amountDR)) {
				transactionLine.setAmountDR(Double.parseDouble(amountDR));
			} else {
				transactionLine.setAmountDR(amo);
			}
			if (!StringUtils.isBlank(amountCR)) {
				transactionLine.setAmountCR(Double.parseDouble(amountCR));
			} else {
				transactionLine.setAmountDR(amo);
			}
			transactionLine.setDescription(lineDescription);
		}
		return transactionLine;
	}
}
