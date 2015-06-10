package com.kongzhonghd.sts.filter;

import com.kongzhonghd.STSConst;
import com.kongzhonghd.sts.common.*;
import com.kongzhonghd.sts.context.PartialMessageContext;
import com.kongzhonghd.util.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.DefaultWriteRequest;
import org.apache.mina.core.write.WriteRequest;

/**
 * User: apple
 * Date: 13-8-11
 * Time: AM11:04
 * 处理分段消息的过滤器
 */
public class PartialMessageFilter extends IoFilterAdapter
{
    private static final Log log = LogFactory.getLog(PartialMessageFilter.class);

    private void logCompleteMessage(IoSession from, String direction, AbstractSTSMessage message)
    {
        try
        {
            if (log.isInfoEnabled())
            {
                String type = "";
                if (message instanceof STSRequestMessage)
                    type = "（完整）请求消息";
                else if (message instanceof STSResponseMessage)
                    type = "（完整）响应消息";
                else
                    return;

                int id = message.getMessageId();
                StringBuilder sb = new StringBuilder();
                sb.append(STSConst.CRLF);
                sb.append(String.format("%s：%sID为#%d#的%s：", from, direction, id, type));
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

    @Override
    public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception
    {
        AbstractSTSMessage msg = (AbstractSTSMessage) message;

        int id = msg.getMessageId();
        AbstractSTSMessage completeMessage = null;
        if (id > 0 && msg.getHeader().getContentRange() != null && msg.getHeader().getContentRange().getTotalBytes() > 0)
        {
            PartialMessageContext partialMessageContext = PartialMessageContext.getContext(session);
            completeMessage = partialMessageContext.addFragmentedMessage(msg);
            if (completeMessage != null)
                logCompleteMessage(session, "收到", completeMessage);
        }
        else
        {
            completeMessage = msg;
        }

        if (completeMessage != null)
            nextFilter.messageReceived(session, completeMessage);
    }

    @Override
    public void filterWrite(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception
    {
        AbstractSTSMessage completeMessage = (AbstractSTSMessage) writeRequest.getMessage();
        int maxMessageSize = STSConst.MAX_MESSAGE_SIZE;
        int totalLength = completeMessage.getBody().getContent().getBytes(STSConst.DEFAULT_ENCODING_CHARSET).length;
        if (totalLength > maxMessageSize)
        {
            logCompleteMessage(session, "发出", completeMessage);

            int partialMessageCount = (int) Math.ceil((double) totalLength / maxMessageSize);
            int currentMessageIndex = 1;
            for (int i = 0; i < totalLength; i += maxMessageSize)
            {
                AbstractSTSMessage frag = (AbstractSTSMessage) ObjectUtils.deepClone(completeMessage);
                STSHeaderRange range = new STSHeaderRange(i, Math.min(i + maxMessageSize - 1, totalLength), totalLength + 1);
                frag.getHeader().setContentRange(range);
                String content = completeMessage.getBody().getContent().substring(i, Math.min(i + maxMessageSize, totalLength));
                frag.setBody(new STSBody(content, currentMessageIndex == partialMessageCount));
                currentMessageIndex++;

                nextFilter.filterWrite(session, new DefaultWriteRequest(frag));
            }
        }
        else
        {
            nextFilter.filterWrite(session, writeRequest);
        }
    }
}
