package com.zzy.pojo.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private Integer accountId;

    private String accountNumber;

    private String accountName;

    private String accountPassword;

    private String accountEmail;

    private String accountToken;

    private byte[] accountHead;

    private static final long serialVersionUID = 1L;
}