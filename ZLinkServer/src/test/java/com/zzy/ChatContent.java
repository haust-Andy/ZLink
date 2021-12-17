package com.sample;


public class ChatContent {

  private String messageNumber;
  private String acountNumber;
  private String content;
  private java.sql.Timestamp postTime;


  public String getMessageNumber() {
    return messageNumber;
  }

  public void setMessageNumber(String messageNumber) {
    this.messageNumber = messageNumber;
  }


  public String getAcountNumber() {
    return acountNumber;
  }

  public void setAcountNumber(String acountNumber) {
    this.acountNumber = acountNumber;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public java.sql.Timestamp getPostTime() {
    return postTime;
  }

  public void setPostTime(java.sql.Timestamp postTime) {
    this.postTime = postTime;
  }

}
