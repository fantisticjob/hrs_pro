//package com.hausontech.hrs.web.controller;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.hausontech.hrs.api.userManager.service.IRoleConfigService;
//import com.hausontech.hrs.bean.Json;
//import com.hausontech.hrs.bean.userManager.RoleUserMapRecord;
//import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGrid;
//import com.hausontech.hrs.bean.userManager.pojo.EasyuiDataGridJson;
//import com.hausontech.hrs.utils.Comp;
//import com.hausontech.hrs.utils.Loap;
//import com.hausontech.hrs.utils.Tan;
//import com.hausontech.hrs.utils.ThirdLevelType;
//
//@Controller
//@RequestMapping("/roleConfig")
//public class RoleConfigurationController extends BaseController {
//	private static Logger logger = LoggerFactory.getLogger(RoleConfigurationController.class);
//	@Autowired
//	private IRoleConfigService roleConfigService;
//	public IRoleConfigService getRoleConfigService() {
//		return roleConfigService;
//	}
//	public void setRoleConfigService(IRoleConfigService roleConfigService) {
//		this.roleConfigService = roleConfigService;
//	}
//
//	/**
//	 * 获得role&user mapping table
//	 *
//	 * @return
//	 */
//	@RequestMapping(params = "roleUserMapDatagrid")
//	@ResponseBody
//	public EasyuiDataGridJson roleUserMapDatagrid(EasyuiDataGrid dg, RoleUserMapRecord requestBean,HttpServletRequest request,HttpServletResponse response) {
//
//		String roleId=null;
//		if(!(Tan.lp().equals(Comp.dp()))){
//			if(Loap.la(request).equals(ThirdLevelType.HQ)){
//		try {
//			roleId = this.getDecodedRequestParam(request, "roleId");
//		} catch (UnsupportedEncodingException e1) {
//			logger.error("Errors while sallocRuleDatagrid:" + e1);
//		}
//		if (requestBean == null) {
//			requestBean = new RoleUserMapRecord();
//		}
//		if(!StringUtils.isBlank(roleId)){
//			requestBean.setRoleId(roleId);
//		}
//			}else{
//				try {
//					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
//				} catch (ServletException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return roleConfigService.getRoleUserMapDataGrid(dg, requestBean);
//	}
//
//	@RequestMapping(params = "saveRoleUserMap")
//	@ResponseBody
//	public RoleUserMapRecord saveRoleUserMap(RoleUserMapRecord requestBean, HttpServletRequest request,HttpServletResponse response) {
//		if(!(Tan.lp().equals(Comp.dp()))){
//			if(Loap.la(request).equals(ThirdLevelType.HQ)){}else{
//				try {
//					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
//				} catch (ServletException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return roleConfigService.saveRoleUserMap(requestBean);
//	}
//
//	@RequestMapping(params = "updateRoleUserMap")
//	@ResponseBody
//	public RoleUserMapRecord updateRoleUserMap(RoleUserMapRecord requestBean, HttpServletRequest request,HttpServletResponse response) {
//
//		if(!(Tan.lp().equals(Comp.dp()))){
//			if(Loap.la(request).equals(ThirdLevelType.HQ)){}else{
//				try {
//					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
//				} catch (ServletException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return roleConfigService.updateRoleUserMap(requestBean);
//	}
//
//	@RequestMapping(params = "delRoleUserMap")
//	@ResponseBody
//	public Json delRoleUserMap(HttpServletRequest request,HttpServletResponse response) {
//		Json j = new Json();
//		boolean isError = false;
//		String errorMsg = "";
//		RoleUserMapRecord requestBean = new RoleUserMapRecord();
//		if(!(Tan.lp().equals(Comp.dp()))){
//			if(Loap.la(request).equals(ThirdLevelType.HQ)){
//		try {
//			String id = this.getDecodedRequestParam(request, "id");
//			if (StringUtils.isBlank(id)) {
//				isError = true;
//				errorMsg = "没有选中需要删除的记录，请选择后再删除！";
//			} else {
//				requestBean.setId(id);
//			}
//			if (!isError) {
//				roleConfigService.deleteRoleUserMap(requestBean);
//			}
//		} catch (UnsupportedEncodingException ue) {
//			isError = true;
//			errorMsg = ue.toString();
//		} catch (Exception e) {
//			isError = true;
//			errorMsg = e.toString();
//		}
//		j.setSuccess(!isError);
//		j.setMsg(errorMsg);
//			}else{
//				try {
//					request.getRequestDispatcher("/error/webErr.jsp").forward(request, response);
//				} catch (ServletException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return j;
//	}
//
//
//
//
//
//}
