package com.zzy.pojo.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * conversation
 * @author 
 */
@Data
public class Conversation implements Serializable {
    private Integer conversationId;

    private String accountNumber1;

    private String accountName1;

    private String accountNumber2;

    private String accountName2;

    private Integer lastMessageId;

}