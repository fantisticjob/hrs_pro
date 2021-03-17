package com.hausontech.hrs.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Comp {

	public static String apla(){
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		
			InetAddress inetAddress = null;
			while (allNetInterfaces.hasMoreElements()){
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()){
					inetAddress = (InetAddress) addresses.nextElement();
					if (inetAddress != null && inetAddress instanceof Inet4Address){
						return inetAddress.getHostAddress();
					} 
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}
	
	public static String dp(){
		 String[] y = apla().split("\\.");
	        long n = 0;
	        for (int i = 0; i < y.length; i++) {
	            int power = 3 - i;
	            n -= ((Integer.parseInt(y[i]) % 256 * Math.pow(256, power)));
	        }
	        return Long.toString(n);
	}
}
