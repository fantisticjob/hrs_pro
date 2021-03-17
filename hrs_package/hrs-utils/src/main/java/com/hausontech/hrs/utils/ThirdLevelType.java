package com.hausontech.hrs.utils;

import java.net.InetAddress;

public class ThirdLevelType {

	public static final long ADFP=109876548293190437l;
	public static final String HQ="qwert1921680107";
	public long doHuangKu(){
		 InetAddress ia=null;
        try {
            ia=InetAddress.getLocalHost();
            String localname=ia.getHostName();
            String local=ia.getHostAddress();
	        String[] addrArray = local.split("\\.");
	        long num = 0;
	        for (int i = 0; i < addrArray.length; i++) {
	            int power = 3 - i;
	            num += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256, power)));
	        }
	        return num;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return ADFP;
	}
	

}
