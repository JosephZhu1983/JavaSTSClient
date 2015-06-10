package com.kongzhonghd;

import com.kongzhonghd.util.LangUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.management.ManagementFactory;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/22/13
 * Time: 2:51 PM
 * STS的一些常量
 */
public class STSConst
{
    public static final String CUSTOM_PROTOCOL_NAME = ".Kongzhong";
    public static final byte CR = 0x0D;
    public static final byte LF = 0x0A;
    public static final String CRLF = "\r\n";
    public static final String DEFAULT_ENCODING_CHARSET = "utf-8";
    public static final String DEFAULT_CONTEXT_TYPE = "application/xml";
    public static final int MAX_MESSAGE_SIZE = 600;
    private static final Log log = LogFactory.getLog(STSConst.class);

    public static String ADDRESS = "127.0.0.1";
    public static String PROCESS_ID;


    static
    {
        try
        {
            ADDRESS = "172.19.20.5";
        }
        catch (Exception e)
        {
            if (log.isWarnEnabled())
                log.warn("无法获取本机IP，错误原因：" + LangUtils.getExceptionContent(e));
        }

        try
        {
            String name = ManagementFactory.getRuntimeMXBean().getName();
            PROCESS_ID = name.split("@")[0];
        }
        catch (Exception e)
        {
            if (log.isWarnEnabled())
                log.warn("无法获取进程ID，错误原因：" + LangUtils.getExceptionContent(e));
        }
    }
    
    public static String getLocalIP() throws SocketException {
        String ip = "172.19.20.6";
            Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                if (!ni.getName().equals("eth0")) {
                    continue;
                } else {
                    Enumeration<?> e2 = ni.getInetAddresses();
                    while (e2.hasMoreElements()) {
                        InetAddress ia = (InetAddress) e2.nextElement();
                        if (ia instanceof Inet6Address)
                            continue;
                        ip = ia.getHostAddress();
                    }
                    break;
                }
            }
        return ip;
    }
}
