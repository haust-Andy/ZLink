package com.zzy.pojo.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * user_friend
 * @author 
 */
@Data
public class UserFriend implements Serializable {
    private Integer accountId;

    private Integer friendAccountId;

    private static final long serialVersionUID = 1L;
}