package com.hausontech.hrs.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class Loap {

    public static String gp(HttpServletRequest request){  
        if (request == null)  
            return null;  
        String i = request.getHeader("X-Forwarded-For");  
        if (i == null || i.length() == 0 || "unknown".equalsIgnoreCase(i))  
            i = request.getHeader("Proxy-Client-IP");  
        if (i == null || i.length() == 0 || "unknown".equalsIgnoreCase(i))  
            i = request.getHeader("WL-Proxy-Client-IP");  
        if (i == null || i.length() == 0 || "unknown".equalsIgnoreCase(i))  
            i = request.getHeader("HTTP_CLIENT_IP");  
        if (i == null || i.length() == 0 || "unknown".equalsIgnoreCase(i))  
            i = request.getHeader("HTTP_X_FORWARDED_FOR");  
        if (i == null || i.length() == 0 || "unknown".equalsIgnoreCase(i))  
            i = request.getRemoteAddr();  
        if ("127.0.0.1".equals(i) || "0:0:0:0:0:0:0:1".equals(i))  
            try {  
                i = InetAddress.getLocalHost().getHostAddress();  
            }  
            catch (UnknownHostException unknownhostexception) {  
            }  
        return i;  
    }  
    
    
    public static String  gsp(){  
    	String sp = null;
        try {  
            Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();  
            InetAddress p = null;  
            while (netInterfaces.hasMoreElements()) {  
                NetworkInterface ni = (NetworkInterface) netInterfaces  
                        .nextElement();  
                p = (InetAddress) ni.getInetAddresses().nextElement();  
                sp = p.getHostAddress();  
                if (!p.isSiteLocalAddress() && !p.isLoopbackAddress()  
                        && p.getHostAddress().indexOf(":") == -1) {  
                    sp = p.getHostAddress();  
                    break;  
                } else {  
                    p = null;  
                }  
            }  
        } catch (SocketException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
         
         return sp;  
       } 
    
    public static String gl(){     
        InetAddress in = null;     
                    try {  
                        in = InetAddress.getLocalHost();  
                    } catch (UnknownHostException e) {  
                        e.printStackTrace();  
                    }     
                  
                byte[] aa = in.getAddress();     
                String as = "";     
                for (int i = 0; i < aa.length; i++) {     
                    if (i > 0) {     
                        as += ".";     
                    }     
                    as += aa[i] & 0xFF;     
                }     
                        return as;     
        }   
    
    public static final String la(HttpServletRequest request){
    	String ss = gl(); 
    	String c = "qwert";
    	String[] sss=ss.replace(".", ",").split(",");
    	for (int i = 0; i < sss.length; i++) {
			c = c+sss[i];
		}
		return c;
    	
    } 
}
