package com.zzy.zlink.bean;

import java.io.Serializable;

/**
 * user_friend
 * @author 
 */
public class UserFriend implements Serializable {
    private Integer accountId;

    private Integer friendAccountId;


    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getFriendAccountId() {
        return friendAccountId;
    }

    public void setFriendAccountId(Integer friendAccountId) {
        this.friendAccountId = friendAccountId;
    }



}