package com.zzy.pojo.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * message_content
 * @author 
 */
@Data
public class MessageContent implements Serializable {
    private Integer messageId;

    private Integer conversationId;

    private String accountName;

    private String content;

    private Date postTime;

}