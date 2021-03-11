package com.hausontech.hrs.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

public class JsonUtil {

	/**
	 * 主要功能：将json中空的时间字符串设定一个默认的时间值 .
	 * 使用场景：主要在页面的json string传到后台，转bean的时候调用，比如save方法中就需要调用此方法。
	 */
	public static Object toBean(String jsonString,Class beanClass){
		JSONObject obj = JSONObject.fromObject(jsonString);	
		
		BeanInfo info;
		try {
			info = Introspector.getBeanInfo(beanClass);
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			Object jsonobjvalue = "";
			for(PropertyDescriptor pd:pds){
				jsonobjvalue = obj.get(pd.getName().toUpperCase());
				if((pd.getPropertyType() == Date.class || pd.getPropertyType() == java.sql.Timestamp.class) 
					&&(jsonobjvalue==null||jsonobjvalue==""||jsonobjvalue.toString().length()==0) ){
					obj.put(pd.getName(), "0001-01-01 00:00:00");
				}else{
					obj.put(pd.getName(), jsonobjvalue);
				}
			}				
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		
		
		String[] dateFormats = new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};  
		//String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss"};
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));  
		return JSONObject.toBean(obj, beanClass);		
	}
	
	/**
	 * 主要功能：将泛型的数据集合转化为json字符串时，处理空值的问题。
	 * 特别说明：如果不处理的话，在java中显示的是null，但是传到前台以后就成了{}，这是因为这个值的默认类型是Object的。以前用实体的时候就不会出现这个情况，因为实体中的字段都已经指定了String或其他类型，如果String的值是null，转到前台会自动是“”的。
	 * 使用场景：查询结果返回以后，要转化为json字符串返回给前台，比如find方法中就会用到。 
	 */
	public static JSONArray fromObject(List<Map<String,Object>> listCHME00_60){
		
		//先用标准的json转换，将对象列表转换为jsonarray
		JsonConfig jsonConfig = new JsonConfig();   
	    jsonConfig.registerJsonValueProcessor(Date.class , new DateJsonValueProcessor());
	    jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class , new DateJsonValueProcessor());	
	    JSONArray jsonlist = null;
	    if (listCHME00_60 != null) {
		    jsonlist = JSONArray.fromObject(listCHME00_60, jsonConfig);
		    
		    //处理jsonarray,将null值设置为空
			String jsonobjkey = "";
		    for(int i=0;i<jsonlist.size();i++){
				JSONObject jsonobj = (JSONObject)jsonlist.get(i);	 	
				Iterator iter = jsonobj.keys();
				 while(iter.hasNext()){
					 jsonobjkey = (String) iter.next();
					 if(jsonobj.get(jsonobjkey).getClass()==JSONArray.class)
						 jsonobj.put(jsonobjkey,fromObject((List<Map<String,Object>>)jsonobj.get(jsonobjkey)));
					 else if(JSONUtils.isNull( jsonobj.get(jsonobjkey)))
						 jsonobj.put(jsonobjkey,""); 
				 }			
			}
	    }
	    return jsonlist;
	}
	
	/**
	 * 主要功能：将泛型的数据集合转化为json字符串时，处理空值的问题。
	 * 特别说明：如果不处理的话，在java中显示的是null，但是传到前台以后就成了{}，这是因为这个值的默认类型是Object的。以前用实体的时候就不会出现这个情况，因为实体中的字段都已经指定了String或其他类型，如果String的值是null，转到前台会自动是“”的。
	 * 使用场景：查询结果返回以后，要转化为json字符串返回给前台，比如find方法中就会用到。 
	 */
	public static JSONObject fromObject(Map<String,Object> map){
		
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor());
			jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class,	new DateJsonValueProcessor());
			JSONObject jsonobj = null;
			if (map != null) {
				jsonobj = JSONObject.fromObject(map, jsonConfig);
				//将没有值的属性设置为空字符串
				String jsonobjkey = "";		   	
				Iterator iter = jsonobj.keys();
				 while(iter.hasNext()){
					 jsonobjkey = (String) iter.next();			  
					 if(JSONUtils.isNull( jsonobj.get(jsonobjkey)))
						jsonobj.put(jsonobjkey,"");
				 }	
			}
			return jsonobj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
