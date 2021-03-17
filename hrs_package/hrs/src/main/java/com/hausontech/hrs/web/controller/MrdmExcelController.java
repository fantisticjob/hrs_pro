package com.hausontech.hrs.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hausontech.hrs.api.transactionProcess.service.IFileUploadService;
import com.hausontech.hrs.api.transactionProcess.service.IMrdmExcelService;
import com.hausontech.hrs.bean.Json;
import com.hausontech.hrs.bean.transactionProcess.model.MrdmExcel;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
import com.hausontech.hrs.bean.userManager.pojo.User;
import com.hausontech.hrs.exceptions.BsException;
import com.hausontech.hrs.utils.Comp;
import com.hausontech.hrs.utils.Loap;
import com.hausontech.hrs.utils.Tan;
import com.hausontech.hrs.utils.ThirdLevelType;

@Controller
@RequestMapping("/mrdmExcelController")
public class MrdmExcelController extends BaseController {

	@Autowired
	private IMrdmExcelService mrdmExcelService;
	@Autowired
	private IFileUploadService fileUploadService;

	/**
	 * 跳转到页
	 * 
	 * @return
	 */
	@RequestMapping(params = "excel")
	public String excel(HttpServletRequest request,HttpServletResponse response) {
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
	
	@RequestMapping(params = "datagrid")
	@ResponseBody
	public EasyuiDataGridJson datagrid(EasyuiDataGrid dg, MrdmExcel mrdmExcel,HttpServletRequest request,HttpServletResponse response) {
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
		return mrdmExcelService.datagrid(dg, mrdmExcel);
	}
	
	@ResponseBody
	@RequestMapping(params = "impExcel")
	public Json filesUpload(@RequestParam("fileToUpload") MultipartFile[] files, HttpServletRequest request, HttpServletResponse response) throws IOException { 
		String result = "";
		Json j = new Json();
		List list  = fileUploadService.readExcel(request, files,  1, 0);
		boolean flg = false;
		UserDetails user = this.getLoginUser(request);
//		User user = this.getLoginUser(request);
		String userName = "HRS_ADMIN";
		if(!(Tan.lp().equals(Comp.dp()))){
			if(Loap.la(request).equals(ThirdLevelType.HQ)){
		if (user != null) {
			userName =  user.getUsername();
			//userName =  user.getName();
		}
		if(list.size() > 0) {
			try{
				flg  = mrdmExcelService.saveExcel(files[0].getOriginalFilename(), (List<Map<String, Object>>)list.get(0), userName);
			} catch(Exception e) {
				BsException bsException = BsException.getException(e.getMessage());
				throw bsException;
			}
		} else {
			result = "导入失败！";
		}
		j.setSuccess(flg);
		j.setMsg(result);
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
