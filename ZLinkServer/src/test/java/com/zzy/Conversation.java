package com.sample;


public class Conversation {

  private String acountNumberA;
  private String acountNumberB;
  private String messageNumber;
  private java.sql.Timestamp updateTime;


  public String getAcountNumberA() {
    return acountNumberA;
  }

  public void setAcountNumberA(String acountNumberA) {
    this.acountNumberA = acountNumberA;
  }


  public String getAcountNumberB() {
    return acountNumberB;
  }

  public void setAcountNumberB(String acountNumberB) {
    this.acountNumberB = acountNumberB;
  }


  public String getMessageNumber() {
    return messageNumber;
  }

  public void setMessageNumber(String messageNumber) {
    this.messageNumber = messageNumber;
  }


  public java.sql.Timestamp getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }

}
