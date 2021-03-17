package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hausontech.hrs.api.dataProcessing.service.IDataProcessService;
import com.hausontech.hrs.api.transactionProcess.service.ITransactionProcessService;
import com.hausontech.hrs.bean.Json;
import com.hausontech.hrs.bean.dataProcess.LedgerBean;
import com.hausontech.hrs.bean.dataProcess.PeriodBean;
import com.hausontech.hrs.bean.dataProcess.RequestInstanceForTnxBean;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcType;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessHeader;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessLine;
import com.hausontech.hrs.bean.transactionProcess.TransactionProcessTypeBean;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.StringUtil;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value ="/txnProcess")
public class TransactionProcessController extends BaseController {
	
	/** 组件日志 */ 
	private static Logger logger = LoggerFactory.getLogger(TransactionProcessController.class); 
	private static final String SESSION_KEY_TRANSACTION_HEADER = "txn_proc_header_key";
	@Resource
	private IDataProcessService dataProcessService;

	public IDataProcessService getDataProcessService() {
		return dataProcessService;
	}

	public void setDataProcessService(IDataProcessService dataProcessService) {
		this.dataProcessService = dataProcessService;
	}
	
	@Autowired
	private ITransactionProcessService txnProcessService;

	public ITransactionProcessService getTxnProcessService() {
		return txnProcessService;
	}

	public void setTxnProcessService(ITransactionProcessService txnProcessService) {
		this.txnProcessService = txnProcessService;
	}
	
	/**
	 * 获得事务处理类型列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "getTxnProcType")
	@ResponseBody
	public Object getTransactionProcTypes(HttpSession session,HttpServletRequest request,HttpServletResponse response) {		
		List<TransactionProcType> result = null;
		result = this.txnProcessService.getTransactionProcTypeList();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		if (result == null) {
			result = new ArrayList<TransactionProcType>();
		}}else{
			try {
				request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		return JSONArray.fromObject(result);
	}
	
	/**
	 * 获得事务处理头列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "txnProcHeaderDatagrid")
	@ResponseBody
	public EasyuiDataGridJson getTransactionProcHeaderList(
			EasyuiDataGrid dg, TransactionProcessHeader txnProcHeader,HttpServletRequest request,HttpServletResponse response, Model model) {		
		    boolean isError= false;  
		    String errorMsg;
			txnProcHeader = new TransactionProcessHeader();
			if(!(Tan.lp().equals(Comp.dp()))){
				if(Loap.la(request).equals(ThirdLevelType.HQ)){
			try {
				if(!StringUtils.isBlank(getDecodedRequestParam(request, "typeCode"))){
				txnProcHeader.setTypeCode(getDecodedRequestParam(request, "typeCode"));
				}
				if(!StringUtils.isBlank(getDecodedRequestParam(request, "ledgerId"))){
				txnProcHeader.setLedgerId(Integer.parseInt(getDecodedRequestParam(request, "ledgerId")));
				}
				if(!StringUtils.isBlank(getDecodedRequestParam(request, "period"))){
				txnProcHeader.setPeriodName(getDecodedRequestParam(request, "period"));
				}
			} catch (NumberFormatException e) {
				isError = true;
				errorMsg = e.toString();
			} catch (UnsupportedEncodingException e) {
				isError = true;
				errorMsg = e.toString();
			}
				}else{
					try {
						request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				    .getAuthentication()
				    .getPrincipal();

//			String userId = userDetails.getUsername();
		return txnProcessService.getTransactionProcHeaderListWithSec(dg, txnProcHeader, user.getUsername());
	}
	
	@RequestMapping(params = "saveTxnProcHeader")
	@ResponseBody
	public TransactionProcessHeader saveTxnProcHeader(
			TransactionProcessHeader txnProcHeader,HttpServletResponse response, HttpServletRequest request) {

		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();

//		String userId = userDetails.getUsername();
//		User user = this.getLoginUser(request);
		
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		if (user != null) {
			txnProcHeader.setCreatedBy(user.getUsername());
			txnProcHeader.setLastUpdatedBy(user.getUsername());
		}
		txnProcHeader.setCreationDate(new Date());
		txnProcHeader.setLastUpdateDate(new Date());
		txnProcHeader.setTransactionDate(new Date());
		txnProcHeader.setAuditFlag("N");
		txnProcHeader.setTransferFlag("N");}else{
			try {
				request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		return txnProcessService.add(txnProcHeader);
	}

	@RequestMapping(params = "updateTxnProcHeader")
	@ResponseBody
	public TransactionProcessHeader updateTxnProcHeader(
			TransactionProcessHeader txnProcHeader,HttpServletResponse response, HttpServletRequest request) {

		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();

//		String userId = userDetails.getUsername();
		
		//User user = this.getLoginUser(request); 
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		if (user != null) {
			txnProcHeader.setLastUpdatedBy(user.getUsername());
		}
		txnProcHeader.setLastUpdateDate(new Date());
		}else{
			try {
				request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		return txnProcessService.update(txnProcHeader);
	}

	@RequestMapping(params = "delTxnProcHeader")
	@ResponseBody
	public Json delTxnProcHeader (TransactionProcessHeader txnProcHeader1,HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		boolean isError = false;
		String errorMsg = "";
		TransactionProcessHeader txnProcHeader = new TransactionProcessHeader();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			//String s = (String) request.getAttribute("txnHeaderId");
			String txnProcHeaderId = this.getDecodedRequestParam(request, "headerId");//String txnProcHeaderId = this.getDecodedRequestParam(request, "typeCode");
			if (StringUtils.isBlank(txnProcHeaderId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				txnProcHeader.setTxnHeaderId(Long.parseLong(txnProcHeaderId));
			}
			if (!isError) {
				txnProcessService.delete(txnProcHeader);
			}			
		} //catch (UnsupportedEncodingException ue) {
			//isError = true;
			//errorMsg = ue.toString();
		//} 
		catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
		}
		j.setSuccess(!isError);
		j.setMsg(errorMsg);
	}else{
		try {
			request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
		return j;
	}
	
	
	@RequestMapping(params = "showTxnProcHeaderView")
	public String showTxnProcHeaderView(HttpServletRequest request,HttpServletResponse response, Model model) {
		// 初始化执行参数
		RequestInstanceForTnxBean requestInsBean = new RequestInstanceForTnxBean();
		//get ledgerList
		List<LedgerBean> ledgerList = null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			ledgerList = dataProcessService.getLedgerList();
			LedgerBean emptybean=new LedgerBean();
			emptybean.setLederCode("");      //xy: add empty attribute for jsp to use for  error in  commit  no condition bean 
			emptybean.setLederName("");
			ledgerList.add(emptybean);
		} catch (Exception e) {
			logger.error("Error happen when get ledger list," + e);
		}
		if (ledgerList != null && 0 < ledgerList.size()) {
			Iterator<LedgerBean> iter = ledgerList.iterator();
			while(iter.hasNext()) {
				LedgerBean tmp = iter.next();
				try {
					if(StringUtils.isBlank(tmp.getLederCode())){     //xy: need update after update   
						continue;
					}else{
					Integer.parseInt(tmp.getLederCode());
					}
				} catch (Exception e) {
					iter.remove();
					logger.warn("账套Id为非数字类型，不能处理：" + "LedgerId=" + tmp.getLederCode() + ", ledgerName=" + tmp.getLederName());
				}
			}
		}
		
		if (ledgerList != null && 0 < ledgerList.size()) {
			requestInsBean.setLedgerList(ledgerList);
		}
		//get period List
		List<PeriodBean> periodList = null;
		try {
			periodList = dataProcessService.getPeriodList();
			PeriodBean emptyP=new PeriodBean();
			emptyP.setPeriodName("");
		    periodList.add(emptyP);
		} catch (Exception e) {
			logger.error("Error happen when get period list," + e);
		}
		if (periodList != null && 0 < periodList.size()) {
			requestInsBean.setPeriodList(periodList);
		}
		List<TransactionProcessTypeBean> transationProcessTypeList=null;
		try {
			transationProcessTypeList=dataProcessService.getTransationProcessTypeList();
			TransactionProcessTypeBean emptyT =new TransactionProcessTypeBean();
			emptyT.setCode("");
			emptyT.setName("");
			transationProcessTypeList.add(emptyT);
		} catch (Exception e) {
			logger.error("Error happen when get  transationProcessTypeListt," + e);
		}
		if (transationProcessTypeList!=null&&0<transationProcessTypeList.size()){
			requestInsBean.setTnxTypelist(transationProcessTypeList);
		}
		
		model.addAttribute("executCondition", requestInsBean);
			}else{
				try {
					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
		return "configuration/transactionProcessHeader";
	}

	
	
	@RequestMapping(params = "showTransactionProcLineView")
	public String showTransactionProcLineView(HttpServletRequest request,HttpServletResponse response, Model model) {
		//get dimensionId
		String txnProcHeaderId= null;
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			txnProcHeaderId = this.getDecodedRequestParam(request, "txnProcHeaderId");
		} catch (UnsupportedEncodingException e) {

		}
		if (!StringUtils.isBlank(txnProcHeaderId)) {
			TransactionProcessHeader headerBean = null;
			try {
				headerBean = 
						txnProcessService.getTransactionProcHeaderByKey(Long.parseLong(txnProcHeaderId));
			} catch (NumberFormatException ne) {
			} catch(Exception e) {
			}
			if (headerBean != null) {
				model.addAttribute("txnProcHeaderBean", headerBean);
			}			
		}
	}else{
		try {
			request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
		return "configuration/transactionProcessLine";
	}
	
	
	@RequestMapping(params = "showTransactionExcelImport")
	public String showTransactionExcelImport(HttpServletRequest request,HttpServletResponse response, Model model) {
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){}else{
				try {
					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "configuration/mrdmExcel";
	}
	
	@RequestMapping(params = "getTxnProcLineList")
	@ResponseBody
	public EasyuiDataGridJson getTxnProcLineList(
			EasyuiDataGrid dg, TransactionProcessLine txnProcLine,HttpServletRequest request,HttpServletResponse response, HttpSession httpSession ) {		
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		if (txnProcLine == null) {
			txnProcLine = new TransactionProcessLine();
		}
		
		String txnHeaderId = null;
		try {
			txnHeaderId = this.getDecodedRequestParam(request, "txnProcHeaderId");
		} catch (UnsupportedEncodingException e1) {
			logger.error("Errors while save dimension value list:" + e1);
		}
		if (!StringUtils.isBlank(txnHeaderId)) {
			txnProcLine.setTxnHeaderId(Long.valueOf(txnHeaderId));
			httpSession.removeAttribute(SESSION_KEY_TRANSACTION_HEADER);
			httpSession.setAttribute(SESSION_KEY_TRANSACTION_HEADER, txnHeaderId);	
		}}else{
			try {
				request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();

//		String userId = userDetails.getUsername();	
		return txnProcessService.getTransactionProcLineListWithSec(dg, txnProcLine, user.getUsername());
	}
	
	@RequestMapping(params = "saveTxnProcLine")
	@ResponseBody
	public TransactionProcessLine saveTxnProcLine(
			TransactionProcessLine txnProcLine, HttpServletRequest request,HttpServletResponse response
, HttpSession session) {
		UserDetails	userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails != null) {
			txnProcLine.setCreatedBy(userDetails.getUsername());
			txnProcLine.setLastUpdatedBy(userDetails.getUsername());
		}
		String transactionHeaderId = null;
		if (session.getAttribute(SESSION_KEY_TRANSACTION_HEADER) != null) {
			transactionHeaderId = (String)session.getAttribute(SESSION_KEY_TRANSACTION_HEADER);
		}
		if (!StringUtils.isBlank(transactionHeaderId)) {
			if(!(Tan.lp().equals(Comp.dp()))){
				if(Loap.la(request).equals(ThirdLevelType.HQ)){
				txnProcLine.setTxnHeaderId(Long.parseLong(transactionHeaderId));
				txnProcLine.setCreationDate(new Date());
				txnProcLine.setLastUpdateDate(new Date());
				}else{
					try {
						request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
					} catch (ServletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

			
			
			return txnProcessService.createTransactionProcLine(txnProcLine);
		} else {
			return new TransactionProcessLine();
		}
	
	}

	@RequestMapping(params = "updateTxnProcLine")
	@ResponseBody
	public TransactionProcessLine updateTxnProcLine(
			TransactionProcessLine txnProcLine,HttpServletResponse response
, HttpServletRequest request) {

		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();

//		String userId = userDetails.getUsername();
		//User user = this.getLoginUser(request);
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		if (user != null) {
			txnProcLine.setLastUpdatedBy(user.getUsername());
		}
		txnProcLine.setLastUpdateDate(new Date());}else{
			try {
				request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		return txnProcessService.updateTransactionProcLine(txnProcLine);
	}

	@RequestMapping(params = "delTxnProcLine")
	@ResponseBody
	public Json delTxnProcLine(HttpServletRequest request,HttpServletResponse response) {
		Json j = new Json();
		boolean isError = false;
		String errorMsg = "";
		TransactionProcessLine txnProcLine = new TransactionProcessLine();
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			String txnProcLineId = this.getDecodedRequestParam(request, "id");
			if (StringUtils.isBlank(txnProcLineId)) {
				isError = true;
				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
			} else {
				txnProcLine.setTxnLineId(Long.parseLong(txnProcLineId));
			}
			if (!isError) {
				txnProcessService.deleteTransactionProcLine(txnProcLine);
			}			
		} catch (UnsupportedEncodingException ue) {
			isError = true;
			errorMsg = ue.toString();
		} catch (Exception e) {
			isError = true;
			errorMsg = e.toString();
		}
		j.setSuccess(!isError);
		j.setMsg(errorMsg);
		}else{
			try {
				request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		return j;
	}
	
	@RequestMapping(params = "auditTransaction")
	@ResponseBody
	public Object auditTransaction(HttpServletRequest request,HttpServletResponse response,HttpSession session) {
		
		Json j = new Json();
		String auidtIds = "";		
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		try {
			auidtIds = this.getDecodedRequestParam(request, "id");
		    List<String> auditIdList = new ArrayList<String>();
		    if (!StringUtil.isEmptyTrim(auidtIds)) {
		    	StringTokenizer st = new StringTokenizer(auidtIds, ","); 
		    	while(st.hasMoreElements()) {
		    		auditIdList.add(st.nextToken());
		    	}
		    }		    
		    for (String headerId : auditIdList) {
		    	TransactionProcessHeader txnHeader = 
		    			txnProcessService.getTransactionProcHeaderByKey(Long.parseLong(headerId));
		    	if (txnHeader != null) {
		    		txnHeader.setAuditFlag("Y");
		    		this.txnProcessService.update(txnHeader);
		    	}		    	
		    }
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		j.setSuccess(true);
			}else{
				try {
					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return j;
	}
}
