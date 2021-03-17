package com.hausontech.hrs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessor implements JsonValueProcessor {

    private String format ="yyyy-MM-dd HH:mm:ss";   
       
    public Object processArrayValue(Object value, JsonConfig config) {   
        return process(value);   
    }   
  
    public Object processObjectValue(String key, Object value, JsonConfig config) {   
        return process(value);   
    }   
       
    private Object process(Object value){   
           
        if(value instanceof Date
        	|| value instanceof java.sql.Timestamp){   
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.PRC);   
            return sdf.format(value);   
        } 
        
        return value == null ? "" : value.toString();   
    }   
}
