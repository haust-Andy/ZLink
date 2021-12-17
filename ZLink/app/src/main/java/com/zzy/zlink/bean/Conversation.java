package com.zzy.zlink.bean;

import java.io.Serializable;
import java.sql.Date;

/**
 * conversation
 * @author 
 */
public class Conversation implements Serializable {



    private String oppositeName;

    private String lastMessage;

    private Date lastMessageTime;

    public String getOppositeName() {
        return oppositeName;
    }

    public void setOppositeName(String oppositeName) {
        this.oppositeName = oppositeName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(Date lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}