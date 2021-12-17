package com.zzy.zlink.bean;

import java.io.Serializable;
import java.sql.Date;



public class ConversationInfo implements Serializable {
    private String accountName2;
    //好友账号
    private String accountNumber2;
    //最后消息文本
    private String content;
    //last post time
    private String postTime;

    public String getAccountName2() {
        return accountName2;
    }

    public void setAccountName2(String accountName2) {
        this.accountName2 = accountName2;
    }

    public String getAccountNumber2() {
        return accountNumber2;
    }

    public void setAccountNumber2(String accountNumber2) {
        this.accountNumber2 = accountNumber2;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
}
