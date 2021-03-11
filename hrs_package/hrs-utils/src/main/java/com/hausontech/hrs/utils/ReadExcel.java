package com.hausontech.hrs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	public static List<Map<String, Object>> readFromXLS200x(InputStream fis, int RowNum, int CellNum) {

		String cellStr = null;// 单元格，�?终按字符串处�?
		List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();// 返回封装数据的List

		try {
			Workbook wb = null;
			 if (!fis.markSupported()) {
		            fis = new PushbackInputStream(fis, 8);
		        }
			if (POIFSFileSystem.hasPOIFSHeader(fis)) {
				wb = new HSSFWorkbook(fis);
			}else{//不这样会报错 zhaott 2014-09-28 13:08
				if (POIXMLDocument.hasOOXMLHeader(fis)) {
					wb = new XSSFWorkbook(OPCPackage.open(fis));
				}
			}
			Sheet sheet = wb.getSheetAt(0);// 取出第一个工作表，索引是0
			
			// �?始循环遍历行，表头不处理，从1�?�?
			for (int i = RowNum; i <= sheet.getLastRowNum(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();// 实例化Student对象
				Row row = sheet.getRow(i);// 获取行对�?
				if (row == null) {// 如果为空，不处理
					continue;
				}
				// 循环遍历单元�?
				for (int j = CellNum; j < row.getLastCellNum(); j++) {
					Cell cell = row.getCell(j);// 获取单元格对�?
					if (cell == null) {// 单元格为空设置cellStr为空�?  
                        cellStr = "";  
                    } else {// 其余按照字符串处�?  
                    	if(Cell.CELL_TYPE_NUMERIC == cell.getCellType()){
                        	if(DateUtil.isCellDateFormatted(cell)){
                        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
                        		cellStr = sdf.format(cell.getDateCellValue());
                        	}else{
                        		cell.setCellType(Cell.CELL_TYPE_STRING);
                        		cellStr = cell.getStringCellValue();  
                        	}
                    	}else{
                    		cell.setCellType(Cell.CELL_TYPE_STRING);
                    		cellStr = cell.getStringCellValue();  
                    	}
                    } 
					// 下面按照数据出现位置封装到Map�?					
					map.put(j+"", cellStr);					
				}
				dateList.add(map);// 数据装入List
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dateList;

	}
	//fis:文件�?  RowNum:�?始读取行�? CellNum：开始读取列�?
	public static List<Map<String, Object>> readFromXLS2003(InputStream fis, int RowNum, int CellNum) {
		
		String cellStr = null;// 单元格，�?终按字符串处�?
		List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();// 返回封装数据的List

		try {
			HSSFWorkbook workbook2003 = new HSSFWorkbook(fis);// 创建Excel2003文件对象
			HSSFSheet sheet = workbook2003.getSheetAt(0);// 取出第一个工作表，索引是0
			
			// �?始循环遍历行，表头不处理，从1�?�?
			for (int i = RowNum; i <= sheet.getLastRowNum(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();// 实例化Student对象
				HSSFRow row = sheet.getRow(i);// 获取行对�?
				if (row == null) {// 如果为空，不处理
					continue;
				}
				// 循环遍历单元�?
				for (int j = CellNum; j < row.getLastCellNum(); j++) {
					HSSFCell cell = row.getCell(j);// 获取单元格对�?
					if (cell == null) {// 单元格为空设置cellStr为空�?  
                        cellStr = "";  
                    } else {// 其余按照字符串处�?  
                    	cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cellStr = cell.getStringCellValue();  
                    } 
					// 下面按照数据出现位置封装到Map�?					
					map.put(j+"", cellStr);					
				}
				dateList.add(map);// 数据装入List
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dateList;

	}
	
	public static List<Map<String, Object>> readFromXLS2007(InputStream fis, int RowNum, int CellNum) {

		String cellStr = null;// 单元格，�?终按字符串处�?
		List<Map<String, Object>> dateList = new ArrayList<Map<String, Object>>();// 返回封装数据的List

		try {
			XSSFWorkbook workbook2003 = new XSSFWorkbook(fis);// 创建Excel2003文件对象
			XSSFSheet sheet = workbook2003.getSheetAt(0);// 取出第一个工作表，索引是0
			
			// �?始循环遍历行，表头不处理，从1�?�?
			for (int i = RowNum; i <= sheet.getLastRowNum(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();// 实例化Student对象
				XSSFRow row = sheet.getRow(i);// 获取行对�?
				if (row == null) {// 如果为空，不处理
					continue;
				}
				// 循环遍历单元�?
				for (int j = CellNum; j < row.getLastCellNum(); j++) {
					XSSFCell cell = row.getCell(j);// 获取单元格对�?
					if (cell == null) {// 单元格为空设置cellStr为空�?  
                        cellStr = "";  
                    } else {// 其余按照字符串处�?  
//                    	if(cell.getCellStyle().getDataFormat() == 14){//如果是日期格�?
//                    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    		cellStr = sdf.format(cell.getDateCellValue()); 
//                    	}else{
                    		cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                            cellStr = cell.getStringCellValue();  
//                    	}
                    	
                    } 
					// 下面按照数据出现位置封装到Map�?					
					map.put(j+"", cellStr);					
				}
				dateList.add(map);// 数据装入List
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return dateList;

	}

}
