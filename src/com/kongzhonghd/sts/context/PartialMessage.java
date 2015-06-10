package com.kongzhonghd.sts.context;

import com.kongzhonghd.sts.common.*;
import com.kongzhonghd.sts.exception.STSMessageException;
import com.kongzhonghd.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: JosephZhu
 * Date: 13-7-28
 * Time: PM5:12
 * To change this template use File | Settings | File Templates.
 * 分段消息实体
 */
class PartialMessage
{
    public static final int INCOMPLETE_PARTIAL_MESSAGE_TIMEOUT_SECONDS = 300;
    private List<AbstractSTSMessage> fragmentedMessageList = new ArrayList<AbstractSTSMessage>();
    private int totalReceivedBytes;
    private Date firstMessageTime;
    private Date lastMessageTime;

    public boolean isTimeout()
    {
        if (firstMessageTime == null) return false;
        return (new Date().getTime() - firstMessageTime.getTime()) / 1000 > INCOMPLETE_PARTIAL_MESSAGE_TIMEOUT_SECONDS;
    }

    public void addMessage(AbstractSTSMessage message) throws STSMessageException
    {
        if (firstMessageTime == null)
            firstMessageTime = new Date();

        lastMessageTime = new Date();
        int length = message.getHeader().getContentLength();
        totalReceivedBytes += length;
        fragmentedMessageList.add(message);

        STSHeaderRange range = message.getHeader().getContentRange();
        if (length != range.getEndOffset() - range.getBeginOffset() + 1)
            throw new STSMessageException(message, "收到的片段消息主体长度和n头中的长度不匹配！");
    }

    private boolean haveGotAllMessages()
    {
        if (fragmentedMessageList.size() > 0)
        {
            AbstractSTSMessage one = fragmentedMessageList.get(0);
            return totalReceivedBytes >= one.getHeader().getContentRange().getTotalBytes();
        }
        return false;
    }

    public AbstractSTSMessage getCompleteMessage() throws UnsupportedEncodingException
    {
        if (fragmentedMessageList.size() == 0 || !haveGotAllMessages())
            return null;

        Collections.sort(fragmentedMessageList, new STSPartialMessageComparator());
        AbstractSTSMessage completeMessage = (AbstractSTSMessage) ObjectUtils.deepClone(fragmentedMessageList.get(0));
        completeMessage.getHeader().removeContentRange();

        String body = "";

        for (AbstractSTSMessage fragmentedMessage : fragmentedMessageList)
        {
            String s = fragmentedMessage.getBody().getContent();
            body += s;
        }

        completeMessage.setBody(new STSBody(body));
        return completeMessage;
    }

    @Override
    public String toString()
    {
        int totalBytes = 0;
        if (fragmentedMessageList.size() > 0)
        {
            AbstractSTSMessage one = fragmentedMessageList.get(0);
            totalBytes = one.getHeader().getContentRange().getTotalBytes();
        }
        return String.format("片段消息数量：%d\t总数据：%d\t已收到数据：%d\t第一次接受数据时间：%s\t最后一次接受时间：%s", fragmentedMessageList.size(), totalBytes, totalReceivedBytes, firstMessageTime.toString(), lastMessageTime.toString());
    }

    private class STSPartialMessageComparator implements Comparator<AbstractSTSMessage>
    {
        @Override
        public int compare(AbstractSTSMessage o1, AbstractSTSMessage o2)
        {
            return o1.getHeader().getContentRange().getBeginOffset() - o2.getHeader().getContentRange().getBeginOffset();
        }
    }
}
