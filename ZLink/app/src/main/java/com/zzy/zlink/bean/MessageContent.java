package com.zzy.zlink.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * message_content
 * @author 
 */
public class MessageContent implements Serializable {
    private Integer messageId;

    private Integer conversationId;

    private Integer accountId;

    private String content;

    private Date postTime;

    private static final long serialVersionUID = 1L;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}