package com.hausontech.hrs.api.transactionProcess.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.hausontech.hrs.api.IBaseService;

public interface IFileUploadService extends IBaseService{
	
	/**
	 * 普�?�上�?
	 * @param files
	 * @param request
	 * @return
	 */
	public String filesUpload(MultipartFile[] files, HttpServletRequest request);
	
	/**
	 * 上传返回路径
	 * @param request
	 * @param files
	 * @return
	 */
	public  List readExcel(HttpServletRequest request, MultipartFile[] files,  int RowNum, int CellNum);

}
