package com.zzy.pojo.Bean;

import lombok.Data;

import java.util.Date;


@Data
public class ChatContent {

    private Integer type;

    private String content;

    private Date postTime;
}
