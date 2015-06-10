package com.kongzhonghd.sts.filter;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.common.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;

/**
 * User: apple
 * Date: 13-8-11
 * Time: PM2:47
 * 记录消息日志的过滤器
 */
public class LogMessageFilter extends IoFilterAdapter
{
    private static final Log log = LogFactory.getLog(LogMessageFilter.class);

    private void logMessage(IoSession from, String direction, AbstractSTSMessage message)
    {
        try
        {
            if (log.isInfoEnabled())
            {
                String type = "";
                if (message instanceof STSRequestMessage)
                    type = "request message";
                else if (message instanceof STSResponseMessage)
                    type = "response message";
                else
                    return;

                int id = message.getMessageId();
                StringBuilder sb = new StringBuilder();
                sb.append(STSConst.CRLF);
                sb.append(String.format("%s：%s ID:#%d# %s：", from, direction, id, type));
                sb.append(STSConst.CRLF);
                sb.append("======================================================================");
                sb.append(STSConst.CRLF);
                sb.append(message.buildToString());
                sb.append(STSConst.CRLF);
                sb.append("======================================================================");
                sb.append(STSConst.CRLF);
                log.info(sb.toString());
            }
        }
        catch (Exception ex)
        {
        }
    }

    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception
    {
        AbstractSTSMessage msg = (AbstractSTSMessage) message;
        if (msg != null)
            logMessage(session, "Received", msg);
        nextFilter.messageReceived(session, message);
    }

    public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception
    {
        AbstractSTSMessage msg = (AbstractSTSMessage) writeRequest.getMessage();
        if (msg != null)
            logMessage(session, "Sent", msg);
        nextFilter.messageSent(session, writeRequest);
    }
}
