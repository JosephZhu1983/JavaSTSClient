package com.kongzhonghd.sts.common;

import com.kongzhonghd.sts.exception.STSException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 7/23/13
 * Time: 9:21 AM
 * 抽象的STS起始行
 */
public abstract class AbstractSTSStartLine implements Serializable, Buildable, Parsable
{
    protected static final String PROTOCOL_VERSION = "STS/1.0";
    private static final Log log = LogFactory.getLog(AbstractSTSStartLine.class);

    public static AbstractSTSMessage getMessageFromStartLine(String line) throws STSException
    {
        AbstractSTSMessage message = null;

        if (line.startsWith(PROTOCOL_VERSION))
        {
            message = new STSResponseMessage();
        }
        else if (line.endsWith(PROTOCOL_VERSION))
        {
            message = new STSRequestMessage();
        }
        else
        {
            String error = "错误的起始行：" + line;
            log.warn(error);
            throw new STSException(error);
        }

        message.getStartLine().parseFromString(line);
        return message;
    }
}
