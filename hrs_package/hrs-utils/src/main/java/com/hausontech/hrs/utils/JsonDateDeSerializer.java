package com.hausontech.hrs.utils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JsonDateDeSerializer  extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext arg1) throws IOException, JsonProcessingException {
        Date date = null;  
        try {  
            date = DateUtil.parse(jp.getText());  
        } catch (ParseException e) {  
            e.printStackTrace();  
        }  
        return date;  
	}

}
