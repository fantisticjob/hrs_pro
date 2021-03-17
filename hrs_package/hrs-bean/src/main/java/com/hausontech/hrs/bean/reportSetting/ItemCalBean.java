package com.hausontech.hrs.bean.reportSetting;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.hausontech.hrs.bean.AuditBean2;
import com.hausontech.hrs.bean.IBaseBeanService;

public class ItemCalBean extends AuditBean2 implements IBaseBeanService {
	private int itemCalId;
	private long itemHeaderId;
	private String itemCode;
	private int lineNum;
	private String sign;
	private String calItemCode;
	private String constant;
	public int getItemCalId() {
		return itemCalId;
	}
	public void setItemCalId(int itemCalId) {
		this.itemCalId = itemCalId;
	}
	public long getItemHeaderId() {
		return itemHeaderId;
	}
	public void setItemHeaderId(long itemHeaderId) {
		this.itemHeaderId = itemHeaderId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public int getLineNum() {
		return lineNum;
	}
	public void setLineNum(int lineNum) {
		this.lineNum = lineNum;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCalItemCode() {
		return calItemCode;
	}
	public void setCalItemCode(String calItemCode) {
		this.calItemCode = calItemCode;
	}
	public String getConstant() {
		return constant;
	}
	public void setConstant(String constant) {
		this.constant = constant;
	}
	
	public boolean isEmpty() {
    	return true;
    }
	@Override
	public void constructBeanWithRequest(HttpServletRequest request, boolean isCreate) throws Exception {
		if (request != null) {
			//set item content id
			String itemCalId = this.getDecodedRequestParam(request, "ITEM_CAL_ID");
			if (!StringUtils.isBlank(itemCalId)) {
				this.setItemCalId(Integer.parseInt(itemCalId));
			}
			String itemHeaderId = null;
			if (!isCreate) {
				//set rule header id
				itemHeaderId = this.getDecodedRequestParam(request, "ITEM_HEADER_ID");
				if (!StringUtils.isBlank(itemHeaderId)) {
					this.setItemHeaderId(Integer.parseInt(itemHeaderId));
				}
				this.setItemCode(this.getDecodedRequestParam(request, "ITEM_CODE"));
			} 
		
			//set line number
			String lineSeq = this.getDecodedRequestParam(request, "LINE_NUM");
			if (!StringUtils.isBlank(lineSeq)) {
				this.setLineNum(Integer.parseInt(lineSeq));
			}
			//set sign 
			this.setSign(request.getParameter("SIGN"));
			//set cal item code
			this.setCalItemCode(request.getParameter("CAL_ITEM_CODE"));
			//set constant
			String constantStr = this.getDecodedRequestParam(request, "CONSTANT");
			if (!StringUtils.isBlank(constantStr)) {
				//try to parse the constant
				Double.parseDouble(constantStr);
				this.setConstant(constantStr);
			}
		}
	}
	
	@Override
	public int inputValidate(boolean isCreated) throws Exception {
		int errorCode = 0;
		if (!isCreated) {
			if (this.getItemCalId() == 0) {
				errorCode = -1;
			}
		}
		if (this.getItemHeaderId() == 0 || StringUtils.isBlank(this.getItemCode()) ||
				(StringUtils.isBlank(this.getCalItemCode()) && StringUtils.isBlank(this.getConstant()))) {
			errorCode = -2;
		}
		
		if (this.getLineNum() == 0 || StringUtils.isBlank(this.getSign())) {
			errorCode = -3;
		}
		if (!StringUtils.isBlank(this.getCalItemCode()) && !StringUtils.isBlank(this.getConstant())) {
			errorCode = -4;
		}
		String sign = this.getSign();
		if (("+".equals(sign) || "-".equals(sign)) && !StringUtils.isBlank(this.getConstant())) {
			errorCode = -4;
		}
		if (("*".equals(sign) || "/".equals(sign)) && !StringUtils.isBlank(this.getCalItemCode())) {
			errorCode = -4;
		}
		return errorCode;
	}
	@Override
	public Map constructReturnMap(boolean isCreated) {
		Map dataMap = new HashMap();
		dataMap.put("ITEM_CAL_ID", this.getItemCalId());
		dataMap.put("ITEM_HEADER_ID", this.getItemHeaderId());
		dataMap.put("ITEM_CODE", this.getItemCode());
		dataMap.put("LINE_NUM", this.getLineNum());
		dataMap.put("SIGN", this.getSign());
		dataMap.put("CAL_ITEM_CODE", this.getCalItemCode());
		dataMap.put("CONSTANT", this.getConstant());
		
		return dataMap;
	}
}
