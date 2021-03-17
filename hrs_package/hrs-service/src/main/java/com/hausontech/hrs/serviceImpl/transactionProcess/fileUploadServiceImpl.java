package com.hausontech.hrs.serviceImpl.transactionProcess;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hausontech.hrs.api.BaseServiceImpl;
import com.hausontech.hrs.api.transactionProcess.service.IFileUploadService;
import com.hausontech.hrs.utils.ReadExcel;

@Service("fileUploadService")
public class fileUploadServiceImpl extends BaseServiceImpl implements IFileUploadService {

	@Override
	public String filesUpload(MultipartFile[] files, HttpServletRequest request) {
		String result ="";
        if (files != null && files.length > 0) {  
            for (int i = 0; i < files.length; i++) {  
                MultipartFile file = files[i];  
                // 保存文件  
                boolean a = saveFile(request, file); 
                System.out.println(file.getSize());
                if(a){  
                    result = "文件: " + file.getOriginalFilename() + "  " + file.getSize()/1024 + "KB 上传成功";
                } else {
                	 result = "文件上传失败";
                }
            }  
        } else {
        	result = "请选择文件再上传";
        }
        return result;  
	}

	 /***  
     * 保存文件  
     *  
     * @param file  
     * @return  
     */  
    private boolean saveFile(HttpServletRequest request, MultipartFile file) {
        // 判断文件是否为空  
        if (!file.isEmpty()) {
            try {  
                // 保存的文件路径(如果用的是Tomcat服务器，文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\upload\\文件夹中  )  
                String filePath = request.getSession().getServletContext()  
                    .getRealPath("/") + "/upload/" + file.getOriginalFilename();  
            	//String filePath = "d://uploadfile//" + file.getOriginalFilename();
                File saveDir = new File(filePath);  
                if (!saveDir.getParentFile().exists())  
                    saveDir.getParentFile().mkdirs();  
                // 转存文件  
                file.transferTo(saveDir);  
                return true;
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return false;  
    }

	@Override
	public List readExcel(HttpServletRequest request, MultipartFile[] files, int RowNum, int CellNum) {
		// TODO Auto-generated method stub
		List list = new ArrayList();
		for (int i = 0; i < files.length; i++) {  
			 //准备返回值列表  
			List<Map<String, Object>> valueList=new ArrayList<Map<String, Object>>();  
	      String tempSavePath="tmp";//缓存文件目录的文件夹名称（struts用）  
//	        String filepathtemp="/mnt/b2b/tmp";//缓存文件目录  
	        String tmpFileName= System.currentTimeMillis()+"."+getExtensionName(files[i].getOriginalFilename());  
	        String ExtensionName=getExtensionName(files[i].getOriginalFilename());  
	      String filepathtemp= request.getSession().getServletContext().getRealPath("/") + "/tmp";//strut获取项目路径  
	        File filelist = new File(filepathtemp);  
	        if  (!filelist .exists()  && !filelist .isDirectory())        
	        {         
	            filelist .mkdir();      
	        }   
	        String filePath = filepathtemp+System.getProperty("file.separator")+tmpFileName;  
	        File tmpfile = new File(filePath);  
	        //拷贝文件到服务器缓存目录（在项目下）  
//	        copy(file,tmpfile);//stuts用的方法  
	        FileInputStream fis = null;
	        try {
				copy(files[i], filepathtemp,tmpFileName);
				fis=new FileInputStream(filePath);    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//spring mvc用的方法  
	  
	        //System.out.println("后缀名："+ExtensionName);  
	      
	        if(ExtensionName.equalsIgnoreCase("xls")){  
	            valueList=ReadExcel.readFromXLS2003(fis, RowNum, CellNum);
	        }else if(ExtensionName.equalsIgnoreCase("xlsx")) {  
	            valueList=ReadExcel.readFromXLS2007(fis, RowNum, CellNum);
	        }  
	        //删除缓存文件  
	        tmpfile.delete();  
	        list.add(valueList);  
		}
		
		return list;
		
		
		
	} 
	
    /** 
     * 文件操作 获取文件扩展名 
     *  
     * @Author: sunny 
     * @param filename 
     *            文件名称包含扩展名 
     * @return 
     */  
    public static String getExtensionName(String filename) {  
        if ((filename != null) && (filename.length() > 0)) {  
            int dot = filename.lastIndexOf('.');  
            if ((dot > -1) && (dot < (filename.length() - 1))) {  
                return filename.substring(dot + 1);  
            }  
        }  
        return filename;  
    }  
  
    /** -----------上传文件,工具方法--------- */  
    private static final int BUFFER_SIZE = 2 * 1024;  
  
    /** 
     *  
     * @param src 
     *            源文件 
     * @param dst 
     *            目标位置 
     */  
    private static void copy(File src, File dst) {  
        InputStream in = null;  
        OutputStream out = null;  
        try {  
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);  
            out = new BufferedOutputStream(new FileOutputStream(dst));  
            byte[] buffer = new byte[BUFFER_SIZE];  
            int len = 0;  
            while ((len = in.read(buffer)) > 0) {  
                out.write(buffer, 0, len);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (null != in) {  
                try {  
                    in.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            if (null != out) {  
                try {  
                    out.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
  
    /** 
     * 上传copy文件方法(for MultipartFile) 
     * @param savePath 在linux上要保存完整路径 
     * @param newname 新的文件名称， 采用系统时间做文件名防止中文报错的问题 
     * @throws Exception 
     */  
    public static void copy(MultipartFile file,String savePath,String newname) throws Exception {  
        try {  
            File targetFile = new File(savePath,newname);  
            if (!targetFile.exists()) {  
                //判断文件夹是否存在，不存在就创建  
                targetFile.mkdirs();  
            }  
  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  

}
