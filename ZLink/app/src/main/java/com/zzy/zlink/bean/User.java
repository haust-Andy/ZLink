package com.zzy.zlink.bean;

import java.io.Serializable;
import java.util.Arrays;

/**
 * user
 *
 * @author
 */
public class User implements Serializable {
    private Integer accountId;
    private String accountNumber;
    private String accountName;
    private String accountPassword;
    private String accountEmail;
    private String accountToken;
    //    private byte[] accountHead;
    private String accountHead;

    public String getAccountToken() {
        return accountToken;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }


    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

//    public byte[] getAccountHead() {
//        return accountHead;
//    }
//
//    public void setAccountHead(byte[] accountHead) {
//        this.accountHead = accountHead;
//    }

    public String getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(String accountHead) {
        this.accountHead = accountHead;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "accountId=" + accountId +
//                ", accountNumber='" + accountNumber + '\'' +
//                ", accountName='" + accountName + '\'' +
//                ", accountPassword='" + accountPassword + '\'' +
//                ", accountEmail='" + accountEmail + '\'' +
//                ", accountHead=" + Arrays.toString(accountHead) +
//                '}';
//    }

    @Override
    public String toString() {
        return "User{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountPassword='" + accountPassword + '\'' +
                ", accountEmail='" + accountEmail + '\'' +
                ", accountHead=" + accountHead +
                '}';
    }


}