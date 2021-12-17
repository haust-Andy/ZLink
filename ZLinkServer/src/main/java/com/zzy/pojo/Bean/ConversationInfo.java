package com.zzy.pojo.Bean;

import lombok.Data;

import java.sql.Date;



@Data
public class ConversationInfo {
    //好友名字
    private String accountName2;
    //好友账号
    private String accountNumber2;
    //最后消息文本
    private String content;
    //last post time
    private Date postTime;
}
