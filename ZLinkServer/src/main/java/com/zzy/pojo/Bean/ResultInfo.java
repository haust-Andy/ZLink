package com.zzy.pojo.Bean;


import lombok.Data;

@Data
public class ResultInfo {
    public int code;
    public Object data;

    public ResultInfo(int code, Object data) {
        this.code = code;
        this.data = data;
    }
}
