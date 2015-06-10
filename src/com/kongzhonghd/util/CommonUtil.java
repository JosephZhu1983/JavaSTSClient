package com.kongzhonghd.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stefanie zhao
 * @date 2013-11-19 上午10:30:47
 */
public class CommonUtil{

    public static String getIp()
    {
        try
        {
            InetAddress address = InetAddress.getLocalHost();
            if (null != address) return address.getHostAddress();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return "127.0.0.1";
    }
    
    public static Date parseDate(String date) throws ParseException{
    	 DateFormat m_ISO8601Local = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	 return m_ISO8601Local.parse(date.replace('T', ' ').replace('Z', ' '));
    }

    public static void main(String[] args)
    {
        System.out.println(getIp());
    }
}
