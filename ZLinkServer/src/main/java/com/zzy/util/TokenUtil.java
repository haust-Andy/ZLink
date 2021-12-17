package com.zzy.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zzy.pojo.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;


@Component
public class TokenUtil {

    public String getToken() {
        return  UUID.randomUUID().toString();
    }
}
