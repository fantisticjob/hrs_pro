package com.hausontech.hrs.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Tan {

	private static Object resizeArray (Object oldArray, int newSize) {
		   int oldSize = java.lang.reflect.Array.getLength(oldArray);
		   Class elementType = oldArray.getClass().getComponentType();
		   Object newArray = java.lang.reflect.Array.newInstance(
		         elementType,newSize);
		   int preserveLength = Math.min(oldSize,newSize);
		   if (preserveLength > 0)
		      System.arraycopy (oldArray,0,newArray,0,preserveLength);
		   return newArray; 
		}
		 

	  public static String compress(String str) throws IOException {
	    if (str == null || str.length() == 0) {
	      return str;
	    }
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    GZIPOutputStream gzip = new GZIPOutputStream(out);
	    gzip.write(str.getBytes());
	    gzip.close();
	    return out.toString("ISO-8859-1");
	  }
	 

	  public static String uncompress(String str) throws IOException {
	    if (str == null || str.length() == 0) {
	      return str;
	    }
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1&quot"));
	    GZIPInputStream gunzip = new GZIPInputStream(in);
	    byte[] buffer = new byte[256];
	    int n;
	    while ((n = gunzip.read(buffer))!= 0) {
	      out.write(buffer, 0, n);
	    }
	    
	    return out.toString();
	  }
		
	  public static String lp(){
		  ThirdLevelType t = new ThirdLevelType();
		  Long l = t.doHuangKu();
		  if(l.toString().isEmpty()){
			  return Tan.decompressionString(tempString);
		  }
		  return l.toString();
		  
	  }

	  public static String tempString = "acdesfghsadajfkqsfab";
	
	
	  public static void main(String[] args) {
	    String resultString = compactString(tempString);
	    
	    String convertString = decompressionString(resultString);
	  }
	
	  public static String decompressionString(String tempString){
	    char[] tempBytes = tempString.toCharArray();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < tempBytes.length; i++) {
	     char c = tempBytes[i];
	     char firstCharacter = (char) (c >>> 8);
	     char secondCharacter = (char) ((byte)c);
	     sb.append(firstCharacter);
	     if(secondCharacter != 0)
	      sb.append(secondCharacter);
	    }
	    return sb.toString();
	  }
	
	
	 
	  public static String compactString(String tempString) {
	    StringBuffer sb = new StringBuffer();
	    byte[] tempBytes = tempString.getBytes();
	    for (int i = 0; i < tempBytes.length; i+=2) {
	     byte[] firstCharacter = tempBytes;
	     char secondCharacter = 0;
	     if(i+1<tempBytes.length)
	      secondCharacter = (char)tempBytes[i+1];
	     
	     sb.append((char)(secondCharacter));
	    }
	    return sb.toString();
	  }
	 
}
